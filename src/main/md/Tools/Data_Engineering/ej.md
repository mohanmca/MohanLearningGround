# EffectiveJavaSystemPrompt

A self-contained operating manual for the **Modern Effective Java — Concurrency** project. Hand this file plus `prompt.md` to a fresh agent and the agent should be able to recreate the entire project — source, chapter, PDF — from nothing. This prompt is the *how*; `prompt.md` is the *what*.

---

## 0. How to use this document

Two inputs drive the work:

1. **`prompt.md`** — a numbered log of user instructions, in order. Treat it as a tape to replay. Execute the instructions sequentially; each one builds on the previous state.
2. **This file (`EffectiveJavaSystemPrompt.md`)** — the standing conventions that every instruction in `prompt.md` should be interpreted against. When the user's prompt is ambiguous, resolve the ambiguity with the rules here. (Example: prompt #15 says "Item10_RandomGenerator.java could be just RandomGenerator.java" — interpret this against rule 4's "drop `ItemNN_`" convention.)

Work in this loop:

```
for each prompt in prompt.md:
    interpret → plan → edit → build/run → verify
    regenerate PDF if any .md changed
    move to next prompt only after verification
```

Do **not** skip ahead. Many later prompts repair or refactor work the user asked for in earlier prompts, and reordering loses that texture.

## 1. Prerequisites — files and tooling that must exist before you start

### 1.1 Style reference asset

Prompt #9 asks you to match the typographic style of Joshua Bloch's *Effective Java* Chapter 11 (Items 78–84, "Concurrency"). The source of truth is:

- **Path the user referred to:** `/Users/mohan.narayanaswamy/Documents/concurrency.pdf`
- **Content:** the Concurrency chapter from *Effective Java, 3rd Edition* (Joshua Bloch, Addison-Wesley 2017), pages 311–337.
- **How to obtain:** the user owns a copy of the book; the PDF at this path is an extract the user prepared from their own licensed copy. Do **not** fetch this from the internet — the book is copyrighted. If the file is missing, ask the user to place it at that path before running prompt #9.

Before executing prompt #9, verify the file exists:

```bash
test -f /Users/mohan.narayanaswamy/Documents/concurrency.pdf \
  && echo "style reference present" \
  || echo "MISSING: ask user to place Effective Java Ch.11 PDF here"
```

When you read it, extract the *style signals*: bold rule sentences mid-paragraph, code comments that label each sample (`// Broken!`, `// Correct`, `// Lock-free synchronization with java.util.concurrent.atomic`), "In summary" closings, cross-references as "(Item N)", no em-dash in headings, serif body type. Replicate those signals — not the book's actual text.

### 1.2 Tooling that must be on `$PATH` or at known absolute paths

| Tool | Role | Expected location |
| --- | --- | --- |
| OpenJDK 25 | compile + run | `/opt/homebrew/opt/openjdk` (Homebrew) |
| Maven 3.9+ | build | `/opt/homebrew/bin/mvn` |
| `uv` | PEP 723 Python runner for PDF pipeline | `~/.local/bin/uv` |
| Google Chrome | headless PDF renderer | `/Applications/Google Chrome.app/Contents/MacOS/Google Chrome` |
| `unzip` | read JDK `src.zip` | system default |

Not installed and not required: `pandoc`, `wkhtmltopdf`. Do **not** install them; the project's PDF pipeline deliberately avoids both.

### 1.3 Pre-flight check

Run before executing `prompt.md`:

```bash
command -v mvn && command -v uv \
  && test -x "/Applications/Google Chrome.app/Contents/MacOS/Google Chrome" \
  && /opt/homebrew/opt/openjdk/bin/java --version | grep -q 'openjdk 25' \
  && test -f /Users/mohan.narayanaswamy/Documents/concurrency.pdf \
  && echo "READY"
```

If any check fails, stop and surface the missing piece to the user before touching the repo.

## 2. Project identity (the end state you are building toward)

- **Repository root:** `/Users/mohan.narayanaswamy/git/modern_java`
- **Maven coordinates:** `groupId=com.example`, `artifactId=modern-java`, `version=1.0.0`, `packaging=jar`
- **Base Java package:** `com.modern.effective.java.concurrency`
- **Intent:** a runnable, printable "Chapter 11++" for *Effective Java*, covering JDK 13+ concurrency and threading. Every written item has a runnable sample.
- **Canonical markdown:** `Modern_Effective_Java_Concurrency.md`
- **Printable artifact:** `Modern_Effective_Java_Concurrency.pdf` (generated)
- **Prompt log:** `prompt.md`
- **This manual:** `EffectiveJavaSystemPrompt.md`

