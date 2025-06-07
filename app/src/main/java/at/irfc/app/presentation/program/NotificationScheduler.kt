package at.irfc.app.presentation.program

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import at.irfc.app.data.local.entity.Event
import java.time.ZoneId

object NotificationScheduler {
    private const val CACHE_VALIDITY_DURATION_MINUTES = 15

    @SuppressLint("ScheduleExactAlarm", "NewApi")
    fun scheduleNotification(context: Context, event: Event) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        if (!alarmManager.canScheduleExactAlarms()) {
            // Optional: Direct user to settings - needed for Android >= 14
            val intent = Intent(
                android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
            ).apply {
                data = Uri.parse("package:${context.packageName}")
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            return
        }

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

        if (triggerTime > System.currentTimeMillis()) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                triggerTime,
                pendingIntent
            )
        }
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
