package com.vlad_skoryk.moviemate.presentation.profile.view

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.google.firebase.auth.FirebaseUser
import com.vlad_skoryk.moviemate.R
import com.vlad_skoryk.moviemate.presentation.auth.viewmodel.AuthViewModel

@Composable
fun ProfileScreenRoute(
    viewModel: AuthViewModel = hiltViewModel(),
    onSignedOut: () -> Unit
) {
    val user by viewModel.authState.collectAsState()
    val context = LocalContext.current
    val emailSent = remember { mutableStateOf(false) }

    ProfileScreen(
        user = user,
        onSignOut = {
            viewModel.signOut()
            onSignedOut()
        },
        onSendEmailVerification = {
            user?.sendEmailVerification()?.addOnCompleteListener {
                emailSent.value = it.isSuccessful
                Toast.makeText(
                    context,
                    if (it.isSuccessful) "Лист підтвердження надіслано" else "Помилка надсилання",
                    Toast.LENGTH_SHORT
                ).show()
            }
        },
        onChangePassword = {
            viewModel.sendPasswordReset(user?.email ?: "") { success ->
                Toast.makeText(
                    context,
                    if (success) "Посилання на зміну пароля надіслано" else "Помилка зміни пароля",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    )
}

@Composable
fun ProfileScreen(
    user: FirebaseUser?,
    onSignOut: () -> Unit,
    onSendEmailVerification: () -> Unit,
    onChangePassword: () -> Unit
) {
    val photoUrl = user?.photoUrl
    val name = user?.displayName ?: user?.email ?: "Guest"
    val email = user?.email ?: ""
    val emailVerified = user?.isEmailVerified ?: false

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.dark_blue))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (photoUrl != null) {
            AsyncImage(
                model = photoUrl,
                contentDescription = "Profile photo",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(name, style = MaterialTheme.typography.headlineSmall, color = colorResource(id = R.color.yellow_main))
        Text(email, color = Color.LightGray)

        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = if (emailVerified) "Email підтверджено" else "Email не підтверджено",
                color = if (emailVerified) Color.Green else Color.Red
            )
            if (!emailVerified) {
                Spacer(modifier = Modifier.width(8.dp))
                OutlinedButton(onClick = onSendEmailVerification) {
                    Text("Надіслати лист")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onChangePassword, modifier = Modifier.fillMaxWidth()) {
            Text("Змінити пароль")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = onSignOut, modifier = Modifier.fillMaxWidth()) {
            Text("Вийти з акаунту")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(
        user = null,
        onSignOut = {},
        onSendEmailVerification = {},
        onChangePassword = {}
    )
}