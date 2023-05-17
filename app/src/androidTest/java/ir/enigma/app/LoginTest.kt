package ir.enigma.app
//
//import androidx.activity.compose.setContent
//import androidx.compose.ui.test.*
//import androidx.compose.ui.test.junit4.createAndroidComposeRule
//import androidx.compose.ui.test.junit4.createComposeRule
//import androidx.navigation.compose.rememberNavController
//import androidx.test.core.app.ActivityScenario
//import ir.enigma.app.repostitory.UserRepository
//import ir.enigma.app.ui.auth.AuthScreen
//import ir.enigma.app.ui.auth.AuthViewModel
//import org.junit.Rule
//import org.junit.Test
//
//class LoginTest {
//    @get:Rule
//    val rule = createAndroidComposeRule<MainActivity>()
////    val rule = createComposeRule()
//
//    @Test
//    fun enterRegister_doRegister() {
//        var api = MockApi()
//
//        var userRepository = UserRepository(api)
//
//        rule.activity.setContent {
//            AuthScreen(
//                navController = rememberNavController(),
//                authViewModel = AuthViewModel(userRepository)
//            )
//        }
//        val textForField = "Ramezani"
//        //Do something
//        rule.onNodeWithTag("nameTextField").performTextInput(textForField)
////        rule.onNodeWithTag("emailTextField").performTextInput("ramezani@gmail.com")
////
////        rule.onNodeWithTag("avatarCard").performClick()
////        rule.onNodeWithTag("avatarIconNumberFive").performClick()
////
////        rule.onNodeWithTag("registerButton").performClick()
//
//        //Check something
//
////        rule.onNodeWithTag("nameTextField").assert(hasText("Ramezani"))
//        rule.onNodeWithTag("nameTextField").assertTextContains(textForField)
////        //        rule.onNodeWithTag("nameTextField").assertExists("Ramezani")
////        rule.onNodeWithTag("emailTextField").assert(hasText("ramezani@gmail.com"))
////
////        rule.onNodeWithTag("avatarDialog").assertExists()
////        rule.onNodeWithTag("avatarDialog").assertDoesNotExist()
//
//    }
//}
