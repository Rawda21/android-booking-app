package com.android.hindara.booking.app.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.android.hindara.booking.app.R
import com.android.hindara.booking.app.ui.theme.BottomSheetBackgroundColor
import com.android.hindara.booking.app.ui.theme.DarkTextColor

/**
 * Bottom sheet composable that has title and some body.
 *
 * @param title bottom sheet main heading
 * @param content bottoms sheet content
 * */
@Composable
fun BottomSheetContentWithTitle(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    val defaultSpacing = dimensionResource(id = R.dimen.defaultSpacing)
    val largeSpacing = dimensionResource(id = R.dimen.extraLargeSpacing)

    val modifier = Modifier
        .background(BottomSheetBackgroundColor)
        .padding(
            start = defaultSpacing,
            end = defaultSpacing,
            top = largeSpacing,
            bottom = largeSpacing
        )

    Column(modifier = modifier) {
        TitleComposable(title)
        content()
    }
}

/**
 * Title composable with h1 style.
 *
 * @param title text to show as a heading
 * */
@Composable
fun TitleComposable(title: String) {
    val modifier = Modifier
        .fillMaxWidth()
        .padding(
            bottom = dimensionResource(id = R.dimen.defaultSpacing)
        )
    Text(
        modifier = modifier,
        text = title,
        style = MaterialTheme.typography.h1,
        color = DarkTextColor
    )
}