package at.irfc.app.data.local.entity

import androidx.room.*
import java.time.LocalDateTime
import java.util.*

@Entity(tableName = "events")
data class Event(
    @PrimaryKey(autoGenerate = false) val id: Long,
    val title: String,
    val startDateTime: LocalDateTime,
    val endDateTime: LocalDateTime,
    val description: String,
    val categoryId: Long,
    val locationId: Long,

    @Embedded val image: Image,

    val updated: LocalDateTime
) {
    data class Image(
        @ColumnInfo("imageTitle") val title: String,
        @ColumnInfo("imagePath") val path: String
    )
}
