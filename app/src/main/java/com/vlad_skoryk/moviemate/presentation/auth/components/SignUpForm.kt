package com.vlad_skoryk.moviemate.presentation.auth.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.vlad_skoryk.moviemate.R

@Composable
fun SignUpForm(
    onSignUp: (String, String, String) -> Unit,
    onGoogleSignIn: () -> Unit,
    onSwitchToSignIn: () -> Unit,
    error: String?,
    onDismissError: () -> Unit,
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }

    val placeholderColor = MaterialTheme.colorScheme.primary
    val textColor = MaterialTheme.colorScheme.primary

    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = placeholderColor,
        unfocusedBorderColor = placeholderColor,
        focusedLabelColor = placeholderColor,
        unfocusedLabelColor = placeholderColor,
        cursorColor = textColor
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        // Name field
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Full Name") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = textFieldColors,
            textStyle = TextStyle(color = textColor)
        )

        // Email field
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            colors = textFieldColors,
            textStyle = TextStyle(color = textColor)
        )

        // Password field
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                IconButton(onClick = { showPassword = !showPassword }) {
                    Icon(
                        imageVector = if (showPassword) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = if (showPassword) "Hide password" else "Show password"
                    )
                }
            },
            singleLine = true,
            colors = textFieldColors,
            textStyle = TextStyle(color = textColor)
        )

        // Sign Up Button
        Button(
            onClick = { onSignUp(email, password, name) },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onSecondary),
            modifier = Modifier.fillMaxWidth(),
            enabled = email.isNotBlank() && password.isNotBlank() && name.isNotBlank(),
        ) {
            Text("Create Account", color = textColor)
        }

        // Google Sign In
        ElevatedButton(
            onClick = onGoogleSignIn,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onSecondary),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSecondary),
            modifier = Modifier.fillMaxWidth(),
            elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 4.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.icons8_google), // TODO: Replace with Google icon
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text("Continue with Google", color = MaterialTheme.colorScheme.onSecondaryContainer)
        }

        // Switch to Sign In
        TextButton(onClick = onSwitchToSignIn) {
            Text("Already have an account? Sign In", color = textColor)
        }
    }
}