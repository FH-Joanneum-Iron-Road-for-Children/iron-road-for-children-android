package at.irfc.app.presentation.program

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import at.irfc.app.data.local.entity.Event
import java.util.concurrent.TimeUnit

object NotificationScheduler {

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

       /* val isTest = true

        val now = LocalDate.now()
        val triggerTime = if (isTest) {
            val testDay = when (event.startDateTime.dayOfMonth) {
                20 -> now // Event vom 20.06. → heute
                21 -> now.plusDays(1) // Event vom 21.06. → morgen
                else -> now
            }

            val testDateTime = LocalDateTime.of(
                testDay.year,
                testDay.month,
                testDay.dayOfMonth,
                event.startDateTime.hour,
                event.startDateTime.minute
            ).minusMinutes(15)

            testDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        } else {
            event.startDateTime
                .minusMinutes(15)
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli()
        }*/

        val triggerTime = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(1)

        /*val triggerTime = event.startDateTime
            .minusMinutes(15)
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()*/

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
