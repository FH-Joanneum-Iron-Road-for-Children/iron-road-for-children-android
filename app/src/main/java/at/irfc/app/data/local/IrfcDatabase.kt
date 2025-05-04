package at.irfc.app.data.local
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import at.irfc.app.data.local.dao.CategoryDao
import at.irfc.app.data.local.dao.CountdownDao
import at.irfc.app.data.local.dao.EventDao
import at.irfc.app.data.local.dao.GalleryDao
import at.irfc.app.data.local.dao.IntroVideoDao
import at.irfc.app.data.local.dao.LocationDao
import at.irfc.app.data.local.dao.PictureDao
import at.irfc.app.data.local.dao.PlaylistDao
import at.irfc.app.data.local.dao.SocialMediaDao
import at.irfc.app.data.local.dao.VotingDao
import at.irfc.app.data.local.entity.Countdown
import at.irfc.app.data.local.entity.Event
import at.irfc.app.data.local.entity.EventCategory
import at.irfc.app.data.local.entity.EventLocation
import at.irfc.app.data.local.entity.EventPicture
import at.irfc.app.data.local.entity.Gallery
import at.irfc.app.data.local.entity.IntroVideo
import at.irfc.app.data.local.entity.Playlist
import at.irfc.app.data.local.entity.SocialMedia
import at.irfc.app.data.local.entity.Voting
import at.irfc.app.data.local.entity.relations.VotingEventCrossRef

@Database(
    entities = [
        Event::class, EventCategory::class, EventPicture::class, EventLocation::class,
        Voting::class, VotingEventCrossRef::class, Gallery::class, Countdown::class,
        IntroVideo::class, SocialMedia::class, Playlist::class
    ],
    version = 8
)
@TypeConverters(Converters::class)
abstract class IrfcDatabase : RoomDatabase() {

    abstract fun eventDao(): EventDao
    abstract fun categoryDao(): CategoryDao
    abstract fun pictureDao(): PictureDao
    abstract fun locationDao(): LocationDao
    abstract fun votingDao(): VotingDao
    abstract fun galleryDao(): GalleryDao
    abstract fun countdownDao(): CountdownDao
    abstract fun introVideoDao(): IntroVideoDao
    abstract fun socialMediaDao(): SocialMediaDao
    abstract fun playlistDao(): PlaylistDao

    companion object {
        const val DATABASE_NAME = "IrfcDB"
    }
}
