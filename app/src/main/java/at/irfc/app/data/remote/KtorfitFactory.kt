package at.irfc.app.data.remote

import android.util.Log
import at.irfc.app.BuildConfig
import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

fun ktorfitFactory(networkLogs: Boolean = false): Ktorfit = ktorfit {
    baseUrl(BuildConfig.apiBaseUrl) // TODO get url from Config
    httpClient(
        HttpClient {
            expectSuccess = true // Throw on non 2xx codes

            install(ContentNegotiation) {
                json(Json { isLenient = true; ignoreUnknownKeys = true })
            }

            if (networkLogs) {
                install(Logging) {
                    logger = object : Logger {
                        override fun log(message: String) {
                            Log.d("Ktor", message)
                        }
                    }
                    level = LogLevel.ALL
                }
            }
        }
    )
}
