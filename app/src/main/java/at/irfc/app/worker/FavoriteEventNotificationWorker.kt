package at.irfc.app.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import at.irfc.app.data.local.dao.EventDao
import at.irfc.app.util.NotificationHelper
import java.time.LocalDateTime
import kotlinx.coroutines.flow.first

class FavoriteEventNotificationWorker(
    private val context: Context,
    workerParams: WorkerParameters,
    private val eventDao: EventDao
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        val events = eventDao.getAll().first()

        val upcomingFavorites = events.filter {
            it.event.isFavorite &&
                it.event.startDateTime.isAfter(LocalDateTime.now()) &&
                it.event.startDateTime.isBefore(
                    LocalDateTime.now().plusDays(UPCOMING_EVENT_THRESHOLD_DAYS)
                )
        }

        upcomingFavorites.forEach { event ->
            NotificationHelper.showNotification(
                context = context,
                title = "Favorite Event starts soon!",
                message = "${event.title} begin at" +
                    "${event.startDateTime.toLocalTime()}!",
                notificationId = event.id.toInt()
            )
        }

        return Result.success()
    }
    companion object {
        private const val UPCOMING_EVENT_THRESHOLD_DAYS: Long = 26L
    }
}
