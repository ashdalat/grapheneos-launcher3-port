#!/bin/bash
# Audit APK da build: quyen, file co the lien quan mang.
ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
cd "$ROOT_DIR" || exit 1
SDK_ROOT="${ANDROID_HOME:-${ANDROID_SDK_ROOT:-$HOME/Android/Sdk}}"
BT=$(ls -d "$SDK_ROOT"/build-tools/* 2>/dev/null | tail -1)
echo "build-tools: $BT"
APK=${1:-GrapheneLauncher-debug.apk}
echo "APK: $APK"
echo
echo "===== [A] aapt2 dump badging: uses-permission / sdk / package ====="
"$BT/aapt2" dump badging "$APK" | grep -i 'package:\|sdkVersion\|targetSdkVersion\|permission'
echo
echo "===== [B] aapt2 dump permissions ====="
"$BT/aapt2" dump permissions "$APK"
echo
echo "===== [C] APK co assets / js / html / .so / network config? ====="
unzip -l "$APK" | grep -iE 'assets/|[.]js$|[.]html$|[.]so$|network|security_config|[.]dex$' | head -40
echo
echo "===== [D] Chuoi URL trong dex (co the la hang so) ====="
"$BT/aapt2" dump strings "$APK" 2>/dev/null | grep -o 'https\?://[^"'"'"' ]*' | sort | uniq -c | sort -rn | head -30
echo "(neu trong = dex khong chua URL nao)"
echo
echo "===== [E] Dummy: so luong dex ====="
unzip -l "$APK" | grep -c '[.]dex$'
