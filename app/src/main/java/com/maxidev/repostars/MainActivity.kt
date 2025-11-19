package com.maxidev.repostars

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.maxidev.repostars.ui.home.HomeView
import com.maxidev.repostars.ui.home.homeDestination
import com.maxidev.repostars.ui.search.SearchView
import com.maxidev.repostars.ui.search.searchDestination
import com.maxidev.repostars.ui.theme.RepoStarsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.isNavigationBarContrastEnforced
        enableEdgeToEdge()
        installSplashScreen()
        setContent {
            RepoStarsTheme(dynamicColor = false) {
                val navController = rememberNavController()

                Surface {
                    NavHost(
                        navController = navController,
                        startDestination = HomeView
                    ) {
                        homeDestination(
                            navigateToSearch = { navController.navigate(SearchView) }
                        )

                        searchDestination()
                    }
                }
            }
        }
    }
}