package com.android.hindara.booking.app.ui.appmenu.mybookings

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.android.hindara.booking.app.R
import com.android.hindara.booking.app.ui.common.composables.AppTopBar
import com.android.hindara.booking.app.ui.hoteldetails.common.BookedHotelCardComposable
import com.android.hindara.booking.app.ui.theme.ScreenBackgroundColor

@Composable
fun MyBookingsScreen(
    viewModel: MyBookingsViewModel = hiltViewModel(),
    navController: NavController) {
    Scaffold(
        backgroundColor = ScreenBackgroundColor,
        topBar = {
            AppTopBar(navController, stringResource(id = R.string.menu_item_my_bookings))
        }
    ) {
        LazyColumn(
            modifier = Modifier.padding(it),
            contentPadding = PaddingValues(dimensionResource(id = R.dimen.defaultSpacing))
        ){
            val myBookingsList = viewModel.getMyBookings()
            items(myBookingsList.size) { index ->
                BookedHotelCardComposable(
                    checkInDate = viewModel.getCheckInDate(),
                    hotel = myBookingsList[index].hotel
                )
            }
        }
    }
}