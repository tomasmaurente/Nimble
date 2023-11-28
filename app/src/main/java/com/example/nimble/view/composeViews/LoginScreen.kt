package com.example.nimble.view.composeViews

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nimble.R
import com.example.nimble.nimble.viewModel.LoginViewModel
import com.example.nimble.view.composeViews.TestTags.login_btn
import com.example.nimble.view.composeViews.TestTags.snack

val neuzeit_s_lt_std_book = FontFamily(Font(R.font.neuzeit_s_lt_std_book))

@Composable
fun LoginScreen(viewModel: LoginViewModel) {
    BackgroundImage()
    Box(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Login(viewModel)
    }
}

@Composable
fun Login(viewModel: LoginViewModel) {

    val email: String by viewModel.email.observeAsState(initial = "")
    val password: String by viewModel.password.observeAsState(initial = "")
    val isLoading: Boolean by viewModel.isLoading.observeAsState(initial = false)
    val showSnackbar: Boolean by viewModel.showSnack.observeAsState(initial = false)

    if (isLoading) {
        Box(Modifier.fillMaxSize()) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    } else {
        Box(Modifier.fillMaxSize()) {
            if (showSnackbar) {
                ShowSnackbar(Modifier.align(Alignment.BottomCenter),  viewModel.snackContent)
            }
            Column(modifier = Modifier.align(Alignment.Center)) {
                HeaderImage(Modifier.align(Alignment.CenterHorizontally))
                Spacer(modifier = Modifier.paddingFromBaseline(dimensionResource(id = R.dimen.margin_nimble_icon)))
                EmailField(email) { viewModel.onLoginChanged(it, password) }
                Spacer(modifier = Modifier.paddingFromBaseline(dimensionResource(id = R.dimen.margin_vertical_text_input)))
                PasswordField(password, { viewModel.onForgotPressed() }) { viewModel.onLoginChanged(email, it) }
                Spacer(modifier = Modifier.paddingFromBaseline(dimensionResource(id = R.dimen.margin_vertical_text_input)))
                LoginButton { viewModel.onLoginSelected() }
            }
        }
    }
}

@Composable
fun ShowSnackbar(modifier: Modifier, message: String){
    Snackbar(
        modifier = modifier.testTag(snack)
    ) {
        Text(
            text = message
        )
    }
}

@Composable
fun EmailField(email: String, onTextFieldChanged: (String) -> Unit) {
    OutlinedTextField(
        value = email,
        onValueChange = { onTextFieldChanged(it) },
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.height_hint_text_input)),
        placeholder = { Text(text = stringResource(id = R.string.email)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = Color.White,
            focusedBorderColor = Color.Black,
            focusedLabelColor = Color.Black,
            backgroundColor = colorResource(id = R.color.dark_grey)
        ),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.corner_radius))
    )
}

@Composable
fun PasswordField(password: String,
                  onForgotButtonPressed: () -> Unit,
                  onTextFieldChanged: (String) -> Unit) {
    OutlinedTextField(
        value = password,
        onValueChange = { onTextFieldChanged(it) },
        placeholder = {
            Text(
                text = stringResource(id = R.string.password),
                fontFamily = neuzeit_s_lt_std_book
            ) },
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.height_hint_text_input)),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        maxLines = 1,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = Color.White,
            focusedBorderColor = Color.Black,
            focusedLabelColor = Color.Black,
            backgroundColor = colorResource(id = R.color.dark_grey)
        ),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.corner_radius)),
        trailingIcon = { ForgotPassword(onForgotButtonPressed) },
        visualTransformation = PasswordVisualTransformation()
    )
}

@Composable
fun ForgotPassword( onForgotButtonPressed: () -> Unit) {
    Text(
        text = stringResource(id = R.string.forgot),
        modifier = Modifier
            .clickable { onForgotButtonPressed() }
            .padding(dimensionResource(id = R.dimen.fab_margin)),
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = colorResource(id = R.color.dark_grey),
        fontFamily = neuzeit_s_lt_std_book
    )
}

@Composable
fun LoginButton(onLoginSelected: () -> Unit) {
    OutlinedButton(
        onClick = { onLoginSelected() },
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.height_hint_text_input))
            .testTag(login_btn),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.White,
        ),
        enabled = true,
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.corner_radius)),
        border = BorderStroke(1.dp, Color.Black)

    ) {
        Text(
            text = stringResource(id = R.string.loguin),
            color = Color.Black,
            fontFamily = neuzeit_s_lt_std_book,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun HeaderImage(modifier: Modifier) {
    Image(
        painter = painterResource(id = R.drawable.ic_logo_white),
        contentDescription = "Header",
        modifier = modifier
    )
}

@Composable
fun BackgroundImage() {
    Image(
        painter = painterResource(id = R.drawable.background_image),
        contentDescription = "Background",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxSize()
            .blur(10.dp)
    )
}

