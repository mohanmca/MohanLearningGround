#!/usr/bin/env bash
#
# ua2_migration_backup.sh
#
# Purpose
# -------
# Inventory files/configuration used by UA2 processes before an OS migration.
#
# Two modes:
#
#   1) dry-run
#      - Finds UA2 processes.
#      - Builds inventory files.
#      - Produces a final "files-to-backup.txt" manifest.
#      - DOES NOT copy application files.
#
#   2) backup
#      - Performs the same inventory.
#      - Copies readable candidate files outside /local/1.
#      - Preserves the original absolute directory structure under:
#
#          <backup-dir>/rootfs/
#
# Example:
#
#   /etc/ua2/config.xml
#
# becomes:
#
#   /local/1/backup/ua2/20260820_201500/rootfs/etc/ua2/config.xml
#
# IMPORTANT
# ---------
# - Run as the SAME USER that normally owns/runs the UA2 processes.
# - Root is NOT required for the basic inventory, but files/processes that
#   your account cannot read will be reported in permission-denied files.
# - This script deliberately avoids automatically backing up/restoring core
#   OS binaries and libraries such as /usr/lib64, /bin, /lib64, etc.
#   Those should come from the target RHEL installation/packages.
# - Open-file discovery alone cannot find config files that UA2 read during
#   startup and then closed. Therefore this script also inventories:
#       * process command lines
#       * environment
#       * cwd/executable
#       * mapped files/libraries
#       * cron
#       * systemd/init references
#       * shell startup files
#       * symlinks
#       * mounts/network/process limits
#
# Usage
# -----
#
#   ./ua2_migration_backup.sh dry-run
#   ./ua2_migration_backup.sh backup
#
# Optional:
#
#   ./ua2_migration_backup.sh dry-run --pattern 'UA2|ua2'
#   ./ua2_migration_backup.sh backup --backup-root /local/1/backup/ua2
#
# After dry-run, inspect:
#
#   <run-dir>/inventory/files-to-backup.txt
#   <run-dir>/inventory/files-not-readable.txt
#   <run-dir>/inventory/os-files-do-not-copy.txt
#   <run-dir>/inventory/summary.txt
#
# Exit codes
# ----------
#   0 = success
#   1 = usage/configuration error
#   2 = no matching application processes found
#
set -u
set -o pipefail

###############################################################################
# Defaults
###############################################################################

MODE=""
PROCESS_REGEX='[uU][aA]2'
BACKUP_ROOT='/local/1/backup/ua2'
PRESERVED_MOUNT='/local/1'

# Core RHEL paths that should be INVENTORIED but not blindly copied from RHEL 7.
OS_PATH_REGEX='^/(bin|sbin|lib|lib64|usr/bin|usr/sbin|usr/lib|usr/lib64)(/|$)'

# Virtual/runtime filesystems that should not be backed up as ordinary files.
VIRTUAL_PATH_REGEX='^/(proc|sys|dev|run)(/|$)'

###############################################################################
# Helper functions
###############################################################################

usage() {
    cat <<EOF
Usage:
  $0 dry-run [--pattern REGEX] [--backup-root DIR]
  $0 backup  [--pattern REGEX] [--backup-root DIR]

Examples:
  $0 dry-run
  $0 backup
  $0 dry-run --pattern '[uU][aA]2'
  $0 backup --backup-root /local/1/backup/ua2

Modes:
  dry-run   Create inventory and files-to-backup manifest only.
  backup    Create inventory and copy readable candidate files.

Default process regex:
  $PROCESS_REGEX

Default backup root:
  $BACKUP_ROOT
EOF
}

log() {
    printf '[%s] %s\n' "$(date '+%Y-%m-%d %H:%M:%S')" "$*"
}

warn() {
    printf '[%s] WARNING: %s\n' "$(date '+%Y-%m-%d %H:%M:%S')" "$*" >&2
}

have_cmd() {
    command -v "$1" >/dev/null 2>&1
}

safe_count() {
    local file="$1"
    if [[ -f "$file" ]]; then
        wc -l < "$file" | tr -d ' '
    else
        printf '0'
    fi
}

###############################################################################
# Argument parsing
###############################################################################

