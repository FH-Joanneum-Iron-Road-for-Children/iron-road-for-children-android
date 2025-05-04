package at.irfc.app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "countdown")
data class Countdown(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "countdownId")
    val id: Long,
    val time: Long
)
