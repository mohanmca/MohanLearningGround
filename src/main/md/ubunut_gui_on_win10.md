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