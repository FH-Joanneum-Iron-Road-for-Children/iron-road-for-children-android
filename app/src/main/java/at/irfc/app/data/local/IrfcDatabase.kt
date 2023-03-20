package at.irfc.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import at.irfc.app.data.local.dao.EventDao
import at.irfc.app.data.local.entity.Event

@Database(entities = [Event::class], version = 1)
@TypeConverters(Converters::class)
abstract class IrfcDatabase : RoomDatabase() {

    abstract fun eventDao(): EventDao

    companion object {
        const val DATABASE_NAME = "IrfcDB"
    }
}
