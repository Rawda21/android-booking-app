package com.android.hindara.booking.app.ui.hoteldetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter.Companion.tint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutScope
import androidx.navigation.NavController
import com.android.hindara.booking.app.R
import com.android.hindara.booking.app.ui.home.HomeViewModel
import com.android.hindara.booking.app.ui.home.Hotel
import com.android.hindara.booking.app.ui.hoteldetails.common.ReviewItemComposable
import com.android.hindara.booking.app.ui.theme.*
import com.android.hindara.booking.app.utils.getHeaderImageHeightInDp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@Composable
fun HotelDetailsScreen(
    homeViewModel: HomeViewModel,
    navController: NavController,
) {
    val hotel = homeViewModel.getChosenHotel()
    Scaffold(
        bottomBar = {
            BookingBottomBar(navController, hotel)
        }
    ) {
        ConstraintLayout(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(it)
        ) {
            val (headerImage, backButton, bookMarkButton, hotelBoardCard, contentSection) = createRefs()

            HeaderImageComposable(headerImage, hotel)
            BackButtonComposable(backButton)
            BookmarkButtonComposable(bookMarkButton)
            ContentfulSectionComposable(contentSection, hotel)
            HotelBannerComposable(hotelBoardCard, hotel)
        }
    }
}

@Composable
fun ConstraintLayoutScope.HeaderImageComposable(
    headerImage: ConstrainedLayoutReference,
    hotel: Hotel
) {
    val headerImageModifier = Modifier
        .constrainAs(headerImage) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
        .height(getHeaderImageHeightInDp())
    Image(
        modifier = headerImageModifier,
        painter = painterResource(id = hotel.image),
        contentScale = ContentScale.Crop,
        contentDescription = stringResource(R.string.hotel_header_image_description)
    )
}

@Composable
fun ConstraintLayoutScope.BackButtonComposable(backButton: ConstrainedLayoutReference) {
    val extraLargeSpacing = dimensionResource(id = R.dimen.extraLargeSpacing)
    val defaultSpacing = dimensionResource(id = R.dimen.defaultSpacing)
    val backButtonBoxModifier = Modifier
        .size(50.dp, 50.dp)
        .clip(RoundedCornerShape(10.dp))
        .background(BlackGradientColor)
        .padding(dimensionResource(id = R.dimen.verySmallSpacing))
        .constrainAs(backButton) {
            top.linkTo(parent.top, margin = extraLargeSpacing)
            start.linkTo(parent.start, margin = defaultSpacing)
        }
    Box(
        modifier = backButtonBoxModifier,
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_back),
            colorFilter = tint(WhiteColor),
            contentDescription = stringResource(R.string.back_button_description)
        )
    }
}

@Composable
fun ConstraintLayoutScope.BookmarkButtonComposable(bookmarkButton: ConstrainedLayoutReference) {
    val extraLargeSpacing = dimensionResource(id = R.dimen.extraLargeSpacing)
    val defaultSpacing = dimensionResource(id = R.dimen.defaultSpacing)
    val bookMarkButtonBoxModifier = Modifier
        .size(50.dp, 50.dp)
        .clip(RoundedCornerShape(10.dp))
        .background(BlackGradientColor)
        .padding(dimensionResource(id = R.dimen.verySmallSpacing))
        .constrainAs(bookmarkButton) {
            top.linkTo(parent.top, margin = extraLargeSpacing)
            end.linkTo(parent.end, margin = defaultSpacing)
        }
    Box(
        modifier = bookMarkButtonBoxModifier,
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_bookmark),
            colorFilter = tint(WhiteColor),
            contentDescription = stringResource(R.string.back_button_description)
        )
    }
}


