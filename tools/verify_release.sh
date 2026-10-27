#!/bin/bash
# Kiem tra APK RELEASE: quyen, INTERNET, debuggable, chu ky, dung luong, dex.
ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
cd "$ROOT_DIR" || exit 1
SDK_ROOT="${ANDROID_HOME:-${ANDROID_SDK_ROOT:-$HOME/Android/Sdk}}"
BT=$(ls -d "$SDK_ROOT"/build-tools/* 2>/dev/null | tail -1)
DIR=build/outputs/apk/noQuickstepStandalone/release
APK=$(ls -t $DIR/*.apk 2>/dev/null | head -1)
OUT=docs/audit/verify_release.txt
{
echo "APK: $APK"
echo "build-tools: $BT"
echo "===== [1] Dung luong / sha256 ====="
ls -l "$APK"
du -h "$APK"
sha256sum "$APK"
echo "debug APK de so sanh: $(du -h GrapheneLauncher-debug.apk | cut -f1)  ($(stat -c%s GrapheneLauncher-debug.apk) byte)"
echo
echo "===== [2] badging: package/sdk/permission/debuggable ====="
"$BT/aapt2" dump badging "$APK" | grep -i 'package:\|sdkVersion\|targetSdk\|permission\|application-debuggable\|application-label:'
echo
echo "===== [3] CO QUYEN INTERNET KHONG? ====="
if "$BT/aapt2" dump badging "$APK" | grep -qi 'permission.INTERNET'; then echo "!!! CO INTERNET !!!"; else echo "KHONG CO android.permission.INTERNET (sach)"; fi
echo
echo "===== [4] aapt2 dump permissions ====="
"$BT/aapt2" dump permissions "$APK"
echo
echo "===== [5] Chu ky ====="
"$BT/apksigner" verify --print-certs "$APK" 2>&1 | grep -i 'signer\|SHA-256'
echo "(cert ban debug: e2e73b9487c9390265a1cc1c3414c2b127f14609843cf8b7f7d4ddc716195f07)"
echo
echo "===== [6] Thanh phan APK: dex / so native lib ====="
unzip -l "$APK" | grep -c '[.]dex$'
unzip -l "$APK" | grep '[.]dex$' | awk '{s+=$1} END {printf "tong dex: %.1f MB\n", s/1048576}'
unzip -l "$APK" | grep -c '[.]so$'
unzip -l "$APK" | grep -iE 'assets/|[.]js$|[.]html$' | head -5
echo
echo "===== [7] Quet dex: co thu vien mang/tracker khong ====="
rm -rf /tmp/dexrel; mkdir -p /tmp/dexrel
unzip -o -q "$APK" 'classes*.dex' -d /tmp/dexrel
cat /tmp/dexrel/classes*.dex > /tmp/dexrel.bin
strings -a /tmp/dexrel.bin > /tmp/dexrel.str
for pat in 'okhttp3' 'retrofit2' 'coil/' 'com/topjohnwu' 'com/google/firebase' 'crashlytics' 'HttpURLConnection' 'java/net/Socket' 'SslSocket' 'com/google/android/gms'; do
  printf '%-22s %s\n' "$pat" "$(grep -c -- "$pat" /tmp/dexrel.str)"
done
echo
echo "===== [8] URL trong dex ban release ====="
grep -o 'https\?://[^ "'"'"'<>]*' /tmp/dexrel.str | sort -u | head -20
} > "$OUT" 2>&1
