package ir.enigma.app.ui.main.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer
import ir.enigma.app.component.*
import ir.enigma.app.ui.theme.IconMedium
import ir.enigma.app.ui.theme.onBackgroundAlpha3
import ir.enigma.app.ui.theme.onBackgroundAlpha7

@Composable
fun ShimmerColumn(
    modifier: Modifier = Modifier,
    count: Int = 5,
    circleSize: Dp = IconMedium,
) {
    val shimmerInstance = rememberShimmer(shimmerBounds = ShimmerBounds.Window)
    Column(modifier = modifier) {
        repeat(count) {
            ShimmerItem(shimmerInstance, circleSize)
            SVSpacer()
        }
    }
}

@Composable
fun ShimmerItem(
    shimmer: Shimmer = rememberShimmer(shimmerBounds = ShimmerBounds.Window),
    circleSize: Dp = IconMedium
) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        ShimmerCircle(shimmer, circleSize)
        MHSpacer()
        Column {
            ShimmerText(shimmer)
            TVSpacer()
            ShimmerText(shimmer, 120.dp)
        }
    }
}

@Composable
fun ShimmerCircle(shimmer: Shimmer, circleSize: Dp = IconMedium) {
    Box(
        modifier = Modifier.size(circleSize).shimmer(
            shimmer
        ).clip(CircleShape).background(MaterialTheme.colors.onBackgroundAlpha3)
    )
}

@Composable
fun ShimmerText(shimmer: Shimmer, width: Dp = 70.dp) {
    Box(
        modifier = Modifier.width(width).height(10.dp).shimmer(shimmer)
            .background(MaterialTheme.colors.onBackgroundAlpha3)
    )
}

@Composable
fun ShimmerRectangle(
    shimmer: Shimmer = rememberShimmer(shimmerBounds = ShimmerBounds.Window),
    width: Dp = 100.dp,
    height: Dp = 35.dp
) {
    Box(
        modifier = Modifier.width(width).height(height).shimmer(shimmer)
            .background(MaterialTheme.colors.onBackgroundAlpha3)
    )
}