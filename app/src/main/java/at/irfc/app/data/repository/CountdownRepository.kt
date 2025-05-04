package at.irfc.app.data.repository

import at.irfc.app.data.local.dao.CountdownDao
import at.irfc.app.data.local.entity.Countdown
import at.irfc.app.data.remote.api.CountdownApi
import at.irfc.app.data.remote.dto.CountdownDto
import at.irfc.app.data.remote.dto.toCountdown
import at.irfc.app.util.Resource
import at.irfc.app.util.cachedRemoteResource
import kotlinx.coroutines.flow.Flow

class CountdownRepository(
    private val countdownDao: CountdownDao,
    private val countdownApi: CountdownApi
) {
    fun getCountdown(force: Boolean): Flow<Resource<List<Countdown>>> = cachedRemoteResource(
        query = countdownDao::getAll,
        fetch = countdownApi::getCountdown,
        update = {
            val countdown = it.map(CountdownDto::toCountdown)
            countdownDao.upsert(countdown)
        },
        shouldFetch = { countdown ->
            force || countdown.isEmpty()
        }
    )
}
