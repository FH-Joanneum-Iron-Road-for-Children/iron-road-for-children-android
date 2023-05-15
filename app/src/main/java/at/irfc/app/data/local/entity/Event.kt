package at.irfc.app.data.local.entity

import androidx.room.*
import java.util.*

@Entity(tableName = "events")
data class Event(
    @PrimaryKey(autoGenerate = false) val id: Long,
    val title: String,
    val startDate: Date,
    val endDate: Date,
    val description: String,
    val categoryId: Long,
    val locationId: Long,

    @Embedded val image: Image,

    val updated: Date
) {
    data class Image(
        @ColumnInfo("imageTitle") val title: String,
        @ColumnInfo("imagePath") val path: String
    )
}