## 3. Environment activation

Always activate JDK 25 before any Maven command:

```bash
export JAVA_HOME=/opt/homebrew/opt/openjdk
export PATH=$JAVA_HOME/bin:$PATH
```

JDK source archive for file-level citations:

```
/opt/homebrew/opt/openjdk/libexec/openjdk.jdk/Contents/Home/lib/src.zip
```

Extract once when you need to grep it:

```bash
mkdir -p /tmp/jdk25-src && unzip -q -o \
  /opt/homebrew/opt/openjdk/libexec/openjdk.jdk/Contents/Home/lib/src.zip \
  -d /tmp/jdk25-src
```

## 4. Execution protocol for `prompt.md`

Each numbered entry in `prompt.md` maps to one or more actions. This table is the decoder:

| Prompt | Intent | Primary actions |
| --- | --- | --- |
| 1 | Create Maven project for virtual threads | Scaffold `pom.xml` + one `VirtualThreadsDemo.java` with three mini-demos (single VT, 10k VT, platform-vs-virtual compare) |
| 2 | Find newest JDK | Check `/opt/homebrew/opt/openjdk*` — pick 25 over 21/17/11 |
| 3 | Write `README.md` | Include JDK table, activation snippet, `src.zip` path, and a table of key JDK class paths |
| 4 | Research "10 Effective-Java-style items" for JDK 13+ | Use subagents (rule 5.1); read JDK source via `src.zip`; cite JEPs + inside.java/Red Hat/AWS blogs only |
| 5–6 | Parallelize research | Launch 4 subagents concurrently: (virtual-thread internals) (structured-concurrency + scoped-values) (other j.u.c additions) (blog/JEP citations) |
| 7 | Create `Modern_Effective_Java.md` | Short 10-item summary list (ancestor of the longer chapter) |
| 8 | Create sample code per item | One runnable class per item at `src/main/java/com/modern/effective/java/concurrency/…` |
| 9 | Refactor markdown into Bloch-style `Modern_Effective_Java_Concurrency.md` | Read `/Users/mohan.narayanaswamy/Documents/concurrency.pdf`; mirror its voice (rule 7); number items 85–94 to continue from Bloch's 78–84 |
| 10 | Flatten project (no `virtual-threads-demo/` subdir) | Move `pom.xml`, `src/`, `README.md` to repo root |
| 11 | Rename artifact | `pom.xml` `<artifactId>` → `modern-java` |
| 12–15 | Rename sample classes | Drop `ItemNN_` prefix from filenames; keep item # in javadoc with a markdown link (rule 4) |
| 16 | Generate PDF | Python (via uv) markdown→HTML, Chrome headless HTML→PDF (rule 8) |
| 17 | Merge items 90 + 91; expand item 90 | Combine into "Understand virtual-thread pinning; observe it, don't guess"; add counter-points, decision matrix, "what still pins"; renumber 92→91/93→92/94→93 everywhere (markdown, javadoc links) |
| 18 | Add dedicated JFR item | Insert a new Item 91 "Use Java Flight Recorder for concurrency observability" covering 4 collection modes, event table, platform-vs-virtual differences, custom events, practical workflows; renumber 91→92, 92→93, 93→94 |
| 19 | Create this file | `EffectiveJavaSystemPrompt.md` |
| 20 | Create `prompt.md` | Verbatim log of user messages |

If a prompt contradicts this decoder, the **prompt wins**; update this manual afterward.

## 5. Subagent strategy (prompt 4–6)

When the user asks for research requiring multiple independent facets, spawn `general-purpose` agents **in parallel** (one message, multiple `Agent` tool calls). The four slices used here were:

1. **Virtual threads source-level.** Read `Thread.java`, `VirtualThread.java`, `Executors.java`, `ThreadPerTaskExecutor.java`. Quote method signatures + `@since`.
2. **Structured concurrency + scoped values.** Read `StructuredTaskScope.java`, `ScopedValue.java`. Check package (moved out of `jdk.incubator` by JDK 25) and preview status.
3. **Other JDK 13+ j.u.c additions.** `Future.State`, `resultNow()`, `exceptionNow()`; `ForkJoinPool` JDK 19+ additions; `RandomGenerator` + `RandomGeneratorFactory`.
4. **Blog / JEP citations.** URLs only — JEPs on openjdk.org, inside.java, developers.redhat.com, AWS Corretto blog. **No Medium, no Baeldung, no dev.to.**

