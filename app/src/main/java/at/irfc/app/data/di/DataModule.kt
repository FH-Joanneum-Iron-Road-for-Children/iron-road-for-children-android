package at.irfc.app.data.di

import androidx.room.Room
import at.irfc.app.BuildConfig
import at.irfc.app.data.local.IrfcDatabase
import at.irfc.app.data.remote.api.createEventApi
import at.irfc.app.data.remote.api.createVotingApi
import at.irfc.app.data.remote.ktorfitFactory
import at.irfc.app.data.repository.EventRepository
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
            .fallbackToDestructiveMigrationFrom(1, 2, 3, 4)
            .build()
    }

    singleOf(IrfcDatabase::eventDao)
    singleOf(IrfcDatabase::categoryDao)
    singleOf(IrfcDatabase::locationDao)
    singleOf(IrfcDatabase::pictureDao)
    singleOf(IrfcDatabase::votingDao)

    singleOf(Ktorfit::createEventApi)
    // singleOf<EventApi>(::EventApiMock)
    singleOf(Ktorfit::createVotingApi)
    // singleOf<VotingApi>(::VotingApiMock)

    singleOf(::EventRepository)
    singleOf(::VotingRepository)
}
