# R8 rules for Mantis.
#
# No custom rules are needed: Hilt, Compose, Navigation and kotlinx.serialization ship their
# own consumer rules, and mXparser is only called directly (no reflection).
# The release build is verified by running every calculator mode with R8 enabled.

# Keep line numbers so Play Console crash reports point at the right source lines.
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile
