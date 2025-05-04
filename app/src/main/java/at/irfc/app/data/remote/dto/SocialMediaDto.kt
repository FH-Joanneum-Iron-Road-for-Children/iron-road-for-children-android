package at.irfc.app.data.remote.dto

import at.irfc.app.data.local.entity.SocialMedia
import kotlinx.serialization.Serializable

@Serializable
class SocialMediaDto(
    val socialMediaId: Long,
    val title: String,
    val link: String
)

fun SocialMediaDto.toSocialMedia(): SocialMedia {
    return SocialMedia(
        id = this.socialMediaId,
        title = this.title,
        path = this.link
    )
}
