package ir.enigma.app.ui.group

import InputTextField
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import ir.enigma.app.component.*
import ir.enigma.app.data.ApiResult
import ir.enigma.app.model.Contribution
import ir.enigma.app.model.Member
import ir.enigma.app.model.Purchase

import ir.enigma.app.model.PurchaseCategory
import ir.enigma.app.ui.ApiScreen
import ir.enigma.app.ui.auth.AuthViewModel.Companion.me
import ir.enigma.app.ui.group.component.CategoryIcon
import ir.enigma.app.ui.theme.*
import ir.enigma.app.util.toPrice
import ir.enigma.app.util.zeroIfEmpty
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import kotlin.collections.ArrayList


@Composable
fun NewPurchaseScreen(
    navController: NavController,
    groupViewModel: GroupViewModel,
    amount: Double
) {
    val group = groupViewModel.state.value.data!!
    val members = group.members!!
    val showSelectCategory = remember { mutableStateOf(false) }
    val purchaseCategories = PurchaseCategory.values()
    val description = remember { mutableStateOf("") }
    val price = remember { mutableStateOf("0") }

    val showError = remember { mutableStateOf(false) }
    val priceDouble = price.value.toDoubleOrNull()
    val priceError = priceDouble == 0.0

    val scrollState = rememberScrollState()

    val isRelatedBuyer = remember { mutableStateOf(true) }
    val isRelatedConsumer = remember { mutableStateOf(true) }
    val selectedCategoryIndex = remember { mutableStateOf(purchaseCategories.size - 1) }
    val selectedCategory = purchaseCategories[selectedCategoryIndex.value]

    val buyers = remember<SnapshotStateMap<Int, MemberContribution>> { mutableStateMapOf() }
    val consumers = remember<SnapshotStateMap<Int, MemberContribution>> { mutableStateMapOf() }

    val buyersDialog = remember { mutableStateOf(false) }
    val consumersDialog = remember { mutableStateOf(false) }

    val consumerError = checkSumError(priceDouble, consumers, isRelatedConsumer.value)
    val buyersError = checkSumError(priceDouble, buyers, isRelatedBuyer.value)

    LaunchedEffect(Unit) {
        if (amount != 0.0) {
            price.value = amount.toString()
            description.value = "تسویه حساب ${me.name}"
            if (amount > 0) {
                consumers[me.id] = MemberContribution(
                    me, related = mutableStateOf("1.0"), exact = mutableStateOf("")
                )
            } else {
                buyers[me.id] = MemberContribution(
                    me, related = mutableStateOf("1.0"), exact = mutableStateOf("")
                )
            }
        } else {
            members.forEach {
                consumers[it.user.id] = MemberContribution(
                    it.user,
                    mutableStateOf("1.0"),
                    mutableStateOf("0")
                )
            }
        }
    }

    if (groupViewModel.newPurchaseState.value is ApiResult.Success) {
        LaunchedEffect(Unit) {
            navController.popBackStack()
            groupViewModel.newPurchaseState.value = ApiResult.Empty()
        }
    }
    if (groupViewModel.newPurchaseState.value is ApiResult.Loading) {
        LoadingDialog()
    }

    BackHandler(enabled = true, onBack = {
        if (showSelectCategory.value) {
            showSelectCategory.value = false
        } else {
            navController.popBackStack()
        }
    })

    if (showSelectCategory.value) {
        CategorySelectScreen(onBack = {
            showSelectCategory.value = false
        }, onCategorySelected = {
            showSelectCategory.value = false
            selectedCategoryIndex.value = it.ordinal
        })
    } else {

        ApiScreen(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(contentPadding = PaddingValues(vertical = SpaceThin)) {
                    BackIconButton(onClick = {
                        navController.popBackStack()
                    })
                    TextH6(text = "خرید جدید", color = MaterialTheme.colors.onPrimary)
                }
            },
            apiResult = groupViewModel.newPurchaseState,
        ) { it ->
            Box(modifier = Modifier.fillMaxSize()) {


                Column(modifier = Modifier.verticalScroll(scrollState)) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = SpaceMedium),
                        elevation = 0.dp
                    ) {
                        Column(
                            modifier = Modifier.padding(
                                horizontal = 20.dp,
                                vertical = SpaceSmall
                            )
                        ) {
                            InputTextField(
                                text = description,
                                label = "توضیحات",
                            )

                            Box(Modifier.fillMaxWidth().height(IntrinsicSize.Max)) {
                                OutlinedTextField(
                                    modifier = Modifier.focusable(false)
                                        .clickable(false, onClick = {})
                                        .fillMaxWidth(),
                                    enabled = amount == 0.0,
                                    readOnly = true,
                                    leadingIcon = {
                                        Icon(
                                            painter = painterResource(selectedCategory.iconRes),
                                            contentDescription = null,
                                            tint = MaterialTheme.colors.onBackground
                                        )
                                    },
                                    value = selectedCategory.text,
                                    label = {
                                        Text("بابت")
                                    },
                                    onValueChange = {},
                                )

                                Box(
                                    modifier = Modifier.fillMaxSize()
                                        .clickable(enabled = amount == 0.0) {
                                            showSelectCategory.value = true
                                        }
                                )

                            }
                            Text("")
                            InputTextField(
                                text = price,
                                keyboardType = KeyboardType.Decimal,
                                label = "قیمت",
                                error = "قیمت نمی\u200Cتواند 0 باشد",
                                hasError = priceError,
                                enabled = amount == 0.0,
                                hint = priceDouble?.toPrice(group.currency),
                                showError = showError.value,

                                onValueChange = {

                                    price.value = it.zeroIfEmpty()
                                },
                            )
                        }
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = SpaceMedium),
                        elevation = 0.dp
                    ) {
                        Column(
                            modifier = Modifier.padding(horizontal = 20.dp, vertical = 15.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                TextH6(text = "خریدارها")
                                if (amount == 0.0 || amount > 0)
                                    SegmentedControl(onItemSelection = {
                                        isRelatedBuyer.value = !isRelatedBuyer.value
                                    })
                                else
                                    Box{}
                            }

                            buyers.forEach {
                                MemberContributionItem(
                                    memberContribution = it.value,
                                    isRelated = isRelatedBuyer.value,
                                    enabled = amount == 0.0 || amount > 0
                                )
                                SVSpacer()
                            }
                            if (buyers.isEmpty()) {
                                if (showError.value) {
                                    ErrorText(
                                        text = "لیست خریدارها نمی\u200Cتواند خالی باشد",
                                    )
                                } else {
                                    HintText(
                                        text = "لیست خالی است",
                                    )
                                }
                            }

                            if (buyersError != null && showError.value) {
                                ErrorText(
                                    text = buyersError,
                                )
                            }

                            AddOutlinedButton(
                                enabled = amount == 0.0 || amount > 0,
                                onClick = {
                                    buyersDialog.value = true
                                }, text = "تغییر لیست"
                            )
                        }

                    }


                    Card(
                        modifier = Modifier
                            .fillMaxWidth(),
                        elevation = 0.dp
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp, vertical = 15.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                TextH6(text = "مصرف کننده ها")
                                if (amount == 0.0 || amount < 0) {
                                    SegmentedControl(onItemSelection = {
                                        isRelatedConsumer.value = !isRelatedConsumer.value
                                    })
                                } else
                                    Box { }
                            }

                            consumers.forEach {
                                MemberContributionItem(
                                    memberContribution = it.value,
                                    isRelated = isRelatedConsumer.value,
                                    enabled = amount == 0.0 || amount < 0
                                )
                                SVSpacer()
                            }
                            if (consumers.isEmpty()) {
                                if (showError.value) {
                                    ErrorText(
                                        text = "لیست مصرف کننده ها نمی\u200Cتواند خالی باشد",
                                    )
                                } else {
                                    HintText(
                                        text = "لیست خالی می\u200Cباشد",
                                    )
                                }
                            }

                            if (consumerError != null && showError.value) {
                                ErrorText(
                                    text = consumerError,
                                )
                            }

                            AddOutlinedButton(
                                enabled = amount == 0.0 || amount < 0,
                                text = "تغییر لیست",
                                onClick = {
                                    consumersDialog.value = true
                                }
                            )

                        }
                    }
                }


                Row(
                    modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth()
                        .background(color = MaterialTheme.colors.surface)
                ) {

                    LoadingButton(
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = SpaceLarge, vertical = SpaceSmall),
                        actionText = "ثبت خرید",
                        loading = groupViewModel.newPurchaseState.value is ApiResult.Loading,
                        onClick = {
                            showError.value = true
                            if (priceError || buyersError != null || consumerError != null)
                                return@LoadingButton
                            val buyersContribution =
                                getContributionList(buyers, priceDouble!!, isRelatedBuyer.value)
                            val consumersContribution =
                                getContributionList(consumers, priceDouble, isRelatedConsumer.value)

                            groupViewModel.createPurchase(
                                Purchase(
                                    title = description.value,
                                    totalPrice = priceDouble,
                                    purchaseCategoryIndex = selectedCategory.ordinal,
                                    buyers = buyersContribution,
                                    consumers = consumersContribution,
                                    date = LocalDate.now()
                                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                                    sender = me
                                )
                            )
                        }
                    )
                }
            }

            if (buyersDialog.value)
                SelecetMembersDialog(
                    members = members,
                    selectedMembers = buyers,
                    onDismiss = {
                        buyersDialog.value = false
                    },
                )
            if (consumersDialog.value)
                SelecetMembersDialog(
                    members = members,
                    selectedMembers = consumers,
                    onDismiss = {
                        consumersDialog.value = false
                    },
                )
        }
    }


}

