import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import at.irfc.app.R
import at.irfc.app.ui.theme.IrfcBlue

@Composable
fun FullSplashScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = IrfcBlue),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(R.drawable.splash_logo),
                contentDescription = null,
                modifier = Modifier.size(200.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = painterResource(R.drawable.splash_fhj_logo),
                contentDescription = null,
                modifier = Modifier.height(40.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "ROCK.RIDE.GIVE! #IRFC2025",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(32.dp))
            Image(
                painter = painterResource(R.drawable.splash_footer),
                contentDescription = null,
                modifier = Modifier.height(33.dp)
            )
        }
    }
}
