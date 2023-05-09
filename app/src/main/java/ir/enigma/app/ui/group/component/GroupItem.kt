package ir.enigma.app.ui.group.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ir.enigma.app.component.*
import ir.enigma.app.model.Group
import ir.enigma.app.model.GroupCategory
import ir.enigma.app.ui.theme.*

@Composable
fun GroupItem(
    modifier: Modifier = Modifier,
    group: Group,
    amount: Double,
    currency: String
) {
    Row(modifier = modifier) {
        CardWithImageOrIcon(
            modifier = Modifier.align(alignment = Alignment.CenterVertically),
            icon = true,
            resource = GroupCategory.values()[group.categoryId].iconRes,
            size = IconLarge,
            contentPadding = 10.dp,
            tint = MaterialTheme.colors.primary,
            backgroundColor = MaterialTheme.colors.primary.copy(alpha = .1f),
            contentDescription = group.name,
        )

        Column(
            modifier = Modifier
                .padding(start = 12.dp)
                .weight(1f)
        ) {
            TextBody2(text = group.name)
            HintText(text = "واحد: ${group.currency}")
//TODO : Show Something!!!
        }

        IconText(
            modifier = Modifier.align(alignment = Alignment.CenterVertically),
            amount = amount,
            currency = currency,
            contentDescription = "Debt or credit"
        )
    }
}