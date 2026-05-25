package com.teomichael.mapmate.profile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.teomichael.mapmate.profile.navigation.MapMateNavGraph
import com.teomichael.mapmate.profile.ui.theme.MapMateTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MapMateTheme {
                MapMateNavGraph()
            }
        }
    }
}

