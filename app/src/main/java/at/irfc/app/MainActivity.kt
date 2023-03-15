package at.irfc.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import at.irfc.app.generated.navigation.NavGraphs
import at.irfc.app.ui.core.BottomBar
import at.irfc.app.ui.theme.IronRoadForChildrenTheme
import com.ramcosta.composedestinations.DestinationsNavHost

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            IronRoadForChildrenTheme {
                Scaffold(
                    bottomBar = {
                        BottomBar(navController)
                    }
                ) { paddingValues ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
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