fun checkSumError(
    priceDouble: Double?,
    list: SnapshotStateMap<Int, MemberContribution>,
    relatedConsumer: Boolean
): String? {
    if (relatedConsumer) {
        list.forEach {
            if (it.value.related.value.toDouble() > 0.0)
                return null;
        }
        return "حداقل وزن یک نفر باید بیشتر از صفر باشد"
    } else {
        if (priceDouble == null)
            return null
        var sum = 0.0
        list.forEach {
            sum += it.value.exact.value.toDoubleOrNull() ?: 0.0
        }
        if (sum != priceDouble)
            return "مجموع مبلغ ها با قیمت کل خرید برابر نیست"
        return null
    }
}


@Composable
fun CategorySelectScreen(onBack: () -> Unit, onCategorySelected: (PurchaseCategory) -> Unit) {
    Scaffold(
        backgroundColor = MaterialTheme.colors.surface,
        topBar = {
            TopAppBar(contentPadding = PaddingValues(vertical = SpaceThin)) {
                BackIconButton(onClick = onBack)
                TextH6(text = "انتخاب دسته\u200Cبندی", color = MaterialTheme.colors.onPrimary)
            }
        }) {
        it.calculateTopPadding()

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(PurchaseCategory.values().size) { index ->
                val category = PurchaseCategory.values()[index]
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 10.dp)
                        .clickable {
                            onCategorySelected(category)
                        },

                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    CategoryIcon(category = category)
                    MHSpacer()
                    TextH6(text = category.text)

                }
            }

        }
    }
}