@Composable
fun ConstraintLayoutScope.ContentfulSectionComposable(
    contentSection: ConstrainedLayoutReference,
    hotel: Hotel
) {

    val detailsSheetCornersSize = dimensionResource(id = R.dimen.detailsSheetCornersSize)
    val offset = getHeaderImageHeightInDp() - detailsSheetCornersSize
    val topGuidelineForContentView = createGuidelineFromTop(offset)

    val modifier = Modifier
        .clip(
            RoundedCornerShape(
                topStart = dimensionResource(id = R.dimen.detailsSheetCornersSize),
                topEnd = dimensionResource(id = R.dimen.detailsSheetCornersSize)
            )
        )
        .background(ScreenBackgroundColor)
        .fillMaxSize()
        .padding(dimensionResource(id = R.dimen.defaultSpacing))
        .constrainAs(contentSection) {
            top.linkTo(topGuidelineForContentView)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

    Column(modifier = modifier) {
        Spacer(modifier = Modifier.padding(top = dimensionResource(id = R.dimen.halfOfBannerHeight)))
        Text(
            modifier = Modifier.wrapContentWidth(),
            text = stringResource(R.string.description_title),
            style = MaterialTheme.typography.h1,
            color = DarkTextColor
        )
        SmallSpacer()
        Text(
            modifier = Modifier.wrapContentWidth(),
            text = hotel.description,
            style = MaterialTheme.typography.body1,
            maxLines = 3,
            color = DarkTextColor
        )
        SmallSpacer()
        Text(
            modifier = Modifier.wrapContentWidth(),
            text = stringResource(R.string.read_more_text),
            style = MaterialTheme.typography.body1,
            color = SuccessColor
        )
        SmallSpacer()
        GoogleMapsComposable()
        Spacer(modifier = Modifier.padding(top = dimensionResource(id = R.dimen.largeSpacing)))
        Text(
            modifier = Modifier.wrapContentWidth(),
            text = stringResource(R.string.reviews_title_text),
            style = MaterialTheme.typography.h1,
            color = DarkTextColor
        )
        SmallSpacer()
        Column {
            repeat(hotel.reviewsList?.size!!) {
                ReviewItemComposable(review = hotel.reviewsList[it])
            }
        }
        Text(
            modifier = Modifier.wrapContentWidth(),
            text = stringResource(R.string.see_more_reviews_text),
            style = MaterialTheme.typography.body1,
            color = SuccessColor
        )
        Spacer(modifier = Modifier.padding(top = dimensionResource(id = R.dimen.largeSpacing)))
    }
}

@Composable
private fun SmallSpacer() {
    Spacer(modifier = Modifier.padding(top = dimensionResource(id = R.dimen.smallSpacing)))
}

@Composable
fun GoogleMapsComposable() {
    val singapore = LatLng(1.35, 103.87)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(singapore, 10f)
    }
    val googleMapModifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(dimensionResource(id = R.dimen.detailsSheetCornersSize)))
        .height(dimensionResource(id = R.dimen.googleMapHeight))

    GoogleMap(
        modifier = googleMapModifier,
        uiSettings = MapUiSettings(
            zoomControlsEnabled = false,
            zoomGesturesEnabled = true
        ),
        cameraPositionState = cameraPositionState
    ) {
        Marker(
            state = MarkerState(position = singapore),
            title = "Singapore",
            snippet = "Marker in Singapore",
        )
    }
}


@Composable
fun ConstraintLayoutScope.HotelBannerComposable(
    hotelBoardCard: ConstrainedLayoutReference,
    hotel: Hotel
) {
    val eightyPercentWidthOfScreen = 0.8f
    val bannerHeight = dimensionResource(id = R.dimen.hotelBannerHeight)
    val halfOfBannerHeight = dimensionResource(id = R.dimen.halfOfBannerHeight)
    val detailsSheetCornersSize = dimensionResource(id = R.dimen.detailsSheetCornersSize)

    val spaceFromBottomOfHeaderImage = halfOfBannerHeight + detailsSheetCornersSize
    val offset = getHeaderImageHeightInDp() - spaceFromBottomOfHeaderImage
    val topGuidelineForHotelBanner = createGuidelineFromTop(offset)

    val modifier = Modifier
        .fillMaxWidth(eightyPercentWidthOfScreen)
        .height(bannerHeight)
        .constrainAs(hotelBoardCard) {
            top.linkTo(topGuidelineForHotelBanner)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
    Card(
        modifier = modifier,
        backgroundColor = WhiteColor,
        elevation = dimensionResource(id = R.dimen.cardElevation),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.detailsSheetCornersSize))
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.defaultSpacing)),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            HotelNameComposable(hotel)
            HotelAddressComposable(hotel)
            HotelRatingAndReviewComposable(hotel)
        }
    }
}

