package com.mpwd2.momomotus.ui.pages.signup

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mpwd2.momomotus.data.entities.State
import com.mpwd2.momomotus.data.entities.User
import com.mpwd2.momomotus.ui.navigation.NavigationKeys

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SignUpScreen(navController: NavController) {

    var mEmailTextFieldValue by remember { mutableStateOf(TextFieldValue("")) }
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
            TopAppBar(title = { Text(text = "S'inscrire") })
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "Inscrivez-vous !",
                style = TextStyle(fontSize = 26.sp, textAlign = TextAlign.Center)
            )
            Box(modifier = Modifier.height(20.dp))
            TextField(
                value = mEmailTextFieldValue,
                onValueChange = { mEmailTextFieldValue = it },
                label = { Text("Enter E-mail") },
            )
            Box(modifier = Modifier.height(5.dp))
            TextField(
                value = mPasswordTextFieldValue,
                onValueChange = { mPasswordTextFieldValue = it },
                label = { Text("Enter password") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
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
            Box(Modifier.height(20.dp))
            TextButton(onClick = {
                navController.navigate(NavigationKeys.Route.LOGIN)
            }) {
                Text(text = "Déjà un compte ? Connectez vous !")
            }
        }
    }
}