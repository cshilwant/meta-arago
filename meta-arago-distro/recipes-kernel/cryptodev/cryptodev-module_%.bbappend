# Bring in fixes including https://github.com/cryptodev-linux/cryptodev-linux/pull/55
# just pull 1.12 revision, everything else remains the same.

PV = "1.12"
PR_append = ".arago0"
SRCREV = "e0c25e289d6baf1d83c2b9cb523d3bc237d0c0c9"

KERNEL_MODULE_AUTOLOAD += "cryptodev"
