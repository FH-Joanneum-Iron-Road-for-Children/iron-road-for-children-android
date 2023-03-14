package at.irfc.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import at.irfc.app.data.local.entity.Event

@Database(entities = [Event::class], version = 1)
abstract class IrfcDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "IrfcDB"
    }
}