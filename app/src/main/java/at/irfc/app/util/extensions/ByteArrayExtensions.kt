package at.irfc.app.util.extensions

import java.security.MessageDigest

fun ByteArray.sha256(): String {
    val md = MessageDigest.getInstance("SHA-256")
    val hex = md.digest(this)
    return hex.encodeHexString()
}

@Suppress("ImplicitDefaultLocale", "MagicNumber")
fun ByteArray.encodeHexString(): String {
    val hex = StringBuilder(this.size * 2)
    for (b in this) hex.append(String.format("%02x", b.toInt() and 0xFF))
    return hex.toString()
}
