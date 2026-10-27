#!/usr/bin/env python3
"""
Vendor hoa cac mau private cua framework (androidprv:color/*) cho ban USER APP.

Van de: dynamiccolors (AOSP) reference @androidprv:color/... - chi resolve duoc khi
build voi platform_apis (system app). User app build bang SDK -> AAPT loi
"resource androidprv:color/... not found".

Cach sua: thay @androidprv:color/X bang @color/dcv_X, roi dinh nghia dcv_X trong
chinh module bang gia tri that lay tu AOSP (core/res/res/values/colors.xml).
"""
import re
import pathlib

WORK = pathlib.Path(__file__).resolve().parent
DC_RES = WORK.parent / "platform_frameworks_libs_systemui" / "dynamiccolors" / "res"

# 1. Doc gia tri that tu found.txt (da curl tu AOSP 17)
vals = {}
for line in (WORK / "found.txt").read_text().splitlines():
    m = re.search(r'<color name="([^"]+)">([^<]+)</color>', line)
    if m:
        vals[m.group(1)] = m.group(2)

print(f"doc duoc {len(vals)} mau tu AOSP")

# 2. Sinh file mau vendored (ca light va dark deu co du 54 ten -> khong bao gio thieu)
def write_colors(path, note):
    lines = [
        '<?xml version="1.0" encoding="utf-8"?>',
        '<!--',
        f'  {note}',
        '  SINH TU DONG boi tools/vendor_private_colors.py - KHONG sua tay.',
        '  Gia tri goc: AOSP frameworks/base core/res/res/values/colors.xml (branch 17).',
        '-->',
        '<resources>',
    ]
    for name in sorted(vals):
        lines.append(f'    <color name="dcv_{name}">{vals[name]}</color>')
    lines.append('</resources>')
    lines.append('')
    path.write_text("\n".join(lines))
    print(f"ghi {path} ({len(vals)} mau)")

write_colors(DC_RES / "values" / "dcv_colors.xml", "Mau private cua framework - ban light.")
write_colors(DC_RES / "values-night" / "dcv_colors.xml", "Mau private cua framework - ban dark.")

# 3. Thay reference trong res cua dynamiccolors
count = 0
for f in list(DC_RES.glob("values*/*.xml")):
    if f.name == "dcv_colors.xml":
        continue
    txt = f.read_text()
    new = txt.replace("@androidprv:color/", "@color/dcv_")
    if new != txt:
        n = txt.count("@androidprv:color/")
        count += n
        f.write_text(new)
        print(f"patch {f.relative_to(WORK.parent)}: {n} reference")

print(f"TONG: {count} reference da doi sang @color/dcv_*")
