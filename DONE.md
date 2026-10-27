# DONE — Graphene Launcher3 branch 17 -> user app (audit + patches xong)

## Ket qua audit tung file (khong doan mo)
1. TIS/Recents: HOOK_AUDIT_1_RECENTS.md + PATCH1_RecentsModel.txt + PATCH2_TIS.txt
   + GrapheneApp.java (copy LawnchairApp.kt:60-73,192-214,244-278)
2. Widget BIND_APPWIDGET: AddItemActivity.kt:210-216 da co startBindFlow,
   khong sua (PATCH3_Widget.txt)
3. Hidden API: prebuilts 4 jar -16 + refine, khong sua code (HOOK_AUDIT_2)
4. aconfig/Protolog: copy flags/ Lawnchair, bo src_protolog neu loi (PATCH4)
5. Manifest 17: GRID_CONTROL knownSigner giu nguyen, doi application name
   sang GrapheneApp (PATCH5 + GrapheneAppManifest.xml)

## File quan trong
- app.build.gradle.example: skeleton chuan Lawnchair 16-dev (SDK 37)
- settings.gradle.new: modules chuan
- BUILD_STEPS.txt: lenh copy-paste de build
- patches/GrapheneApp.java: gate recents nhu Lawnchair

## Buoc tiep theo (may tao khong chay duoc lenh copy nang, terminal bi treo)
Chay tung dong trong BUILD_STEPS.txt tren may that, roi:
./gradlew :app:assembleGrapheneNoQuickstepStandaloneDebug
Flavor noQuickstep = HOME thuong kieu Neo, khong can root.
