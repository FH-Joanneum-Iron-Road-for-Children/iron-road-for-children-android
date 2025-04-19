package at.irfc.app.data.remote.dto

import at.irfc.app.data.local.entity.GalleryPicture
import kotlinx.serialization.Serializable

@Serializable
class PicturesDto(
    val pictureId: Long,
    val altText: String,
    val path: String
)

fun PicturesDto.toGalleryPicture(): GalleryPicture {
    return GalleryPicture(
        id = this.pictureId,
        title = this.altText,
        path = this.path
    )
}
