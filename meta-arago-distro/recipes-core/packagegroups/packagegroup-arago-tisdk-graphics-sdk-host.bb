DESCRIPTION = "Task to install graphics sources in SDK"
LICENSE = "MIT"
PR = "r7"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

GRAPHICS_RDEPENDS = "${@bb.utils.contains('MACHINE_FEATURES','gpu','${PREFERRED_PROVIDER_virtual/gpudriver}-src','',d)}"

GRAPHICS_RDEPENDS_append_omap-a15 = "\
    ti-gc320-driver-src \
"

RDEPENDS_${PN} = "\
    ${GRAPHICS_RDEPENDS} \
"
