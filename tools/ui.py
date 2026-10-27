#!/usr/bin/env python3
"""Dump UI hien tai (launcher) qua adb uiautomator va liet ke node co nhan kem toa do tap.

Cach dung:  python3 _work/ui.py [filter]
  filter: chi hien node co text/content-desc chua chuoi nay (khong phan biet hoa/thuong)
"""
import re
import subprocess
import sys

import os

ADB = os.environ.get("ADB", "adb")
TMP_XML = "/tmp/ui_now.xml"


def sh(*args):
    return subprocess.run(args, capture_output=True, text=True).stdout


def center(bounds):
    m = re.match(r"\[(\d+),(\d+)\]\[(\d+),(\d+)\]", bounds)
    if not m:
        return None
    x1, y1, x2, y2 = (int(v) for v in m.groups())
    return ((x1 + x2) // 2, (y1 + y2) // 2)


def main():
    filt = sys.argv[1].lower() if len(sys.argv) > 1 else None
    sh(ADB, "shell", "uiautomator", "dump", "/sdcard/ui_now.xml")
    xml = sh(ADB, "shell", "cat", "/sdcard/ui_now.xml")
    with open(TMP_XML, "w", encoding="utf-8") as f:
        f.write(xml)

    count = 0
    for node in re.findall(r"<node[^>]*>", xml):
        def attr(name):
            m = re.search(name + r'="([^"]*)"', node)
            return m.group(1) if m else ""

        text = attr("text")
        desc = attr("content-desc")
        if not (text or desc):
            continue
        label = text or desc
        cls = attr("class")
        if filt and filt not in label.lower() and filt not in cls.lower():
            continue
        print(f"{label!r:42} {cls:42} tap={center(attr('bounds'))}")
        count += 1
    print(f"-- {count} node co nhan (package={re.search('package=.([^\\\"]+)', xml).group(1) if re.search('package=.[^\"]+', xml) else '?'}) --")


main()
