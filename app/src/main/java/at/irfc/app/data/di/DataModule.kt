package at.irfc.app.data.di

import androidx.room.Room
import at.irfc.app.BuildConfig
import at.irfc.app.data.local.IrfcDatabase
import at.irfc.app.data.remote.api.EventApi
import at.irfc.app.data.remote.api.mock.EventApiMock
import at.irfc.app.data.remote.ktorfitFactory
import at.irfc.app.data.repository.EventRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule = module {
    single { ktorfitFactory(networkLogs = BuildConfig.DEBUG) }
    single {
        Room.databaseBuilder(
            context = get(),
            IrfcDatabase::class.java,
            IrfcDatabase.DATABASE_NAME
        ).build()
    }

    singleOf(IrfcDatabase::eventDao)

    // singleOf(Ktorfit::createEventApi)
    singleOf<EventApi>(::EventApiMock)

    singleOf(::EventRepository)
}
