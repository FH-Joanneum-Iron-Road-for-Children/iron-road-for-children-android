package at.irfc.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import at.irfc.app.data.local.dao.CategoryDao
import at.irfc.app.data.local.dao.EventDao
import at.irfc.app.data.local.dao.LocationDao
import at.irfc.app.data.local.dao.PictureDao
import at.irfc.app.data.local.dao.VotingDao
import at.irfc.app.data.local.entity.Event
import at.irfc.app.data.local.entity.EventCategory
import at.irfc.app.data.local.entity.EventLocation
import at.irfc.app.data.local.entity.EventPicture
import at.irfc.app.data.local.entity.Voting
import at.irfc.app.data.local.entity.relations.VotingEventCrossRef

@Database(
    entities = [
        Event::class, EventCategory::class, EventPicture::class, EventLocation::class,
        Voting::class, VotingEventCrossRef::class
    ],
    version = 5
)
@TypeConverters(Converters::class)
abstract class IrfcDatabase : RoomDatabase() {

    abstract fun eventDao(): EventDao
    abstract fun categoryDao(): CategoryDao
    abstract fun pictureDao(): PictureDao
    abstract fun locationDao(): LocationDao
    abstract fun votingDao(): VotingDao

    companion object {
        const val DATABASE_NAME = "IrfcDB"
    }
}
