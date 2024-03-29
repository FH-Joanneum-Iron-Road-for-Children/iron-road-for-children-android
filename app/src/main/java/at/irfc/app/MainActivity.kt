package at.irfc.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import at.irfc.app.generated.navigation.NavGraphs
import at.irfc.app.ui.core.BottomBar
import at.irfc.app.ui.core.TopBar
import at.irfc.app.ui.theme.IronRoadForChildrenTheme
import com.ramcosta.composedestinations.DestinationsNavHost

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalLayoutApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Switch from SplashScreenTheme to AppTheme
        setTheme(R.style.Theme_IronRoadForChildren)

        setContent {
            val navController = rememberNavController()

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
