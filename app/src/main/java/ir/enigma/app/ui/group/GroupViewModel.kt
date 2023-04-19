package ir.enigma.app.ui.group

import androidx.compose.runtime.mutableStateOf
import ir.enigma.app.data.fakeGroups
import ir.enigma.app.data.fakePurchases
import ir.enigma.app.data.groupA
import ir.enigma.app.model.Purchase


class GroupViewModel {
    val group = groupA
    val purchases: List<Purchase> = fakePurchases()


}