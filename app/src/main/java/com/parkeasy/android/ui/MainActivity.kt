package com.parkeasy.android.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.parkeasy.android.ui.navigation.Navigation
import com.parkeasy.android.ui.theme.ParkeasyTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * MainActivity class.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    /**
     * Overrides the onCreate method of the Activity class.
     *
     * This method is called when the activity is starting. It is responsible for initializing the activity's user interface.
     *
     * @param savedInstanceState The saved instance state of the activity, containing information about the previous state.
     * @return Unit
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ParkeasyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation()
                }
            }
        }
    }
}