package com.gpillaca.upcomingmovies.model

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class PermissionChecker(activity: AppCompatActivity, private val permission: String) {

    private var onRequest: (Boolean) -> Unit = {}

    private val requestPermissionLauncher: ActivityResultLauncher<String> =
        activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            onRequest(isGranted)
        }

    suspend fun requestPermission(): Boolean {
        return suspendCancellableCoroutine { continuation ->
            onRequest = {
                continuation.resume(it)
            }
            requestPermissionLauncher.launch(permission)
        }
    }
}