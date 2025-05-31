package at.irfc.app.presentation.program

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import at.irfc.app.data.local.entity.Event
import java.time.ZoneId

object NotificationScheduler {
    private const val CACHE_VALIDITY_DURATION_MINUTES = 15

    @SuppressLint("ScheduleExactAlarm")
    fun scheduleNotification(context: Context, event: Event) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, EventReminderReceiver::class.java).apply {
            putExtra("title", event.title)
            putExtra("eventId", event.id)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            event.id.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Set the trigger time to 1 minute from now for testing purposes
        // val triggerTime = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(1)

        val triggerTime = event.startDateTime
            .minusMinutes(CACHE_VALIDITY_DURATION_MINUTES.toLong())
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            triggerTime,
            pendingIntent
        )
    }

    fun cancelNotification(context: Context, event: Event) {
        val intent = Intent(context, EventReminderReceiver::class.java)

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            event.id.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
    }
}
