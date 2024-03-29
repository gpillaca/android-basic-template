package com.gpillaca.upcomingmovies.ui.main

import android.Manifest
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.gpillaca.upcomingmovies.model.Movie
import com.gpillaca.upcomingmovies.ui.common.PermissionRequester
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainState(
    private val scope: CoroutineScope,
    private val locationPermissionRequester: PermissionRequester,
    private val navController: NavController
) {

    fun onMovieClick(movie: Movie) {
        val navAction = MainFragmentDirections.actionMainToMoviedetail(movie)
        navController.navigate(navAction)
    }

    fun requestLocationPermissions(afterRequest: (Boolean) -> Unit) {
        scope.launch {
            val result = locationPermissionRequester.requestPermission()
            afterRequest(result)
        }
    }
}

fun Fragment.buildMainState(
    scope: CoroutineScope = viewLifecycleOwner.lifecycleScope,
    locationPermissionRequester: PermissionRequester = PermissionRequester(
        this,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ),
    navController: NavController = findNavController()
) = MainState(scope, locationPermissionRequester, navController)
