package com.example.starwarsdemoapp

import android.os.Bundle
import android.provider.DocumentsContract.Root
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.starwarsdemoapp.ui.theme.StarWarsDemoAppTheme
import com.example.starwarsdemoapp.ui.theme.navigation.RootScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StarWarsDemoAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    RootScreen()
                    it
                }
            }
        }
    }
}