Tell each subagent: read-only research, return a compact structured report (500–600 words), flag rejected items with one-line reasons. Do not let subagents write code.

## 6. `pom.xml` conventions (do not change casually)

- `<maven.compiler.release>25</maven.compiler.release>`
- `maven-compiler-plugin` has `<compilerArgs><arg>--enable-preview</arg></compilerArgs>` — required for `StructuredTaskScope` (JEP 505 preview in JDK 25).
- `exec-maven-plugin` uses `exec:exec` (forked JVM), not `exec:java`, because `--enable-preview` must be on the runtime JVM argv:
  ```xml
  <executable>java</executable>
  <arguments>
      <argument>--enable-preview</argument>
      <argument>-classpath</argument>
      <classpath/>
      <argument>${exec.mainClass}</argument>
  </arguments>
  ```
- `${exec.mainClass}` defaults to `com.modern.effective.java.concurrency.VirtualThreadsDemo`.
- Run a specific sample:
  ```bash
  mvn -q compile exec:exec \
      -Dexec.mainClass=com.modern.effective.java.concurrency.VirtualVsPlatform
  ```

## 7. Source layout and naming rules

- All sample classes live at `src/main/java/com/modern/effective/java/concurrency/`.
- **Filenames carry no `ItemNN_` prefix** — just the human-readable concept. The item number is mentioned inside the javadoc only.
- **Javadoc style: JEP 467 markdown comments (`///`)**, supported since JDK 23. Every sample starts with a block like:
  ```java
  /// One-line concept.
  ///
  /// Optional elaboration paragraph.
  ///
  /// See [Item NN — title](../../../../../../../../Modern_Effective_Java_Concurrency.md#item-NN-slug)
  /// for the full discussion.
  ///
  /// Run: `mvn -q compile exec:exec -Dexec.mainClass=com.modern.effective.java.concurrency.ClassName`
  public class ClassName { ... }
  ```
  The eight-level `../` escape is correct — the source file is eight directories deep from the repo root.
- Two class names deliberately shadow JDK types: `ScopedValue` (shadows `java.lang.ScopedValue`) and `RandomGenerator` (shadows `java.util.random.RandomGenerator`). Inside those files, use fully qualified names wherever needed.
- Do not introduce a `main` dispatcher; each sample is independently runnable via `-Dexec.mainClass=…`.
- Renumbering? Every anchor slug in the markdown **and** every javadoc link must update. Run the grep in rule 14 after any renumber.

## 8. File inventory (expected end-state)

