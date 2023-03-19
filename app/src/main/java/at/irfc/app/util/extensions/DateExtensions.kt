package at.irfc.app.util.extensions

import java.util.*
import kotlin.time.Duration

operator fun Date.minus(amount: Duration): Date {
    return Date(this.time - amount.inWholeMilliseconds)
}
