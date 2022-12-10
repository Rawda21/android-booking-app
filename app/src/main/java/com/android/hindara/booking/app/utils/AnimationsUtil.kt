package com.android.hindara.booking.app.utils

import androidx.compose.animation.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraphBuilder
import com.android.hindara.booking.app.App
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun OnboardingContentSlideAnimation(
    selectionPosition: Int,
    content: @Composable (Int) -> Unit) {
    AnimatedContent(
        targetState = selectionPosition,
        transitionSpec = {
            if (targetState > initialState) {
                // If the target number is larger, it slides in and fades in
                // while the initial (smaller) number slides out and fades out.
                slideInHorizontally { width -> width } + fadeIn() with
                        slideOutHorizontally { height -> -height } + fadeOut()
            } else {
                // If the target number is smaller, it slides in and fades in
                // while the initial number slides out and fades out.
                slideInHorizontally { width -> -width } + fadeIn() with
                        slideOutHorizontally() { height -> height } + fadeOut()
            }.using(
                // Disable clipping since the faded slide-in/out should
                // be displayed out of bounds.
                SizeTransform(clip = false)
            )
        }
    ) { targetPosition->
        content(targetPosition)
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.animatedComposable(
    route: String,
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit
) {
    composable(
        route = route,
        enterTransition = {
            getEnterTransitionAnimation()
        },
        exitTransition = {
            exitTransitionAnimation()
        },
        popEnterTransition = {
            getPopEnterTransitionAnimation()
        },
        popExitTransition = {
            getPopExitTransitionAnimation()
        }
    ) {
        content(it)
    }
}

private fun exitTransitionAnimation() =
    if (App.IS_RTL) {
        slideOutHorizontally(targetOffsetX = { 2000 })
    }else{
        slideOutHorizontally(targetOffsetX = { -2000 })
    }

private fun getEnterTransitionAnimation() =
    if (App.IS_RTL) {
        slideInHorizontally(initialOffsetX = { -2000 })
    } else {
        slideInHorizontally(initialOffsetX = { 2000 })
    }

private fun getPopEnterTransitionAnimation() =
    if (App.IS_RTL) {
        slideInHorizontally(initialOffsetX = { 2000 })
    } else {
        slideInHorizontally(initialOffsetX = { -2000 })
    }

private fun getPopExitTransitionAnimation() =
    if (App.IS_RTL) {
        slideOutHorizontally(targetOffsetX = { -2000 })
    } else {
        slideOutHorizontally(targetOffsetX = { 2000 })
    }