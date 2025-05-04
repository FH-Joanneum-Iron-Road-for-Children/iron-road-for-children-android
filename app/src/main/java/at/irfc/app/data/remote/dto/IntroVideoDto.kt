package at.irfc.app.data.remote.dto

import at.irfc.app.data.local.entity.IntroVideo
import kotlinx.serialization.Serializable

@Serializable
class IntroVideoDto(
    val videoId: Long,
    val altText: String,
    val path: String
)

fun IntroVideoDto.toVideo(): IntroVideo {
    return IntroVideo(
        id = this.videoId,
        altText = this.altText,
        path = this.path
    )
}
