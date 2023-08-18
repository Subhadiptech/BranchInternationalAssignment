package com.ersubhadip.branchinternationalassignment.host

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ersubhadip.branchinternationalassignment.navigation.AppNavigation
import com.ersubhadip.branchinternationalassignment.ui.theme.BranchInternationalAssignmentTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BranchInternationalAssignmentTheme {
               AppNavigation()
            }
        }
    }
}