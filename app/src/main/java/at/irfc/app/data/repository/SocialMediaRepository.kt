package at.irfc.app.data.repository

import at.irfc.app.data.local.dao.SocialMediaDao
import at.irfc.app.data.local.entity.SocialMedia
import at.irfc.app.data.remote.api.SocialMediaApi
import at.irfc.app.data.remote.dto.SocialMediaDto
import at.irfc.app.data.remote.dto.toSocialMedia
import at.irfc.app.util.Resource
import at.irfc.app.util.cachedRemoteResource
import kotlinx.coroutines.flow.Flow

class SocialMediaRepository(
    private val socialMediaDao: SocialMediaDao,
    private val socialMediaApi: SocialMediaApi
) {
    fun getSocialMedia(force: Boolean): Flow<Resource<List<SocialMedia>>> = cachedRemoteResource(
        query = socialMediaDao::getAll,
        fetch = socialMediaApi::getSocialMedia,
        update = {
            val socialMedia = it.map(SocialMediaDto::toSocialMedia)
            socialMediaDao.upsert(socialMedia)
        },
        shouldFetch = { socialMedia ->
            force || socialMedia.isEmpty()
        }
    )
}
