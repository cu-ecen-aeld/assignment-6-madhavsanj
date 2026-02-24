SUMMARY = "AESD assignments (aesdsocket)"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

# Our init script lives in meta-aesd/recipes-aesd-assignments/files/
FILESEXTRAPATHS:prepend := "${THISDIR}/../files:"

PV = "1.0+git${SRCPV}"

# --- YOU MUST SET THESE TWO ---
# Prefer https to avoid ssh-agent complexity during bitbake fetch
SRC_URI = "git@github.com:cu-ecen-aeld/assignments-3-and-later-madhavsanj.git"

SRC_URI = "git://github.com/cu-ecen-aeld/assignments-3-and-later-madhavsanj.git;protocol=https;branch=main \
           file://aesdsocket-init \
          "
SRCREV = "da303aae5837a439637c4703461787272adeed78"


# -----------------------------

# Fetches into ${WORKDIR}/git; build from server/ inside your repo
S = "${WORKDIR}/git/server"

# Ensure aesdsocket is installed and init script is registered
inherit update-rc.d

INITSCRIPT_NAME = "aesdsocket"
INITSCRIPT_PARAMS = "defaults 99"

do_configure() {
    :
}

do_compile() {
    oe_runmake
}

do_install() {
    # Install aesdsocket binary
    install -d ${D}${bindir}
    install -m 0755 ${S}/aesdsocket ${D}${bindir}/aesdsocket

    # Install init script as /etc/init.d/aesdsocket
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/aesdsocket-init ${D}${sysconfdir}/init.d/aesdsocket
}

FILES:${PN} += "${bindir}/aesdsocket ${sysconfdir}/init.d/aesdsocket"