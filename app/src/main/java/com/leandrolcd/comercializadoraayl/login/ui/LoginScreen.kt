package com.leandrolcd.comercializadoraayl.login.ui

import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.leandrolcd.comercializadoraayl.R
import com.leandrolcd.comercializadoraayl.login.ui.models.UserData
import com.leandrolcd.comercializadoraayl.login.ui.states.UiStatus


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LoginScreen(loginViewModel: LoginViewModel, onLoginWithGoogleClicked: () -> Unit) {
    Box(
        Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        val uiStatus: UiStatus<UserData> by loginViewModel.uiStatus.observeAsState(initial = UiStatus.Login())
        when (uiStatus) {
            is UiStatus.Error -> {
                Header(
                    Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                )
                Body(Modifier.align(Alignment.Center), loginViewModel) {
                    onLoginWithGoogleClicked()
                }

                Footer(Modifier.align(Alignment.BottomCenter))
                // ErrorDialog((uiStatus as UiStatus.Error<UserData>).message)
            }
            is UiStatus.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center), contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is UiStatus.Login -> {
                Header(
                    Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                )
                Body(Modifier.align(Alignment.Center), loginViewModel) {
                    onLoginWithGoogleClicked()
                }

                Footer(Modifier.align(Alignment.BottomCenter))
            }
            is UiStatus.Success -> {
                Header(
                    Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                )
                Body(Modifier.align(Alignment.Center), loginViewModel) {
                    onLoginWithGoogleClicked()
                }

                Footer(Modifier.align(Alignment.BottomCenter))
            }
        }

    }


}

@Composable
fun ErrorDialog(message: String) {

}

//region Header
@Composable
fun Header(modifier: Modifier) {
    val activity = LocalContext.current as Activity
    Icon(
        imageVector = Icons.Default.Close,
        contentDescription = "Close App",
        modifier = modifier.clickable { activity.finish() })
}

//endregion

//region Body
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Body(modifier: Modifier, loginViewModel: LoginViewModel, onLoginWithGoogleClicked: () -> Unit) {

    //region States
    val email: String by loginViewModel.email.observeAsState("")

    val pwd: String by loginViewModel.password.observeAsState("")

    val isLoginEnabled by loginViewModel.isLoginEnabled.observeAsState(true)

    //endregion

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = CenterHorizontally
    ) {
        ImageLogo(Modifier.align(CenterHorizontally))
        Spacer(modifier = modifier.height(16.dp))
        Email(email) {
            loginViewModel.onLoginChange(it, pwd)

        }
        Spacer(modifier = modifier.height(4.dp))
        Password(pwd, {
            loginViewModel.onLoginChange(email, it)
        }, {loginViewModel.onLoginClicked()})
        Spacer(modifier = Modifier.height(8.dp))
        ForgotPassword(Modifier.align(Alignment.End))
        LoginButton(isLoginEnabled, loginViewModel)
        Spacer(modifier = Modifier.height(16.dp))
        Divider(
            Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
                .background(Color(0xFFF9F9F9))
                .height(1.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))
        LoginWithGoogle(Modifier.align(CenterHorizontally), onClickAction = {
            onLoginWithGoogleClicked()
        })

    }
}

//region Controls Body


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LoginButton(enable: Boolean, loginViewModel: LoginViewModel) {
    Button(
        onClick = { loginViewModel.onLoginClicked() }, enabled = enable, modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp), colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFF4Ea8E9),
            disabledBackgroundColor = Color(0xFF78C8FA),
            contentColor = Color.White,
            disabledContentColor = Color.White

        )

    ) {
        Text("Log In")
    }
}

@Composable
fun ForgotPassword(modifier: Modifier) {
    Text(
        text = "Forgot Password?",
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF4Ea8E9),
        modifier = modifier
    )
}


@Composable
fun ImageLogo(modifier: Modifier) {
    Card(
        shape = RoundedCornerShape(5.dp),

        modifier = modifier
            .padding(8.dp)
            .width(120.dp)
            .height(100.dp),
        backgroundColor = Color.Red,
        elevation = 4.dp
    ) {
        Box(contentAlignment = Center){
            Text(text = "AyL", color = Color.Black,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun LoginWithGoogle(modifier: Modifier, onClickAction: () -> Unit) {
    Card(
        shape = RoundedCornerShape(5.dp),
        modifier = modifier.padding(8.dp),
        elevation = 4.dp
    ) {
        Row(Modifier.clickable {
            onClickAction()
        }) {
            Image(
                painter = painterResource(id = R.drawable.ic_google),
                contentDescription = "LoginWithGoogle",
                modifier = modifier
                    .width(40.dp)
                    .height(40.dp)

            )
            Text(
                text = "Ingresar con Google",
                Modifier
                    .align(CenterVertically)
                    .padding(horizontal = 8.dp)
            )
        }
    }

}

@Composable
fun Email(email: String, function: (String) -> Unit) {
    TextField(
        value = email,
        onValueChange = { function(it) },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Email") },
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color(0xFFB2B2B2),
            backgroundColor = Color(0xFFFAFAFA),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun Password(pwd: String, function: (String) -> Unit, onDone: () -> Unit) {
    var passwordVisibility by remember {
        mutableStateOf(false)
    }
    TextField(
        value = pwd,
        onValueChange = { function(it) },
        Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Password") },
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        keyboardActions = KeyboardActions(onDone = {
            onDone()
        }),
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color(0xFFB2B2B2),
            backgroundColor = Color(0xFFFAFAFA),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        trailingIcon = {
            val image = if (passwordVisibility) {
                Icons.Rounded.Visibility
            } else {
                Icons.Rounded.VisibilityOff
            }
            IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                Icon(imageVector = image, contentDescription = "Show Password")
            }
        },
        visualTransformation = if (passwordVisibility) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation('*')
        }
    )
}


//endregion

//endregion

//region Footer
@Composable
fun Footer(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp)
    ) {
        Divider(
            Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
                .background(Color(0xFFF9F9F9))
                .height(1.dp)
        )

        SignUp()
    }
}

@Composable
fun SignUp() {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Text("Don't have an account?", fontSize = 12.sp, color = Color.Gray)
        Text(
            "Sign Up",
            Modifier.padding(start = 8.dp),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF4Ea8E9)
        )

    }
}
//endregion
