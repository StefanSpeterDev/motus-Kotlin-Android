package com.mpwd2.momomotus.ui.pages.login

import android.annotation.SuppressLint
import android.text.TextUtils
import android.util.Patterns
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.mpwd2.momomotus.ui.navigation.NavigationKeys


fun isValidEmail(target: CharSequence?): Boolean {
    return if (TextUtils.isEmpty(target)) {
        false
    } else {
        Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun LoginScreen(navController: NavController) {

    var mEmailTextFieldValue by remember { mutableStateOf(TextFieldValue("")) }
    var mPasswordTextFieldValue by remember {
        mutableStateOf(TextFieldValue())
    }

    // Regex
    val regexPassword = "[0-9A-Za-z]{6,16}".toRegex()
    var password by rememberSaveable { mutableStateOf("") }
    val borderColorEmail = remember { mutableStateOf(Color.Blue) }
    val borderColorPassword = remember { mutableStateOf(Color.Blue) }


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
            TopAppBar(title = { Text(text = "Connectez-vous!") })
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "Connectez-vous !",
                style = TextStyle(fontSize = 26.sp, textAlign = TextAlign.Center)
            )
            Box(modifier = Modifier.height(20.dp))
            TextField(value = mEmailTextFieldValue,
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
            Box(modifier = Modifier.height(5.dp))
            TextButton(onClick = {
                val isEmailOK = isValidEmail(mEmailTextFieldValue.text)
                val isPassOK = regexPassword.matches(password)
                if (isEmailOK)
                    borderColorEmail.value = Color.Blue
                else
                    borderColorEmail.value = Color.Red
                if (!isPassOK)
                    borderColorPassword.value = Color.Red
                else
                    borderColorPassword.value = Color.Blue

                if (isEmailOK && isPassOK) {
                    viewModel.login(
                        mEmailTextFieldValue.text,
                        mPasswordTextFieldValue.text,
                    )
                }

            }) {
                Text(text = "Check Regex")
            }
            TextButton(onClick = {
                viewModel.login(
                    mEmailTextFieldValue.text,
                    mPasswordTextFieldValue.text,
                )
            }) {
                Text(text = "Se connecter ->")
            }
            TextButton(onClick = {
                navController.navigate(NavigationKeys.Route.SIGN_UP)
            }) {
                Text(text = "S'inscrire ->")
            }
        }
    }
}