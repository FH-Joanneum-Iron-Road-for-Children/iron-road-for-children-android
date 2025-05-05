package at.irfc.app.data.repository

import at.irfc.app.data.local.dao.IntroVideoDao
import at.irfc.app.data.local.entity.IntroVideo
import at.irfc.app.data.remote.api.IntroVideoApi
import at.irfc.app.data.remote.dto.toVideo
import at.irfc.app.util.Resource
import at.irfc.app.util.cachedRemoteResource
import kotlinx.coroutines.flow.Flow

class VideoRepository(
    private val introVideoDao: IntroVideoDao,
    private val introVideoApi: IntroVideoApi
) {
    fun getVideo(force: Boolean): Flow<Resource<IntroVideo?>> = cachedRemoteResource(
        query = introVideoDao::get,
        fetch = { introVideoApi.getVideo().toVideo() },
        update = { video ->
            introVideoDao.upsert(video)
            introVideoDao.deleteNotInList(setOf(video.id))
        },
        shouldFetch = { local -> force || local == null }
    )
}