@Composable
private fun HotelNameComposable(hotel: Hotel) {
    Text(
        modifier = Modifier.wrapContentWidth(),
        text = hotel.name,
        style = MaterialTheme.typography.h1,
        color = DarkTextColor
    )
}

@Composable
fun HotelAddressComposable(hotel: Hotel) {
    val modifier = Modifier
        .wrapContentWidth()

    Text(
        modifier = modifier,
        text = hotel.address,
        style = MaterialTheme.typography.body1,
        color = DarkTextColor
    )
}

@Composable
fun HotelRatingAndReviewComposable(hotel: Hotel) {
    val modifier = Modifier
        .fillMaxWidth()

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RatingBarComposable()
        RatingTextComposable(hotel)
        ReviewsCount(hotel)
    }
}

@Composable
fun RatingBarComposable() {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(5) {
            Image(
                modifier = Modifier.padding(end = dimensionResource(id = R.dimen.verySmallSpacing)),
                painter = painterResource(id = R.drawable.ic_star), contentDescription = null
            )
        }
    }
}

@Composable
fun RatingTextComposable(hotel: Hotel) {
    val modifier = Modifier
        .wrapContentWidth()
        .padding(start = dimensionResource(id = R.dimen.smallSpacing))

    Text(
        modifier = modifier,
        text = hotel.rating.toString(),
        style = MaterialTheme.typography.body1,
        color = YellowColor
    )
}

@Composable
fun ReviewsCount(hotel: Hotel) {
    val modifier = Modifier
        .wrapContentWidth()
        .padding(start = dimensionResource(id = R.dimen.smallSpacing))

    Text(
        modifier = modifier,
        text = "(${hotel.reviewsList?.size.toString()} Reviews)",
        style = MaterialTheme.typography.body1,
        color = Color.LightGray
    )
}


@Composable
fun BookingBottomBar(navController: NavController, hotel: Hotel) {
    val modifier = Modifier
        .wrapContentHeight()
        .clip(
            RoundedCornerShape(
                topStart = dimensionResource(id = R.dimen.featuredImageCornerSize),
                topEnd = dimensionResource(id = R.dimen.featuredImageCornerSize)
            )
        )
        .fillMaxWidth()
        .background(WhiteColor)
        .padding(dimensionResource(id = R.dimen.defaultSpacing))

    ConstraintLayout(
        modifier = modifier
    ) {
        val (priceLabel, price,bookButton) = createRefs()
        val priceLabelModifier = Modifier
            .wrapContentWidth()
            .constrainAs(priceLabel) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
            }
        Text(
            modifier = priceLabelModifier,
            text = stringResource(R.string.per_night_price_text),
            style = MaterialTheme.typography.body1,
            maxLines = 1,
            color = DarkTextColor
        )

        val priceModifier = Modifier
            .wrapContentWidth()
            .constrainAs(price) {
                start.linkTo(parent.start)
                top.linkTo(priceLabel.bottom)
            }
        Text(
            modifier = priceModifier,
            text = "$ ${hotel.pricePerNight}",
            style = MaterialTheme.typography.h1,
            maxLines = 1,
            color = DarkTextColor
        )

        val bookNowButton = Modifier
            .wrapContentWidth()
            .constrainAs(bookButton) {
                end.linkTo(parent.end)
                top.linkTo(parent.top)
            }
        Button(
            modifier = bookNowButton,
            shape = RoundedCornerShape(CornerSize(dimensionResource(id = R.dimen.buttonCornersSize))),
            onClick = {
                // navigationToHomeScreen(navController)
            },
        ) {
            Text(stringResource(R.string.book_now_button_text))
        }
    }
}

