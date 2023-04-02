package com.stylist.app.ui.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.stylist.app.ui.theme.*

@Composable
fun TVSpacer(space: Dp = SpaceThin) {
    Spacer(modifier = Modifier.height(space))
}

@Composable
fun THSpacer(space: Dp = SpaceThin) {
    Spacer(modifier = Modifier.width(space))
}

@Composable
fun SVSpacer(space: Dp = SpaceSmall) {
    Spacer(modifier = Modifier.height(space))
}

@Composable
fun SHSpacer(space: Dp = SpaceSmall) {
    Spacer(modifier = Modifier.width(space))
}


@Composable
fun MVSpacer(space: Dp = SpaceMedium) {
    Spacer(modifier = Modifier.height(space))
}

@Composable
fun MHSpacer(space: Dp = SpaceMedium) {
    Spacer(modifier = Modifier.width(space))
}


@Composable
fun LVSpacer(space: Dp = SpaceLarge) {
    Spacer(modifier = Modifier.height(space))
}

@Composable
fun LHSpacer(space: Dp = SpaceLarge) {
    Spacer(modifier = Modifier.width(space))
}


@Composable
fun VLVSpacer(space: Dp = SpaceVeryLarge) {
    Spacer(modifier = Modifier.height(space))
}

@Composable
fun VLHSpacer(space: Dp = SpaceVeryLarge) {
    Spacer(modifier = Modifier.width(space))
}

