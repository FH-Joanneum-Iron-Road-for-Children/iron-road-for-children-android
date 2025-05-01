package at.irfc.app.data.remote.dto

import at.irfc.app.data.local.entity.Countdown
import kotlinx.serialization.Serializable

@Serializable
class CountdownDto(
    val countdownId: Long,
    val endDateTimeInUTC: Long
)

fun CountdownDto.toCountdown(): Countdown {
    return Countdown(
        id = this.countdownId,
        time = this.endDateTimeInUTC
    )
}
