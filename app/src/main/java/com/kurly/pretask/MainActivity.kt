package com.kurly.pretask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.kurly.feature.main.MainScreen
import com.kurly.pretask.designsystem.theme.KurlyPreTaskTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KurlyPreTaskTheme {
                val mainViewModel: MainViewModel = hiltViewModel()
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            color = MaterialTheme.colorScheme.background
                        )
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it),

                        ) {
                        MainScreen()
                    }
                }

            }
        }
    }
}
