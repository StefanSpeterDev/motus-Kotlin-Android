package com.mpwd2.momomotus.ui.pages.signup

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mpwd2.momomotus.data.entities.State
import com.mpwd2.momomotus.data.entities.User
import com.mpwd2.momomotus.ui.navigation.NavigationKeys
import com.mpwd2.momomotus.ui.pages.game.GameViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SignUpScreen(navController: NavController){

    var mEmailTextFieldValue by remember { mutableStateOf(TextFieldValue("sf")) }
    var mPasswordTextFieldValue by remember {
        mutableStateOf(TextFieldValue())
    }

    val viewModel: SignUpViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        viewModel.signUpState.collect {
            if (it is State.Success) {
                navController.navigate(NavigationKeys.Route.HOME)
            }
        }
    }

    /*
    Autre manière de gérer la redirection
    val test = viewModel.signUpState.collectAsState().value

    if(test is State.Success)
        navController.navigate(NavigationKeys.Route.HOME)
     */

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "S'inscrire")})
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            TextField(value = mEmailTextFieldValue , onValueChange = { mEmailTextFieldValue = it})
            TextField(value = mPasswordTextFieldValue, onValueChange = {mPasswordTextFieldValue = it})
            TextButton(onClick = {
                viewModel.signUp(
                    mEmailTextFieldValue.text,
                    mPasswordTextFieldValue.text,
                    User(
                        email = mEmailTextFieldValue.text,
                        id = "32134",
                        score = 0,
                        pseudo = "StefanS",
                        image = "https://www.monurl.com/image/stefan.jpg"

                    )
                )
            }) {
                Text(text = "Enregistrer")
            }
        }
    }
}