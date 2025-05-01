package at.irfc.app.data.di

import androidx.room.Room
import at.irfc.app.BuildConfig
import at.irfc.app.data.local.IrfcDatabase
import at.irfc.app.data.remote.api.createCountdownApi
import at.irfc.app.data.remote.api.createEventApi
import at.irfc.app.data.remote.api.createGalleryApi
import at.irfc.app.data.remote.api.createVotingApi
import at.irfc.app.data.remote.ktorfitFactory
import at.irfc.app.data.repository.CountdownRepository
import at.irfc.app.data.repository.EventRepository
import at.irfc.app.data.repository.GalleryRepository
import at.irfc.app.data.repository.VotingRepository
import de.jensklingenberg.ktorfit.Ktorfit
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule = module {
    single { ktorfitFactory(networkLogs = BuildConfig.DEBUG) }
    single {
        @Suppress("MagicNumber")
        Room
            .databaseBuilder(
                context = get(),
                klass = IrfcDatabase::class.java,
                name = IrfcDatabase.DATABASE_NAME
            )
            .fallbackToDestructiveMigration()
            .build()
    }

    singleOf(IrfcDatabase::eventDao)
    singleOf(IrfcDatabase::categoryDao)
    singleOf(IrfcDatabase::locationDao)
    singleOf(IrfcDatabase::pictureDao)
    singleOf(IrfcDatabase::votingDao)
    singleOf(IrfcDatabase::galleryDao)
    singleOf(IrfcDatabase::countdownDao)

    singleOf(Ktorfit::createGalleryApi)
    singleOf(Ktorfit::createEventApi)
    // singleOf<EventApi>(::EventApiMock)
    singleOf(Ktorfit::createVotingApi)
    singleOf(Ktorfit::createCountdownApi)
    // singleOf<VotingApi>(::VotingApiMock)

    singleOf(::EventRepository)
    singleOf(::VotingRepository)
    singleOf(::GalleryRepository)
    singleOf(::CountdownRepository)
}
