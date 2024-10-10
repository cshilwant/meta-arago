FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI += " \
	file://network.cfg \
	file://utils.cfg \
	file://selinux_fragment.cfg \
"

PR:append = ".arago22"
