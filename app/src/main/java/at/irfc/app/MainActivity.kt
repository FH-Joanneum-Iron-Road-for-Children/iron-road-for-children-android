package at.irfc.app

import FullSplashScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import at.irfc.app.data.local.DebugEventInserter
import at.irfc.app.data.local.IrfcDatabase
import at.irfc.app.generated.navigation.NavGraphs
import at.irfc.app.ui.core.BottomBar
import at.irfc.app.ui.core.TopBar
import at.irfc.app.ui.theme.IronRoadForChildrenTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val database: IrfcDatabase by inject()

    @OptIn(ExperimentalLayoutApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        // simulateStartup()
        // Switch from SplashScreenTheme to AppTheme
        // setTheme(R.style.Theme_IronRoadForChildren)

        if (BuildConfig.DEBUG) {
            lifecycleScope.launch {
                DebugEventInserter.insertTestEvent(database)
            }
        }

        setContent {
            val navController = rememberNavController()
            var showSplash by remember {
                mutableStateOf(true)
            }

            LaunchedEffect(Unit) {
                delay(Companion.splashScreenDelayMs)
                showSplash = false
            }

            IronRoadForChildrenTheme {
                if (showSplash) {
                    FullSplashScreen() // dein Composable Splashscreen
                } else {
                    IronRoadForChildrenTheme {
                        Scaffold(
                            topBar = {
                                TopBar(navController)
                            },
                            bottomBar = {
                                BottomBar(navController)
                            }
                        ) { paddingValues ->
                            Surface(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(paddingValues)
                                    .consumeWindowInsets(paddingValues)
                            ) {
                                DestinationsNavHost(
                                    navController = navController,
                                    navGraph = NavGraphs.root
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    companion object {
        private const val splashScreenDelayMs = 2000L
    }
}
