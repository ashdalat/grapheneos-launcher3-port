#!/bin/bash
# Quet chuoi trong toan bo dex cua APK: URL, ten class mang/tracker, API hook.
ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
cd "$ROOT_DIR" || exit 1
APK=${1:-GrapheneLauncher-debug.apk}
OUT=docs/audit/audit_dex.txt
BIN=/tmp/dex_all.bin
STR=/tmp/dex_strings.txt
unzip -o -q "$APK" 'classes*.dex' -d /tmp/dexdir 2>/dev/null
cat /tmp/dexdir/classes*.dex > "$BIN"
strings -a "$BIN" > "$STR"
{
echo "APK: $APK"
echo "dex size: $(du -h "$BIN" | cut -f1)   strings: $(wc -l < "$STR") dong"
echo
echo "===== [1] URL trong dex ====="
grep -o 'https\?://[^ "'"'"'<>]*' "$STR" | sed 's/[,);].*$//' | sort | uniq -c | sort -rn | head -40
echo
echo "===== [2] Ten class dang nghi van (so dong chua chuoi) ====="
for pat in 'okhttp3' 'retrofit2' 'retrofit' 'coil/' 'com/topjohnwu' 'libsu' 'firebase' 'crashlytics' 'analytics' 'HttpURLConnection' 'java/net/URL' 'java/net/Socket' 'WebView' 'SslSocket' 'com/google/android/gms' 'androidx/work' 'ChickenHook' 'RestrictionBypass' 'rikka'; do
  printf '%-24s %s\n' "$pat" "$(grep -c -- "$pat" "$STR")"
done
echo
echo "===== [3] Ten host/domain xuat hien trong dex ====="
grep -oE '[a-z0-9.-]+\.(com|org|net|io|dev|me|gg|app|xyz|ru|cn)([/:]|$)' "$STR" | sort -u | head -60
} > "$OUT" 2>&1
