package com.kush.mantis

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.kush.mantis.features.settings.domain.SettingsUseCases
import com.kush.mantis.navigation.MantisNavHost
import com.kush.mantis.ui.theme.MantisTheme
import dagger.hilt.android.AndroidEntryPoint
import org.mariuszgromada.math.mxparser.License
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var settingsUseCases: SettingsUseCases

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        
        License.iConfirmNonCommercialUse("Kush")
        

        setContent {
            val themeMode by settingsUseCases.themeModeFlow.collectAsState(initial = "System")
            
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