if [[ $# -lt 1 ]]; then
    usage
    exit 1
fi

MODE="$1"
shift

case "$MODE" in
    dry-run|backup)
        ;;
    -h|--help)
        usage
        exit 0
        ;;
    *)
        echo "ERROR: First argument must be 'dry-run' or 'backup'." >&2
        usage
        exit 1
        ;;
esac

while [[ $# -gt 0 ]]; do
    case "$1" in
        --pattern)
            [[ $# -ge 2 ]] || { echo "ERROR: --pattern requires a value." >&2; exit 1; }
            PROCESS_REGEX="$2"
            shift 2
            ;;
        --backup-root)
            [[ $# -ge 2 ]] || { echo "ERROR: --backup-root requires a value." >&2; exit 1; }
            BACKUP_ROOT="$2"
            shift 2
            ;;
        -h|--help)
            usage
            exit 0
            ;;
        *)
            echo "ERROR: Unknown argument: $1" >&2
            usage
            exit 1
            ;;
    esac
done

###############################################################################
# Create run directory
###############################################################################

TIMESTAMP="$(date '+%Y%m%d_%H%M%S')"
RUN_DIR="${BACKUP_ROOT}/${TIMESTAMP}_${MODE}"
INV="${RUN_DIR}/inventory"
ROOTFS="${RUN_DIR}/rootfs"
CRON_DIR="${RUN_DIR}/cron"
DELETED_DIR="${RUN_DIR}/deleted"

mkdir -p "$INV" "$ROOTFS" "$CRON_DIR" "$DELETED_DIR" || {
    echo "ERROR: Cannot create $RUN_DIR" >&2
    exit 1
}

# Save exact invocation for auditability.
{
    printf 'Date: %s\n' "$(date)"
    printf 'User: %s\n' "$(id 2>/dev/null || true)"
    printf 'Hostname: %s\n' "$(hostname 2>/dev/null || true)"
    printf 'Mode: %s\n' "$MODE"
    printf 'Process regex: %s\n' "$PROCESS_REGEX"
    printf 'Backup root: %s\n' "$BACKUP_ROOT"
    printf 'Preserved mount: %s\n' "$PRESERVED_MOUNT"
    printf 'Script PID: %s\n' "$$"
    printf 'Parent PID: %s\n' "$PPID"
} > "$INV/run-info.txt"

log "Mode       : $MODE"
log "Run folder : $RUN_DIR"
log "Pattern    : $PROCESS_REGEX"

###############################################################################
# 1. Find target processes
#
# pgrep -f can accidentally match this script itself if its filename contains
# "ua2". Therefore explicitly remove this shell PID and its immediate parent.
###############################################################################

if have_cmd pgrep; then
    pgrep -f "$PROCESS_REGEX" 2>/dev/null \
        | awk -v self="$$" -v parent="$PPID" '$1 != self && $1 != parent' \
        | sort -n -u > "$INV/pids.txt"
else
    # Fallback if pgrep is unavailable.
    ps -eo pid=,args= \
        | awk -v re="$PROCESS_REGEX" -v self="$$" -v parent="$PPID" \
            '$1 != self && $1 != parent && $0 ~ re {print $1}' \
        | sort -n -u > "$INV/pids.txt"
fi

PID_COUNT="$(safe_count "$INV/pids.txt")"

if [[ "$PID_COUNT" -eq 0 ]]; then
    echo "ERROR: No processes matched regex: $PROCESS_REGEX" >&2
    echo "Run directory retained for inspection: $RUN_DIR" >&2
    exit 2
fi

log "Found $PID_COUNT matching process(es)."

###############################################################################
# 2. Process details: PID, PPID, user, command line, executable, CWD
###############################################################################

: > "$INV/process-details.txt"

while IFS= read -r pid; do
    [[ -d "/proc/$pid" ]] || continue

    {
        echo "================================================================"
        echo "PID=$pid"
        ps -o pid=,ppid=,user=,lstart=,args= -p "$pid" 2>/dev/null || true

        printf 'EXE='
        readlink -f "/proc/$pid/exe" 2>/dev/null || echo "<unreadable>"

        printf 'CWD='
        readlink -f "/proc/$pid/cwd" 2>/dev/null || echo "<unreadable>"

        printf 'CMD='
        tr '\0' ' ' < "/proc/$pid/cmdline" 2>/dev/null || true
        echo
    } >> "$INV/process-details.txt"
done < "$INV/pids.txt"

###############################################################################
# 3. Process environment
#
# This often reveals CONFIG, HOME, JAVA_HOME, CLASSPATH, DATA, CACHE, TMPDIR,
# LD_LIBRARY_PATH, etc.
###############################################################################

: > "$INV/environment.txt"

while IFS= read -r pid; do
    [[ -r "/proc/$pid/environ" ]] || {
        echo "PID $pid: /proc/$pid/environ not readable" >> "$INV/process-permission-denied.txt"
        continue
    }

    {
        echo "================================================================"
        echo "PID=$pid"
        tr '\0' '\n' < "/proc/$pid/environ" 2>/dev/null | sort
    } >> "$INV/environment.txt"
done < "$INV/pids.txt"

###############################################################################
# 4. Open file handles using /proc/<pid>/fd
#
# readlink is used instead of parsing normal human-readable lsof output.
###############################################################################

: > "$INV/open-files-all.raw"

while IFS= read -r pid; do
    [[ -d "/proc/$pid/fd" ]] || continue

    for fd in "/proc/$pid"/fd/*; do
        [[ -e "$fd" || -L "$fd" ]] || continue
        readlink "$fd" 2>/dev/null || true
    done
done < "$INV/pids.txt" \
    | sort -u > "$INV/open-files-all.raw"

# Keep normal absolute filesystem paths.
grep '^/' "$INV/open-files-all.raw" 2>/dev/null \
    | sort -u > "$INV/open-files-absolute.txt" || true

# Record deleted-but-still-open files separately.
grep ' (deleted)$' "$INV/open-files-absolute.txt" 2>/dev/null \
    > "$INV/open-files-deleted.txt" || true

# Remove "(deleted)" marker for path classification.
sed 's/ (deleted)$//' "$INV/open-files-absolute.txt" \
    | sort -u > "$INV/open-files-normalized.txt"

###############################################################################
# 5. Memory-mapped files/libraries
#
# /proc/<pid>/maps catches shared libraries, mmap'ed data/state files, etc.
###############################################################################

: > "$INV/mapped-files.txt"

while IFS= read -r pid; do
    [[ -r "/proc/$pid/maps" ]] || continue
    awk '$6 ~ /^\// {print $6}' "/proc/$pid/maps" 2>/dev/null
done < "$INV/pids.txt" \
    | sed 's/ (deleted)$//' \
    | sort -u > "$INV/mapped-files.txt"

###############################################################################
# 6. Executables and working directories
###############################################################################

: > "$INV/process-executables.txt"
: > "$INV/process-working-directories.txt"

while IFS= read -r pid; do
    readlink -f "/proc/$pid/exe" 2>/dev/null || true
done < "$INV/pids.txt" \
    | grep '^/' \
    | sort -u > "$INV/process-executables.txt" || true

while IFS= read -r pid; do
    readlink -f "/proc/$pid/cwd" 2>/dev/null || true
done < "$INV/pids.txt" \
    | grep '^/' \
    | sort -u > "$INV/process-working-directories.txt" || true

###############################################################################
# 7. Build raw candidate list from:
#      - open file handles
#      - memory mapped files
#      - executables
#
# CWD itself is useful inventory, but blindly backing up an entire CWD could
# unexpectedly copy a huge directory tree, so CWD is NOT automatically placed
# into files-to-backup.txt.
###############################################################################

cat \
    "$INV/open-files-normalized.txt" \
    "$INV/mapped-files.txt" \
    "$INV/process-executables.txt" \
    2>/dev/null \
    | grep '^/' \
    | sort -u > "$INV/runtime-paths-all.txt"

###############################################################################
# 8. Separate /local/1, virtual paths, OS files and application candidates
###############################################################################

grep "^${PRESERVED_MOUNT}\(/\\|$\)" "$INV/runtime-paths-all.txt" 2>/dev/null \
    > "$INV/runtime-paths-already-on-local1.txt" || true

grep -E "$VIRTUAL_PATH_REGEX" "$INV/runtime-paths-all.txt" 2>/dev/null \
    > "$INV/virtual-files-do-not-copy.txt" || true

grep -E "$OS_PATH_REGEX" "$INV/runtime-paths-all.txt" 2>/dev/null \
    > "$INV/os-files-do-not-copy.txt" || true

grep -v "^${PRESERVED_MOUNT}\(/\\|$\)" "$INV/runtime-paths-all.txt" 2>/dev/null \
    | grep -Ev "$VIRTUAL_PATH_REGEX" \
    | grep -Ev "$OS_PATH_REGEX" \
    | sort -u > "$INV/runtime-app-candidates.txt" || true

###############################################################################
# 9. Search startup/configuration mechanisms for references to UA2
#
# These files may NOT currently be open, but can be essential when restarting.
#
# Search only locations readable by the current user.
###############################################################################

: > "$INV/startup-reference-files.txt"

for search_root in \
    /etc/systemd \
    /etc/init.d \
    /etc/rc.d \
    /etc/profile \
    /etc/profile.d \
    /etc/environment \
    /etc/crontab \
    /etc/cron.d \
    /etc/cron.hourly \
    /etc/cron.daily \
    /etc/cron.weekly \
    /etc/cron.monthly
do
    [[ -e "$search_root" ]] || continue

    if [[ -f "$search_root" ]]; then
        grep -IlE "$PROCESS_REGEX" "$search_root" 2>/dev/null || true
    else
        grep -RIlE "$PROCESS_REGEX" "$search_root" 2>/dev/null || true
    fi
done \
    | sort -u > "$INV/startup-reference-files.txt"

###############################################################################
# 10. User shell startup files
###############################################################################

: > "$INV/user-startup-files.txt"

for f in \
    "$HOME/.bash_profile" \
    "$HOME/.bashrc" \
    "$HOME/.profile" \
    "$HOME/.bash_login"
do
    [[ -e "$f" ]] && printf '%s\n' "$f"
done | sort -u > "$INV/user-startup-files.txt"

###############################################################################
# 11. Crontab
###############################################################################

if have_cmd crontab; then
    crontab -l > "$CRON_DIR/crontab.${USER:-$(id -un)}" 2>"$CRON_DIR/crontab.stderr" || true
else
    echo "crontab command not found" > "$CRON_DIR/crontab.stderr"
fi

###############################################################################
# 12. at jobs
###############################################################################

if have_cmd atq && have_cmd at; then
    atq > "$INV/atq.txt" 2>/dev/null || true

    while IFS= read -r job; do
        [[ -n "$job" ]] || continue
        at -c "$job" > "$CRON_DIR/at-job-${job}.txt" 2>/dev/null || true
    done < <(atq 2>/dev/null | awk '{print $1}')
fi

###############################################################################
# 13. systemd/cgroup relationship
###############################################################################

: > "$INV/cgroups.txt"

while IFS= read -r pid; do
    {
        echo "================================================================"
        echo "PID=$pid"
        cat "/proc/$pid/cgroup" 2>/dev/null || true
    } >> "$INV/cgroups.txt"
done < "$INV/pids.txt"

###############################################################################
# 14. Process limits
###############################################################################

: > "$INV/process-limits.txt"

while IFS= read -r pid; do
    {
        echo "================================================================"
        echo "PID=$pid"
        cat "/proc/$pid/limits" 2>/dev/null || true
    } >> "$INV/process-limits.txt"
done < "$INV/pids.txt"

###############################################################################
# 15. Symlinks
#
# Search selected application/config areas only. We do NOT traverse the entire
# filesystem because that can be expensive and can hit remote mounts.
###############################################################################

: > "$INV/symlinks.txt"

for root in /etc /opt /usr/local "$HOME"; do
    [[ -d "$root" ]] || continue

    find "$root" -xdev -type l \
        \( -iname '*ua2*' -o -lname '*ua2*' -o -lname '*UA2*' \) \
        -printf '%p -> %l\n' 2>/dev/null || true
done | sort -u > "$INV/symlinks.txt"

###############################################################################
# 16. Mount/system/network inventory
#
# These are comparison/reference files, not candidates for restoration.
###############################################################################

have_cmd findmnt && findmnt > "$INV/findmnt.txt" 2>&1 || true
df -hT > "$INV/df-hT.txt" 2>&1 || true
mount > "$INV/mount.txt" 2>&1 || true

have_cmd ip && ip addr show > "$INV/ip-address.txt" 2>&1 || true
have_cmd ip && ip route show > "$INV/ip-route.txt" 2>&1 || true
have_cmd ip && ip maddr show > "$INV/ip-maddr.txt" 2>&1 || true

have_cmd ss && ss -anp > "$INV/ss-anp.txt" 2>&1 || true
have_cmd ipcs && ipcs -a > "$INV/ipcs.txt" 2>&1 || true

###############################################################################
# 17. RPM/package inventory
###############################################################################

if have_cmd rpm; then
    rpm -qa | sort > "$INV/rpm-packages.txt" 2>&1 || true

    : > "$INV/runtime-path-rpm-owners.txt"

    while IFS= read -r f; do
        [[ -e "$f" ]] || continue
        printf '%s : ' "$f" >> "$INV/runtime-path-rpm-owners.txt"
        rpm -qf "$f" >> "$INV/runtime-path-rpm-owners.txt" 2>&1 || true
    done < "$INV/runtime-paths-all.txt"
fi

###############################################################################
# 18. Construct final files-to-backup manifest
#
# Sources:
#   A. Runtime application candidates outside /local/1.
#   B. Startup/service/cron reference files.
#   C. User shell startup files.
#
# Again, core RHEL binaries/libraries are deliberately excluded.
###############################################################################

cat \
    "$INV/runtime-app-candidates.txt" \
    "$INV/startup-reference-files.txt" \
    "$INV/user-startup-files.txt" \
    2>/dev/null \
    | grep '^/' \
    | grep -v "^${PRESERVED_MOUNT}\(/\\|$\)" \
    | grep -Ev "$VIRTUAL_PATH_REGEX" \
    | grep -Ev "$OS_PATH_REGEX" \
    | sort -u > "$INV/files-to-backup-unchecked.txt"

###############################################################################
# 19. Classify candidates:
#       - readable file/symlink
#       - directory
#       - not readable
#       - no longer exists
#
# Directories are NOT recursively copied automatically, because a process CWD
# or startup reference could otherwise cause a very large/unintended backup.
###############################################################################

: > "$INV/files-to-backup.txt"
: > "$INV/directories-review-manually.txt"
: > "$INV/files-not-readable.txt"
: > "$INV/files-no-longer-exist.txt"

while IFS= read -r path; do
    [[ -n "$path" ]] || continue

    if [[ -L "$path" ]]; then
        # Symlink itself will be preserved by rsync -aR.
        if [[ -r "$path" || -e "$path" ]]; then
            printf '%s\n' "$path" >> "$INV/files-to-backup.txt"
        else
            printf '%s\n' "$path" >> "$INV/files-not-readable.txt"
        fi

    elif [[ -f "$path" ]]; then
        if [[ -r "$path" ]]; then
            printf '%s\n' "$path" >> "$INV/files-to-backup.txt"
        else
            printf '%s\n' "$path" >> "$INV/files-not-readable.txt"
        fi

    elif [[ -d "$path" ]]; then
        printf '%s\n' "$path" >> "$INV/directories-review-manually.txt"

    elif [[ -e "$path" ]]; then
        # FIFOs, devices, sockets, etc. are inventory only.
        printf '%s\n' "$path" >> "$INV/directories-review-manually.txt"

    else
        printf '%s\n' "$path" >> "$INV/files-no-longer-exist.txt"
    fi
done < "$INV/files-to-backup-unchecked.txt"

sort -u -o "$INV/files-to-backup.txt" "$INV/files-to-backup.txt"
sort -u -o "$INV/files-not-readable.txt" "$INV/files-not-readable.txt"
sort -u -o "$INV/directories-review-manually.txt" "$INV/directories-review-manually.txt"
sort -u -o "$INV/files-no-longer-exist.txt" "$INV/files-no-longer-exist.txt"

###############################################################################
# 20. Optional lsof inventory
#
# lsof is supplementary. /proc is the primary source.
###############################################################################

if have_cmd lsof; then
    PID_CSV="$(paste -sd, "$INV/pids.txt")"
    lsof -nP -p "$PID_CSV" > "$INV/lsof-full.txt" 2>&1 || true
else
    echo "lsof not installed; /proc inventory was used." > "$INV/lsof-full.txt"
fi

###############################################################################
# 21. BACKUP MODE ONLY: copy files using rsync -aR
#
# -a = preserve permissions/timestamps/symlinks where allowed
# -R = preserve absolute path structure beneath rootfs/
#
# Example:
#     /etc/ua2/a.conf
# becomes:
#     rootfs/etc/ua2/a.conf
###############################################################################

: > "$INV/copied-files.txt"
: > "$INV/copy-failed.txt"
: > "$INV/rsync-errors.txt"

if [[ "$MODE" == "backup" ]]; then
    if ! have_cmd rsync; then
        echo "ERROR: backup mode requires rsync." >&2
        exit 1
    fi

    log "Copying readable candidate files..."

    while IFS= read -r path; do
        [[ -n "$path" ]] || continue

        if rsync -aR -- "$path" "$ROOTFS/" 2>>"$INV/rsync-errors.txt"; then
            printf '%s\n' "$path" >> "$INV/copied-files.txt"
        else
            printf '%s\n' "$path" >> "$INV/copy-failed.txt"
        fi
    done < "$INV/files-to-backup.txt"

    # Save user crontab backup alongside the rootfs tree as a separate artifact.
    # We intentionally do not run "crontab" restoration automatically.
    log "Backup copy complete."
else
    log "Dry-run mode: no application files were copied."
fi

###############################################################################
# 22. Generate human-readable summary
###############################################################################

{
    echo "UA2 MIGRATION BACKUP SUMMARY"
    echo "============================"
    echo
    echo "Run directory                : $RUN_DIR"
    echo "Mode                         : $MODE"
    echo "Process regex                : $PROCESS_REGEX"
    echo "Matching processes           : $PID_COUNT"
    echo
    echo "Runtime paths found          : $(safe_count "$INV/runtime-paths-all.txt")"
    echo "Already under /local/1       : $(safe_count "$INV/runtime-paths-already-on-local1.txt")"
    echo "Core OS paths excluded       : $(safe_count "$INV/os-files-do-not-copy.txt")"
    echo "Virtual paths excluded       : $(safe_count "$INV/virtual-files-do-not-copy.txt")"
    echo
    echo "FINAL readable files         : $(safe_count "$INV/files-to-backup.txt")"
    echo "Unreadable files             : $(safe_count "$INV/files-not-readable.txt")"
    echo "Directories/manual review    : $(safe_count "$INV/directories-review-manually.txt")"
    echo "Missing/deleted paths        : $(safe_count "$INV/files-no-longer-exist.txt")"
    echo "Open deleted files           : $(safe_count "$INV/open-files-deleted.txt")"
    echo
    if [[ "$MODE" == "backup" ]]; then
        echo "Files copied                 : $(safe_count "$INV/copied-files.txt")"
        echo "Copy failures                : $(safe_count "$INV/copy-failed.txt")"
    else
        echo "Files copied                 : 0 (dry-run)"
    fi
    echo
    echo "MOST IMPORTANT FILES TO REVIEW"
    echo "------------------------------"
    echo "$INV/files-to-backup.txt"
    echo "$INV/files-not-readable.txt"
    echo "$INV/directories-review-manually.txt"
    echo "$INV/open-files-deleted.txt"
    echo "$INV/os-files-do-not-copy.txt"
    echo "$INV/process-details.txt"
    echo "$INV/environment.txt"
    echo "$INV/startup-reference-files.txt"
    echo "$INV/symlinks.txt"
    echo
    echo "NOTE:"
    echo "This inventory cannot guarantee detection of every file UA2 may require."
    echo "A configuration file read only during application startup may not currently"
    echo "appear as an open file. Review startup scripts/configuration before migration."
} > "$INV/summary.txt"

###############################################################################
# 23. Print final result
###############################################################################

echo
cat "$INV/summary.txt"
echo

if [[ "$MODE" == "dry-run" ]]; then
    echo "DRY-RUN COMPLETE."
    echo
    echo "Review the proposed backup list:"
    echo "  cat '$INV/files-to-backup.txt'"
    echo
    echo "Then execute:"
    echo "  $0 backup --pattern '$PROCESS_REGEX' --backup-root '$BACKUP_ROOT'"
else
    echo "BACKUP COMPLETE."
    echo
    echo "Backup rootfs:"
    echo "  $ROOTFS"
    echo
    echo "Verify:"
    echo "  cat '$INV/copied-files.txt'"
    echo "  cat '$INV/copy-failed.txt'"
fi