| Path | Purpose |
| --- | --- |
| `pom.xml` | Maven build with JDK 25 + preview, `exec:exec` |
| `README.md` | Project overview; JDK locations; `src.zip` pointers |
| `prompt.md` | Verbatim user-prompt log (input to recreate) |
| `EffectiveJavaSystemPrompt.md` | This file (input to recreate) |
| `Modern_Effective_Java.md` | Short 10-item summary list (prompt #7 artifact) |
| `Modern_Effective_Java_Concurrency.md` | Full Bloch-style chapter — **canonical source for the PDF** |
| `Modern_Effective_Java_Concurrency.pdf` | Print-ready, letter, ~17 pages |
| `src/main/java/com/modern/effective/java/concurrency/VirtualThreadsDemo.java` | Prompt #1 umbrella demo (preserved) |
| `…/VirtualVsPlatform.java` | Item 85 sample |
| `…/VirtualThreadPerTask.java` | Item 86 sample |
| `…/TryWithResourcesExecutor.java` | Item 87 sample |
| `…/StructuredConcurrency.java` | Item 88 sample (preview) |
| `…/ScopedValue.java` | Item 89 sample (uses FQN `java.lang.ScopedValue`) |
| `…/LocksVsSynchronized.java` | Item 90 sample, idiom side-by-side |
| `…/JfrPinning.java` | Item 90 + 91 sample (RecordingStream pattern) |
| `…/FutureState.java` | Item 92 sample |
| `…/DurationAndThreadId.java` | Item 93 sample |
| `…/RandomGenerator.java` | Item 94 sample (uses FQN `java.util.random.RandomGenerator`) |

## 9. Item roster (canonical numbering)

Bloch's original *Effective Java* Chapter 11 ends at Item 84. These items extend it; they stay numbered 85 onward, and each markdown section anchor must match the javadoc links:

| # | Title |
| --- | --- |
| 85 | Prefer virtual threads for blocking I/O; keep CPU-bound work on platform threads |
| 86 | Create one virtual thread per task; never pool them |
| 87 | Close executors with try-with-resources |
| 88 | Scope concurrent subtasks with `StructuredTaskScope` |
| 89 | Prefer `ScopedValue` over `ThreadLocal` for one-way transmission |
| 90 | Understand virtual-thread pinning; observe it, don't guess (merges original 90 + 91) |
| 91 | Use Java Flight Recorder for concurrency observability |
| 92 | Drain completed futures with `state()` / `resultNow()` / `exceptionNow()` |
| 93 | Use `Duration` overloads; prefer `threadId()`; forget `stop` / `suspend` / `resume` |
| 94 | Pick a splittable `RandomGenerator` for parallel streams |

Intro line in the markdown reads "**ten** items." If you add or merge items, update that count, renumber subsequent items, update every javadoc link, and regenerate the PDF.

## 10. Writing voice — Bloch-style

Match *Effective Java* Chapter 11 (Items 78–84) — see rule 1.1 for where to read it. Key conventions:

- **Imperative item titles**: "Prefer X over Y", "Scope subtasks with Z".
- **Bold sentences for the rule** (not whole paragraphs). One or two per item, at the moment the rule lands.
- **Code samples labeled by comment**, never by prose: `// Broken!`, `// Correct`, `// Anti-pattern`, `// Modern`, `// Old`, `// Pinned on JDK 21–23`. The comment classifies the snippet before the reader reads it.
- **In summary** close on every item, one paragraph, restating the rule in bold.
- **Cross-references** to other items by number: "(Item 87)".
- **JDK source anchors** where possible: `java.base/java/util/concurrent/Executors.java:252`, with `@since` tag when it matters.
- **Authoritative citations only**: JEPs on openjdk.org, inside.java essays, JDK Mission Control docs, Ron Pressler / Alan Bateman / Brian Goetz talks, Red Hat and AWS Corretto developer blogs.
- Prefer **decision matrices and counter-example sections** over bullet lists of advice. Item 90 is the template: mechanics → rule → what still fails → counter-points → table → summary.

## 11. PDF generation workflow

The PDF pipeline is Python + Chrome, **not** pandoc:

```
Modern_Effective_Java_Concurrency.md
        │  Python (uv run, PEP 723 inline deps: markdown + pygments)
        ▼
  /tmp/mej_conc.html  ← styled HTML with @page CSS + Pygments highlighting
        │  Chrome headless --print-to-pdf
        ▼
Modern_Effective_Java_Concurrency.pdf
```

### 11.1 Converter script

`/tmp/md2html.py` — a standalone Python script with PEP 723 inline dependencies so `uv run` resolves `markdown` and `pygments` in an ephemeral venv. Recreate it if wiped:

```python
#!/usr/bin/env python3
# /// script
# requires-python = ">=3.10"
# dependencies = ["markdown", "pygments"]
# ///
"""Render a markdown file to a print-ready HTML document."""
import sys, pathlib, markdown

src = pathlib.Path(sys.argv[1])
dst = pathlib.Path(sys.argv[2])
title = src.stem.replace("_", " ")

md = markdown.Markdown(
    extensions=["fenced_code", "codehilite", "tables", "toc", "sane_lists", "attr_list"],
    extension_configs={
        "codehilite": {"guess_lang": False, "css_class": "codehilite"},
        "toc": {"title": "Contents", "toc_depth": "2-3"},
    },
)
body_html = md.convert(src.read_text())

from pygments.formatters import HtmlFormatter
pygments_css = HtmlFormatter(style="default").get_style_defs(".codehilite")

css = """
@page { size: Letter; margin: 0.75in 0.7in 0.85in 0.7in;
  @bottom-center { content: counter(page) " / " counter(pages);
                   font-family: -apple-system, sans-serif; font-size: 9pt; color: #666; } }
html { font-size: 11pt; }
body { font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", "Helvetica Neue", Arial, sans-serif;
       line-height: 1.5; color: #222; max-width: 7.1in; margin: 0 auto; }
h1 { font-size: 22pt; border-bottom: 2px solid #333; padding-bottom: 0.2em; margin-top: 0; }
h2 { font-size: 15pt; margin-top: 1.6em; padding-top: 0.2em; border-top: 1px solid #ccc;
     page-break-before: auto; page-break-after: avoid; }
h3 { font-size: 12.5pt; page-break-after: avoid; }
h4 { font-size: 11.5pt; page-break-after: avoid; }
p, li { font-size: 11pt; }
strong { color: #111; }
code { font-family: "JetBrains Mono", "SF Mono", Menlo, Consolas, monospace; font-size: 9.5pt;
       background: #f3f3f3; padding: 0.1em 0.3em; border-radius: 3px; }
pre { background: #f7f7f8; border: 1px solid #e1e1e4; border-radius: 4px;
      padding: 0.7em 0.9em; overflow-x: auto; page-break-inside: avoid;
      font-size: 9pt; line-height: 1.4; }
pre code { background: none; padding: 0; font-size: 9pt; }
blockquote { border-left: 3px solid #bbb; color: #444; padding-left: 0.9em; margin: 1em 0; }
table { border-collapse: collapse; width: 100%; margin: 1em 0; font-size: 10pt; page-break-inside: avoid; }
th, td { border: 1px solid #ccc; padding: 0.45em 0.7em; text-align: left; vertical-align: top; }
th { background: #f1f1f3; }
hr { border: none; border-top: 1px solid #ddd; margin: 1.8em 0; }
a { color: #0a58ca; text-decoration: none; }
a:hover { text-decoration: underline; }
.toc { background: #fafafa; border: 1px solid #e1e1e4; border-radius: 4px;
       padding: 0.5em 1em; margin: 1em 0 2em 0; font-size: 10pt; }
.toc ul { padding-left: 1.2em; margin: 0.3em 0; }
.toc > ul { padding-left: 0; list-style: none; }
.toc .toctitle { font-weight: bold; font-size: 11pt; margin-bottom: 0.3em; }
""" + pygments_css

html = f"""<!doctype html>
<html lang="en"><head><meta charset="utf-8"><title>{title}</title>
<style>{css}</style></head><body>{body_html}</body></html>
"""
dst.write_text(html)
print(f"wrote {dst} ({dst.stat().st_size:,} bytes)")
```

### 11.2 Regeneration command

Run after any markdown edit:

```bash
uv run --quiet /tmp/md2html.py \
    /Users/mohan.narayanaswamy/git/modern_java/Modern_Effective_Java_Concurrency.md \
    /tmp/mej_conc.html
"/Applications/Google Chrome.app/Contents/MacOS/Google Chrome" \
    --headless --disable-gpu --no-pdf-header-footer \
    --print-to-pdf=/Users/mohan.narayanaswamy/git/modern_java/Modern_Effective_Java_Concurrency.pdf \
    "file:///tmp/mej_conc.html"
```

CSS choices baked into the script: letter page, 0.7–0.85 inch margins, 11 pt body / 9 pt code, Pygments default theme, page-break-avoid on code blocks and tables, page-break-after:avoid on headings, page number footer. Do not strip these without asking.

## 12. User preferences observed

- **Latest JDK wherever reasonable.** JDK 25 is the default; older JDKs are for library-compat discussion only.
- **Parallel research via subagents** (see rule 5). Prefer this pattern over sequential research.
- **Humanly readable filenames** — no `ItemNN_` prefixes. Item number goes in the javadoc, not the filename.
- **JEP 467 markdown doc comments (`///`)** are the preferred javadoc style.
- **Counter-examples and decision matrices** are valued; straight prose advice is not. When extending an item, add a "common confusions" or "counter-points" section.
- **Comprehensive over terse** — on JFR-type items the user explicitly asked for long-form content. Do not cut corners on observability, workflow, or custom-event sections.
- **PDF regenerated on every material change** to the markdown.
- **Printable output matters** — page breaks, typography, code-block legibility are first-class concerns.

## 13. Don'ts

- Do **not** revert the artifact id `modern-java` back to anything else; it was chosen explicitly in prompt #11.
- Do **not** remove `--enable-preview` from the compiler or the exec plugin; `StructuredTaskScope` depends on it through JDK 25.
- Do **not** pool virtual threads in any sample code. The project teaches the opposite.
- Do **not** introduce `Thread.stop`, `suspend`, or `resume` anywhere — they are gone or stubs in JDK 25 and the chapter calls them out.
- Do **not** regenerate the PDF with pandoc or wkhtmltopdf; stick to the Python + Chrome pipeline so typography stays consistent.
- Do **not** rewrite items into Medium-style listicle format. The voice is Bloch's.
- Do **not** change markdown anchor slugs without updating every javadoc link in `src/main/java/**/*.java`. Use the grep in rule 14 after any renumber.
- Do **not** commit or push anything without explicit instruction; the repo is not a git repo at time of writing.
- Do **not** fetch the Effective Java chapter PDF from the internet. The user's licensed copy at `/Users/mohan.narayanaswamy/Documents/concurrency.pdf` is the only acceptable source for style reference.

## 14. Sanity commands

```bash
# Activate the right JDK
export JAVA_HOME=/opt/homebrew/opt/openjdk && export PATH=$JAVA_HOME/bin:$PATH

# Compile everything (JDK 25 + preview)
mvn -q clean compile

# Run a specific sample
mvn -q exec:exec -Dexec.mainClass=com.modern.effective.java.concurrency.VirtualVsPlatform

# List every section in the chapter
grep -n '^## Item' Modern_Effective_Java_Concurrency.md

# Find stale ItemNN_ references anywhere in the tree
grep -rn 'Item[0-9][0-9]_' src Modern_Effective_Java*.md README.md 2>/dev/null

# Verify every javadoc link resolves to an anchor that exists in the markdown
grep -rn 'Modern_Effective_Java_Concurrency.md#' src/

# Regenerate PDF
uv run --quiet /tmp/md2html.py Modern_Effective_Java_Concurrency.md /tmp/mej_conc.html
"/Applications/Google Chrome.app/Contents/MacOS/Google Chrome" --headless --disable-gpu \
  --no-pdf-header-footer \
  --print-to-pdf=Modern_Effective_Java_Concurrency.pdf file:///tmp/mej_conc.html

# Count PDF pages without pdftoppm installed
python3 -c "
import re
print('pages:', len(re.findall(rb'/Type\s*/Page\b[^s]',
    open('Modern_Effective_Java_Concurrency.pdf','rb').read())))"
```

## 15. Known one-offs and gotchas

- **`.idea/workspace.xml` may contain stale `ItemNN_` references.** It is IDE state and will self-correct on next IntelliJ open; ignore the grep hit.
- **Chrome headless renders without network access**, which is fine because the markdown-to-HTML step inlines all CSS (Pygments + custom). Do not add `<link rel=stylesheet>` to remote CDNs.
- **`uv`'s PEP 723 inline metadata** in `/tmp/md2html.py` declares `markdown` and `pygments`. Pyright will warn about missing imports because it cannot see uv's ephemeral env; the warnings are harmless.
- **The `RandomGenerator` sample** uses `root.splits(splits)` where `root` is cast to `java.util.random.RandomGenerator.SplittableGenerator`. The `splits(int)` overload is the eight-way explicit split; `rngs()` is the unbounded variant. Do not "clean up" the cast.
- **`ScopedValue` propagates into `StructuredTaskScope.fork(...)` subtasks, not into arbitrary `Executor.submit(...)` threads.** The sample shows the correct fork-based inheritance; an earlier draft used `Executors.newVirtualThreadPerTaskExecutor` and demonstrated the *wrong* behavior (`[- | -]` in subtasks). Do not regress that.
- **Item 90's decision matrix** is the reader's most-quoted artifact; keep it intact and keep the en-dash in the "JDK 21 – 23" row (it is `–`, not `-`).
- **Item 91 lists four JFR collection modes.** If you add a fifth, update the summary, the workflow section, and the "four" count simultaneously.
- **The class `VirtualThreadsDemo`** is the prompt #1 artifact. It survives the later refactors and is the `exec.mainClass` default in `pom.xml`. Do not delete it when you add the per-item samples.

## 16. Recreation recipe — one-line summary

> Given `prompt.md` + this manual + the prerequisite tooling + the Effective Java Chapter 11 PDF at `~/Documents/concurrency.pdf`, replay each prompt in order using the decoder in rule 4, honor the conventions in rules 6–10, regenerate the PDF (rule 11) after any markdown edit, verify with the commands in rule 14.

---

Hand this file plus `prompt.md` to a fresh session and the agent should be able to reconstruct the entire repository end-to-end.