@Composable
fun SelecetMembersDialog(
    members: List<Member>,
    selectedMembers: SnapshotStateMap<Int, MemberContribution>,
    onDismiss: () -> Unit,
) {
    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier.fillMaxWidth().background(
                color = MaterialTheme.colors.surface,
            ).padding(horizontal = 20.dp, vertical = 10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TextH6(text = "انتخاب اعضا")
                if (selectedMembers.size != members.size) {
                    OutlinedButton(
                        onClick = {
                            selectedMembers.clear()
                            members.forEach {
                                selectedMembers[it.user.id] = MemberContribution(
                                    user = it.user,
                                    related = mutableStateOf("1.0"),
                                    exact = mutableStateOf("0")
                                )
                            }
                        },
                        modifier = Modifier.padding(horizontal = 10.dp)
                    ) {
                        Text(text = "انتخاب همه")
                    }
                } else {
                    OutlinedButton(
                        onClick = {
                            selectedMembers.clear()
                        },
                        modifier = Modifier.padding(horizontal = 10.dp)
                    ) {
                        Text(text = "حذف همه")
                    }
                }

            }
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(members.size) { index ->
                    val member = members[index]
                    val userId = member.user.id
                    Row(
                        modifier = Modifier.background(
                            color = if (selectedMembers.containsKey(userId)) {
                                MaterialTheme.colors.primary.copy(alpha = 0.1f)
                            } else {
                                Color.Transparent
                            }
                        ).padding(vertical = 10.dp)
                            .fillMaxWidth()

                            .clickable {
                                if (selectedMembers.containsKey(userId)) {
                                    selectedMembers.remove(userId)
                                } else {
                                    selectedMembers[userId] = MemberContribution(
                                        user = member.user,
                                        related = mutableStateOf("1.0"),
                                        exact = mutableStateOf("0")
                                    )
                                }
                            },

                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        AvatarWithText(
                            user = member.user,
                        )

                    }
                }

            }

            Button(
                modifier = Modifier.fillMaxWidth().padding(top = SpaceSmall),
                onClick = onDismiss
            ) {
                Text(text = "تایید")
            }
        }
    }

}

fun getContributionList(
    selectedMembers: SnapshotStateMap<Int, MemberContribution>,
    totalPrice: Double,
    isRelated: Boolean
): List<Contribution> {
    val list = ArrayList<Contribution>()
    if (isRelated) {
        var sumWeight = 0.0
        selectedMembers.forEach {
            sumWeight += it.value.related.value.toDouble()
        }
        selectedMembers.forEach {
            list.add(
                Contribution(
                    it.value.user,
                    (totalPrice / sumWeight) * it.value.related.value.toDouble()
                )
            )
        }
    } else {
        selectedMembers.forEach {
            list.add(Contribution(it.value.user, it.value.exact.value.toDouble()))
        }
    }

    return list

}

@Composable
fun LoadingDialog(){
    Dialog(onDismissRequest = {}){
        CircularProgressIndicator()
    }
}
