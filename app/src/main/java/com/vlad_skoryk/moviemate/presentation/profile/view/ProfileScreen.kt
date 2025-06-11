package com.vlad_skoryk.moviemate.presentation.profile.view

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
    val name = user?.displayName ?: user?.email ?: "Гість"
    val email = user?.email ?: ""
    val emailVerified = user?.isEmailVerified ?: false

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.dark_blue))
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = "Profile",
            style = MaterialTheme.typography.headlineSmall,
            color = colorResource(id = R.color.yellow_main),
            modifier = Modifier.padding(vertical = 16.dp)
        )

        HorizontalDivider(
            color = colorResource(id = R.color.yellow_main),
            thickness = 1.dp,
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Profile Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.large,
            colors = CardDefaults.cardColors(
                containerColor = colorResource(id = R.color.gray_blue)
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
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
                } else {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = name.take(1).uppercase(),
                            style = MaterialTheme.typography.headlineMedium,
                            color = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium,
                    color = colorResource(id = R.color.yellow_main)
                )

                if (email.isNotEmpty()) {
                    Text(
                        text = email,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.LightGray
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Email verification status
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = if (emailVerified) Color(0xFF2E7D32) else Color(0xFFC62828)
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = if (emailVerified) "Email verified" else "Email not verified",
                    color = colorResource(id = R.color.light_blue),
                    style = MaterialTheme.typography.bodyLarge
                )
                if (!emailVerified) {
                    OutlinedButton(
                        onClick = onSendEmailVerification,
                        border = ButtonDefaults.outlinedButtonBorder,
                        modifier = Modifier.padding(horizontal = 12.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = colorResource(id = R.color.light_blue)
                        )
                    ) {
                        Text("Send", color = colorResource(id = R.color.light_blue))
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Action buttons
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = onChangePassword,
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.gray_blue)
                ),
                elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Key, contentDescription = "Logout", tint = colorResource(id = R.color.light_blue))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Change password", color = colorResource(id = R.color.light_blue))
            }

            ElevatedButton(
                onClick = onSignOut,
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.gray_blue)
                ),
                elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Logout, contentDescription = "Logout", tint = colorResource(id = R.color.light_blue))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Logout", color = colorResource(id = R.color.light_blue))
            }
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