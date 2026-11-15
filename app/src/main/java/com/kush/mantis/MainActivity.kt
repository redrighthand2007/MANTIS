package com.kush.mantis

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.kush.mantis.navigation.MantisNavHost
import com.kush.mantis.ui.theme.MantisTheme
import dagger.hilt.android.AndroidEntryPoint
import org.mariuszgromada.math.mxparser.License

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        // Transparent system bars with icon colours that follow light/dark mode
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        License.iConfirmNonCommercialUse("Kush")

        setContent {
            MantisTheme {
                // Calculator keypads and numbers read left-to-right in every language, including Arabic and Hebrew
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        MantisNavHost()
                    }
                }
            }
        }
    }
}
