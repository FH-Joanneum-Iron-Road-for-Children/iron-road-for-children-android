package at.irfc.app.util.extensions

fun String.sha256(): String = this.toByteArray().sha256()
