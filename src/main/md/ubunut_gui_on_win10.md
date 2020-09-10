# Recover root password
* boot in recovery mode and select root
* mount -rw -remount/
* passwd <<user_name>>

echo "export DISPLAY=:0.0" >> ~/.bashrc

sudo apt-get install xvfb
Xvfb :0 -screen 0 1920x1080x24 +extension GLX -nolisten tcp -dpi 96

apt-get install ubuntu-desktop
apt-get install unity
apt-get install compiz-core
apt-get install compizconfig-settings-manager


service --status-all
service ssh --full-restart

@/etc/ssh/sshd_config
Match User nikias
ForceCommand internal-sftp
PasswordAuthentication yes
ChrootDirectory /mnt/d/git/Thales/ThalesExcelProcess/
PermitTunnel no
AllowAgentForwarding no
AllowTcpForwarding no
X11Forwarding no


## Reference

* [Windows Subsystem for Linux - Add desktop experience to Ubuntu](https://www.tenforums.com/tutorials/144208-windows-subsystem-linux-add-desktop-experience-ubuntu.html)
