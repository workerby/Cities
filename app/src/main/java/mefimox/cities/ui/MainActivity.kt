package mefimox.cities.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import mefimox.cities.ui.messages.MessageFlow
import mefimox.cities.ui.navigation.Navigation
import mefimox.cities.ui.theme.CitiesTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CitiesTheme {
                Navigation()
            }
        }

        observeMessages()
    }

    private fun observeMessages() {
        lifecycleScope.launch {
            MessageFlow().collect {
                Toast.makeText(this@MainActivity, getString(it), Toast.LENGTH_SHORT).show()
            }
        }
    }
}


