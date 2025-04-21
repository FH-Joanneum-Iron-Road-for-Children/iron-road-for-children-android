package at.irfc.app.data.repository

import at.irfc.app.data.local.dao.GalleryPictureDao
import at.irfc.app.data.local.entity.GalleryPicture
import at.irfc.app.data.remote.api.PictureApi
import at.irfc.app.data.remote.dto.PicturesDto
import at.irfc.app.data.remote.dto.toGalleryPicture
import at.irfc.app.util.Resource
import at.irfc.app.util.cachedRemoteResource
import kotlinx.coroutines.flow.Flow

class GalleryPictureRepository(
    private val galleryPictureDao: GalleryPictureDao,
    private val pictureApi: PictureApi
) {
    fun loadPictures(force: Boolean): Flow<Resource<List<GalleryPicture>>> = cachedRemoteResource(
        query = galleryPictureDao::getAll,
        fetch = pictureApi::getPictures,
        update = {
            val pictures = it.map(PicturesDto::toGalleryPicture)
            galleryPictureDao.upsert(pictures)
        },
        shouldFetch = { pictures ->
            force || pictures.isEmpty()
        }
    )
}
