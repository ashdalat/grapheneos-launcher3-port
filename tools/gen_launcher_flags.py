#!/usr/bin/env python3
"""
Sinh 5 file aconfig flags (Flags / FeatureFlags / FeatureFlagsImpl /
CustomFeatureFlags / FakeFeatureFlagsImpl) cho package com.android.launcher3
tu cac file .aconfig cua GrapheneOS Launcher3 branch 17.

AOSP sinh cac file nay bang aconfig tool trong Soong; build Gradle khong co
aconfig tool nen phai sinh tay, giong dinh dang AOSP (mau: flags/src cua Lawnchair).
"""
import re
import pathlib

PORT = pathlib.Path(__file__).resolve().parent.parent
ACONFIG_DIR = PORT / "aconfig"
OUT_DIR = PORT / "flags" / "src" / "com" / "android" / "launcher3"

PKG = "com.android.launcher3"
DEFAULT = True  # AOSP Launcher3 release bat phan lon flag

flags = []
for f in sorted(ACONFIG_DIR.glob("*.aconfig")):
    txt = f.read_text()
    for m in re.finditer(r'flag\s*\{(.*?)\}', txt, re.S):
        nm = re.search(r'name:\s*"([^"]+)"', m.group(1))
        if nm:
            flags.append(nm.group(1))
flags = sorted(set(flags))
print(f"tim thay {len(flags)} flag trong {ACONFIG_DIR}")


def camel(snake):
    parts = snake.split("_")
    return parts[0] + "".join(p[:1].upper() + p[1:] for p in parts[1:])


def const(snake):
    return "FLAG_" + snake.upper()


default_str = "true" if DEFAULT else "false"
HEADER = (
    "/*\n"
    " * Sinh tu dong boi tools/gen_launcher_flags.py - KHONG sua tay.\n"
    f" * Nguon: GrapheneOS Launcher3 branch 17 ({len(flags)} flag).\n"
    " */\n"
    f"package {PKG};\n"
)

# ---------- Flags.java ----------
out = [HEADER, "", "/** @hide */", "public final class Flags {"]
for f in flags:
    out.append(f'    /** @hide */')
    out.append(f'    public static final String {const(f)} = "{PKG}.{f}";')
out.append("")
for f in flags:
    out.append("    /** @hide */")
    out.append(f"    public static boolean {camel(f)}() {{")
    out.append(f"        return FEATURE_FLAGS.{camel(f)}();")
    out.append("    }")
    out.append("")
out.append("    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();")
out.append("")
out.append("}")
(OUT_DIR / "Flags.java").write_text("\n".join(out) + "\n")

# ---------- FeatureFlags.java ----------
out = [HEADER, "", "/** @hide */", "public interface FeatureFlags {"]
for f in flags:
    out.append(f"    boolean {camel(f)}();")
out.append("}")
(OUT_DIR / "FeatureFlags.java").write_text("\n".join(out) + "\n")

# ---------- FeatureFlagsImpl.java ----------
out = [HEADER, "", "/** @hide */",
       "public final class FeatureFlagsImpl implements FeatureFlags {"]
for f in flags:
    out.append("    @Override")
    out.append(f"    public boolean {camel(f)}() {{")
    out.append(f"        return {default_str};")
    out.append("    }")
    out.append("")
out.append("}")
(OUT_DIR / "FeatureFlagsImpl.java").write_text("\n".join(out) + "\n")

# ---------- CustomFeatureFlags.java ----------
out = [HEADER, "",
       "import java.util.Arrays;",
       "import java.util.HashSet;",
       "import java.util.Set;",
       "import java.util.function.BiPredicate;",
       "import java.util.function.Predicate;",
       "/** @hide */",
       "public class CustomFeatureFlags implements FeatureFlags {",
       "",
       "    private BiPredicate<String, Predicate<FeatureFlags>> mGetValueImpl;",
       "",
       "    public CustomFeatureFlags(BiPredicate<String, Predicate<FeatureFlags>> getValueImpl) {",
       "        mGetValueImpl = getValueImpl;",
       "    }"]
for f in flags:
    out.append("    @Override")
    out.append(f"    public boolean {camel(f)}() {{")
    out.append(f"        return getValue(Flags.{const(f)}, FeatureFlags::{camel(f)});")
    out.append("    }")
    out.append("")
out.append("    protected boolean getValue(String flagName, Predicate<FeatureFlags> getter) {")
out.append("        return mGetValueImpl.test(flagName, getter);")
out.append("    }")
out.append("")
out.append("    public Set<String> getFlagNames() {")
out.append("        return new HashSet<>(Arrays.asList(")
for i, f in enumerate(flags):
    sep = "));" if i == len(flags) - 1 else ","
    out.append(f"                Flags.{const(f)}{sep}")
out.append("    }")
out.append("}")
(OUT_DIR / "CustomFeatureFlags.java").write_text("\n".join(out) + "\n")
print("da ghi Flags/FeatureFlags/FeatureFlagsImpl/CustomFeatureFlags")


# ---------- FakeFeatureFlagsImpl.java ----------
out = [HEADER, "",
       "import java.util.HashMap;",
       "import java.util.Map;",
       "import java.util.function.Predicate;",
       "/** @hide */",
       "public class FakeFeatureFlagsImpl extends CustomFeatureFlags {",
       "    private final Map<String, Boolean> mFlagMap = new HashMap<>();",
       "    private final FeatureFlags mDefaults;",
       "",
       "    public FakeFeatureFlagsImpl() {",
       "        this(null);",
       "    }",
       "",
       "    public FakeFeatureFlagsImpl(FeatureFlags defaults) {",
       "        super(null);",
       "        mDefaults = defaults;",
       "        for (String flagName : getFlagNames()) {",
       "            mFlagMap.put(flagName, null);",
       "        }",
       "    }",
       "",
       "    @Override",
       "    protected boolean getValue(String flagName, Predicate<FeatureFlags> getter) {",
       "        Boolean value = this.mFlagMap.get(flagName);",
       "        if (value != null) {",
       "            return value;",
       "        }",
       "        if (mDefaults != null) {",
       "            return getter.test(mDefaults);",
       "        }",
       "        throw new IllegalArgumentException(flagName + \" is not set\");",
       "    }",
       "",
       "    public void setFlag(String flagName, boolean value) {",
       "        if (!this.mFlagMap.containsKey(flagName)) {",
       "            throw new IllegalArgumentException(\"no such flag \" + flagName);",
       "        }",
       "        this.mFlagMap.put(flagName, value);",
       "    }",
       "",
       "    public void resetAll() {",
       "        for (Map.Entry<String, Boolean> entry : mFlagMap.entrySet()) {",
       "            entry.setValue(null);",
       "        }",
       "    }",
       "}"]
(OUT_DIR / "FakeFeatureFlagsImpl.java").write_text("\n".join(out) + "\n")

print(f"da ghi 5 file vao {OUT_DIR.relative_to(PORT)}")
print("vi du vai flag:", ", ".join(flags[:5]))
