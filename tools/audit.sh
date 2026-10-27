#!/bin/bash
# Audit bao mat cho ban port: quyen, URL, API mang, exec/reflection, tracker.
ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
cd "$ROOT_DIR" || exit 1
AUTH="src src_no_quickstep src_plugins res res_graphene flags"
LIBS="modules platform_frameworks_libs_systemui systemUI wmshell quickstep compatLib concurrent hidden-api androidx-lib dagger protos"

FA=$(find $AUTH -type f \( -name '*.java' -o -name '*.kt' -o -name '*.xml' -o -name '*.kts' -o -name '*.json' \) 2>/dev/null)
FL=$(find $LIBS -type f \( -name '*.java' -o -name '*.kt' \) -not -path '*/tests/*' -not -path '*/build/*' -not -path '*Test*' 2>/dev/null)

echo "########## [1] PERMISSION TRONG MANIFEST GOC ##########"
grep -n 'uses-permission\|<permission\|uses-feature\|android:sharedUserId' AndroidManifest.xml AndroidManifest-common.xml

echo
echo "########## [2] INTERNET / cleartext / networkSecurityConfig ##########"
grep -n 'permission.INTERNET\|usesCleartextTraffic\|networkSecurityConfig' AndroidManifest.xml AndroidManifest-common.xml
echo "$FA" | xargs -r grep -n 'permission.INTERNET\|usesCleartextTraffic\|networkSecurityConfig' 2>/dev/null | head -20
echo "-- het (khong in dong nao = KHONG CO INTERNET) --"

echo
echo "########## [3] URL http/https trong src+res+flags (minh kiem soat) ##########"
echo "$FA" | xargs -r grep -noh 'https\?://[^"'"'"' <>)]*' 2>/dev/null | sort | uniq -c | sort -rn | head -40
echo "-- het --"

echo
echo "########## [4] URL http/https trong cac lib AOSP duoc compile ##########"
echo "$FL" | xargs -r grep -noh 'https\?://[^"'"'"' <>)]*' 2>/dev/null | sort | uniq -c | sort -rn | head -40
echo "-- het --"

echo
echo "########## [5] API MANG trong src+res+flags ##########"
echo "$FA" | xargs -r grep -nE 'HttpURLConnection|URLConnection|openConnection|okhttp|OkHttp|retrofit|new URL\(|SocketFactory|WebView|loadUrl|java\.net\.|DatagramSocket|SslSocket' 2>/dev/null | head -30
echo "-- het --"

echo
echo "########## [6] API MANG trong lib AOSP ##########"
echo "$FL" | xargs -r grep -nE 'HttpURLConnection|openConnection|new URL\(|WebView|loadUrl|java\.net\.' 2>/dev/null | head -30
echo "-- het --"

echo
echo "########## [7] EXEC / native / dex / Class.forName trong src ##########"
echo "$FA" | xargs -r grep -nE 'getRuntime\(\)\.exec|ProcessBuilder|DexClassLoader|System\.loadLibrary|Class\.forName' 2>/dev/null | head -30
echo "-- het --"

echo
echo "########## [8] DEPENDENCY: tracker/analytics/mang ##########"
grep -rn 'firebase\|crashlytics\|analytics\|sentry\|bugsnag\|play-services\|amplitude\|mixpanel\|okhttp\|retrofit\|volley' gradle/libs.versions.toml build.gradle settings.gradle 2>/dev/null
for f in $(find . -maxdepth 4 -name 'build.gradle' -not -path '*/build/*'); do grep -Hn 'firebase\|crashlytics\|analytics\|sentry\|play-services\|okhttp\|retrofit\|volley' "$f"; done | head -20
echo "-- het (khong in dong nao = khong co tracker) --"

echo
echo "########## [9] SCRIPT tools CO GOI MANG? ##########"
grep -rnE 'requests\.|urllib|socket|curl |wget ' tools/*.py tools/*.sh 2>/dev/null | head -20
echo "-- het --"

echo
echo "########## [10] FILE assets/co the chua JS/HTML ##########"
find src res res_graphene -type d -name assets 2>/dev/null
