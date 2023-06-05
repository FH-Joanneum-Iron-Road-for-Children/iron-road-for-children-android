package at.irfc.app.data.local

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.ZoneOffset

class Converters {
    @TypeConverter
    fun localDateTimeFromTimestamp(value: Long?): LocalDateTime? {
        return value?.let { LocalDateTime.ofEpochSecond(value, 0, ZoneOffset.UTC) }
    }

    @TypeConverter
    fun localDateTimeToTimestamp(localDateTime: LocalDateTime?): Long? {
        return localDateTime?.toEpochSecond(ZoneOffset.UTC)
    }
}
