package com.kush.mantis

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.kush.mantis.features.settings.data.SettingsRepository
import com.kush.mantis.navigation.MantisNavHost
import com.kush.mantis.ui.theme.MantisTheme
import dagger.hilt.android.AndroidEntryPoint
import org.mariuszgromada.math.mxparser.License
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var settingsRepository: SettingsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        
        License.iConfirmNonCommercialUse("Kush")
        
        enableEdgeToEdge()
        setContent {
            val themeMode by settingsRepository.themeModeFlow.collectAsState(initial = "System")
            
            val isDarkTheme = when (themeMode) {
                "Dark" -> true
                "Light" -> false
                else -> isSystemInDarkTheme()
            }

            MantisTheme(darkTheme = isDarkTheme) {
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