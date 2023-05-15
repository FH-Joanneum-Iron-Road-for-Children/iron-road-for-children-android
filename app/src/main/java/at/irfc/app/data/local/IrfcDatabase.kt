package at.irfc.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import at.irfc.app.data.local.dao.CategoryDao
import at.irfc.app.data.local.dao.EventDao
import at.irfc.app.data.local.dao.LocationDao
import at.irfc.app.data.local.dao.PictureDao
import at.irfc.app.data.local.entity.Event
import at.irfc.app.data.local.entity.EventCategory
import at.irfc.app.data.local.entity.EventLocation
import at.irfc.app.data.local.entity.EventPicture

@Database(
    entities = [Event::class, EventCategory::class, EventPicture::class, EventLocation::class],
    version = 2
)
@TypeConverters(Converters::class)
abstract class IrfcDatabase : RoomDatabase() {

    abstract fun eventDao(): EventDao
    abstract fun categoryDao(): CategoryDao
    abstract fun pictureDao(): PictureDao
    abstract fun locationDao(): LocationDao

    companion object {
        const val DATABASE_NAME = "IrfcDB"
    }
}
