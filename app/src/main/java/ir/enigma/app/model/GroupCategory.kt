package ir.enigma.app.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import ir.enigma.app.R

enum class GroupCategory(
    val id: Int,
    val text: String,
    @DrawableRes val iconRes: Int,
) {
    Travel(
        0, "سفر", R.drawable.ic_airplane,
    ),
    Home(
        1, "خانه", R.drawable.ic_home,
    ),

    Group(
        2, "مهمانی", R.drawable.ic_people,
    ),

    Other(
        3, "سایر", R.drawable.ic_receipt,
    )
}
