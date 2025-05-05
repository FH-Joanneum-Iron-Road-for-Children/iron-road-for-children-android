package at.irfc.app.data.remote.dto

import at.irfc.app.data.local.entity.Gallery
import kotlinx.serialization.Serializable

@Serializable
class GalleryDto(
    val galleryId: Long,
    val altText: String,
    val path: String
)

fun GalleryDto.toGallery(): Gallery {
    return Gallery(
        id = this.galleryId,
        title = this.altText,
        path = this.path
    )
}
