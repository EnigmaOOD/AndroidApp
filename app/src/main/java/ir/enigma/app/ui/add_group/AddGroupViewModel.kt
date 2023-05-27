package ir.enigma.app.ui.add_group

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.enigma.app.network.AddGroupRequest
import ir.enigma.app.repostitory.MainRepository
import ir.enigma.app.ui.ApiViewModel
import ir.enigma.app.ui.auth.AuthViewModel.Companion.token
import ir.enigma.app.util.LogType
import ir.enigma.app.util.MyLog
import ir.enigma.app.util.StructureLayer
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddGroupViewModel @Inject constructor(private val mainRepository: MainRepository) :
    ApiViewModel<Unit>() {

    fun createGroup(addGroupRequest: AddGroupRequest) {
        viewModelScope.launch {
            state.value =
                mainRepository.createGroup(token = token, addGroupRequest)
            MyLog.log(
                StructureLayer.ViewModel,
                "AddGroupViewModel",
                "createGroup",
                type = LogType.Info,
                "state.value = ${state.value}"
            )
        }
    }

}
