DESCRIPTION = "Matrix GUI Application launcher"
HOMEPAGE = "https://gforge.ti.com/gf/project/matrix-gui-v2/"

LICENSE = "BSD MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=a886c9ef769b2d8271115d2502512e5d"

SECTION = "multimedia"

PR = "r23"

INITSCRIPT_NAME = "matrix-gui-2.0"
INITSCRIPT_PARAMS = "defaults 97"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit update-rc.d

BRANCH ?= "master"
SRCREV = "d65a5e1d178bb30653f16322bebf7e7ee530d613"

MATRIX_INITSCRIPT = "${@base_conditional('QT_PROVIDER', 'qt5', base_contains('DISTRO_FEATURES', 'wayland', 'init', 'init.eglfs', d), 'init', d)}"

SRC_URI = "git://gitorious.org/matrix-gui-v2/matrix-gui-v2.git;protocol=http;branch=${BRANCH} \
           file://${MATRIX_INITSCRIPT} \
           file://php.ini"

require matrix-gui-paths.inc

S = "${WORKDIR}/git"

MATRIX_FLAGS = "${@base_conditional('QT_PROVIDER','qt5','','-qws',d)}"
SWITCH_FOREGROUND_VT = "${@base_conditional('QT_PROVIDER','qt5','','chvt 4',d)}"
do_install(){
	install -d ${D}${MATRIX_BASE_DIR}
	install -d ${D}${MATRIX_WEB_DIR}
	cp -rf ${S}/* ${D}${MATRIX_WEB_DIR}

	# Install our php.ini file
	install -m 0644 ${WORKDIR}/php.ini ${D}${MATRIX_BASE_DIR}/

	# Set the proper path in the init script
	sed -i -e s=__MATRIX_WEB_DIR__=${MATRIX_WEB_DIR}= ${WORKDIR}/${MATRIX_INITSCRIPT}
	sed -i -e "s/__MATRIX_FLAGS__/\"${MATRIX_FLAGS}\"/" ${WORKDIR}/${MATRIX_INITSCRIPT}
	sed -i -e "s/__SWITCH_FOREGROUND_VT__/${SWITCH_FOREGROUND_VT}/" ${WORKDIR}/${MATRIX_INITSCRIPT}

	# Install the init script
	# TODO: replace init script with systemd files
	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/${MATRIX_INITSCRIPT} ${D}${sysconfdir}/init.d/matrix-gui-2.0
}

GUIDEPS = "matrix-gui-browser refresh-screen"
GUIDEPS_keystone = ""

RDEPENDS_${PN} += "matrix-lighttpd-config lighttpd lighttpd-module-cgi lighttpd-module-compress lighttpd-module-expire php php-cgi php-cli ${GUIDEPS}"

FILES_${PN} += "${MATRIX_BASE_DIR}/*"
