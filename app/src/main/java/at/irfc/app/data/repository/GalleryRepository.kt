package at.irfc.app.data.repository

import at.irfc.app.data.local.dao.GalleryDao
import at.irfc.app.data.local.entity.Gallery
import at.irfc.app.data.remote.api.GalleryApi
import at.irfc.app.data.remote.dto.GalleryDto
import at.irfc.app.data.remote.dto.toGallery
import at.irfc.app.util.Resource
import at.irfc.app.util.cachedRemoteResource
import kotlinx.coroutines.flow.Flow

class GalleryRepository(
    private val galleryDao: GalleryDao,
    private val galleryApi: GalleryApi
) {
    fun loadGallery(force: Boolean): Flow<Resource<List<Gallery>>> = cachedRemoteResource(
        query = galleryDao::getAll,
        fetch = galleryApi::getGallery,
        update = {
            val gallery = it.map(GalleryDto::toGallery)
            galleryDao.upsert(gallery)
        },
        shouldFetch = { gallery ->
            force || gallery.isEmpty()
        }
    )
}
