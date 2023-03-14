package at.irfc.app.data.di

import androidx.room.Room
import at.irfc.app.BuildConfig
import at.irfc.app.data.local.IrfcDatabase
import at.irfc.app.data.remote.api.createEventApi
import at.irfc.app.data.remote.ktorfitFactory
import at.irfc.app.data.repository.EventRepository
import de.jensklingenberg.ktorfit.Ktorfit
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

    singleOf(Ktorfit::createEventApi)

    singleOf(::EventRepository)
}

