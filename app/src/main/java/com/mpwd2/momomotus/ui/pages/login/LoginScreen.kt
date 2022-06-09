package com.mpwd2.momomotus.ui.pages.login

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mpwd2.momomotus.data.entities.State
import com.mpwd2.momomotus.ui.navigation.NavigationKeys

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun LoginScreen(navController: NavController){

    var mEmailTextFieldValue by remember { mutableStateOf(TextFieldValue("")) }
    var mPasswordTextFieldValue by remember {
        mutableStateOf(TextFieldValue())
    }

    val viewModel: LoginViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        viewModel.loginState.collect {
            if (it is State.Success) {
                navController.navigate(NavigationKeys.Route.HOME)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Connectez-vous!")})
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(text = "Connectez-vous !", style = TextStyle(fontSize = 26.sp, textAlign = TextAlign.Center))
            Box(modifier = Modifier.height(20.dp))
            TextField(value = mEmailTextFieldValue , onValueChange = { mEmailTextFieldValue = it})
            Box(modifier = Modifier.height(5.dp))
            TextField(value = mPasswordTextFieldValue, onValueChange = {mPasswordTextFieldValue = it})
            TextButton(onClick = {
                viewModel.login(
                    mEmailTextFieldValue.text,
                    mPasswordTextFieldValue.text,
                )
            }) {
                Text(text = "Se connecter ->")
            }
        }
    }
}