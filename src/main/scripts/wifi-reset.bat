@echo off
set ssid="Sambu_2GEXT"
echo The following interfaces are preset
netsh interface show interface
echo Resetting the Wi-Fi interface...
netsh wlan disconnect
netsh interface set interface Wi-Fi disable
echo Command completed. Status:
netsh interface show interface
echo Enabling interface again...
netsh interface set interface Wi-Fi enable
echo Successful reset
ping 127.0.0.1 -n 3 > nul
netsh wlan connect name=%ssid%