package at.irfc.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "events")
data class Event(
    @PrimaryKey val id: String,
    val title: String,
    val updated: Date,
)