package at.irfc.app

import android.app.Application
import at.irfc.app.data.di.dataModule
import at.irfc.app.presentation.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class IrfcApp : Application() {

    override fun onCreate() {
        super.onCreate()

        // start Koin!
        startKoin {
            androidContext(this@IrfcApp)
            modules(dataModule, presentationModule)
        }
    }
}