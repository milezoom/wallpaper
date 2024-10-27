#!/bin/bash

SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )
java -jar "${SCRIPT_DIR}"/downloader/app/build/libs/app.jar

DISPLAY=:0 DBUS_SESSION_BUS_ADDRESS=unix:path=/run/user/1000/bus qdbus6 org.kde.plasmashell /PlasmaShell \
    org.kde.PlasmaShell.evaluateScript "
    var Desktops = desktops();                                                                                                                       
    for (i=0;i<Desktops.length;i++) {
        d = Desktops[i];
        d.wallpaperPlugin = 'org.kde.image';
        d.currentConfigGroup = Array('Wallpaper','org.kde.image','General');
        d.writeConfig('Image', '${HOME}/Pictures/wallpaper-desktop.jpg');
        d.reloadConfig();
    }"