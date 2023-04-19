package ir.enigma.app.component

import InputTextField
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.enigma.app.model.PurchaseCategory

@Composable
fun Autocomplete(modifier: Modifier = Modifier) {
    val categories = PurchaseCategory.values()
    // listOf("خانه", "غذا و خوراکی", "سرگرمی", "حمل و نقل و سفر", "سایر")

    var category = remember {
        mutableStateOf("")
    }

    var expanded = remember {
        mutableStateOf(false)
    }

    val interactionSource = remember {
        MutableInteractionSource()
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = { expanded.value = false })
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(modifier = Modifier.fillMaxWidth()) {
                InputTextField(text = category, onValueChange = {
                    category.value = it
                    expanded.value = true
                },
                    trailingIcon = {
                        IconButton(onClick = { expanded.value = !expanded.value }) {
                            Icon(
                                imageVector = Icons.Rounded.ArrowDropDown,
                                contentDescription = "arrow"
                            )
                        }
                    })
            }

            AnimatedVisibility(visible = expanded.value) {
                Card(
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                        .fillMaxWidth(),
                    elevation = 10.dp
                ) {
                    LazyColumn(modifier = Modifier.heightIn(max = 150.dp)) {
                        if (category.value.isNotEmpty()) {
                            items(
                                categories.filter {
                                    it.text.lowercase()
                                        .contains(category.value.lowercase()) || it.text
                                        .contains("others")
                                }.sorted()
                            ) {
                                CategoryItems(title = it.text) { title ->
                                    category.value = title
                                    expanded.value = false
                                }
                            }
                        } else {
                            items(categories.sorted()) {
                                CategoryItems(title = it.text) { title ->
                                    category.value = title
                                    expanded.value = false
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryItems(title: String, onSelect: (String) -> Unit) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable { onSelect(title) }
        .padding(10.dp)) {
        Text(text = title)
    }
}

@Preview(showBackground = true)
@Composable
fun func() {
    RtlThemePreview {
        Autocomplete()
    }
}








