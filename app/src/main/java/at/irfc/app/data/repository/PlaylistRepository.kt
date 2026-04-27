package at.irfc.app.data.repository
import at.irfc.app.data.local.dao.PlaylistDao
import at.irfc.app.data.local.entity.Playlist
import at.irfc.app.data.remote.api.PlaylistApi
import at.irfc.app.data.remote.dto.toPlaylist
import at.irfc.app.util.Resource
import at.irfc.app.util.cachedRemoteResource
import kotlinx.coroutines.flow.Flow

class PlaylistRepository(
    private val playlistDao: PlaylistDao,
    private val playlistApi: PlaylistApi
) {
    fun getPlaylist(force: Boolean): Flow<Resource<Playlist?>> = cachedRemoteResource(
        query = playlistDao::getAll,
        fetch = {
            playlistApi.getPlaylist()?.toPlaylist()
                ?: error("Playlist response was empty")
        },
        update = { playlist ->
            playlistDao.upsert(playlist)
            playlistDao.deleteNotInList(setOf(playlist.id))
        },
        shouldFetch = { local -> force || local == null }
    )
}
