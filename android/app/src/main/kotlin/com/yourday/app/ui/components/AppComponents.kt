package com.yourday.app.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.yourday.app.ui.theme.*

@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    variant: ButtonVariant = ButtonVariant.PRIMARY
) {
    when (variant) {
        ButtonVariant.PRIMARY -> {
            Button(
                onClick = onClick,
                enabled = enabled,
                modifier = modifier.height(52.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Primary,
                    contentColor = Color.White,
                    disabledContainerColor = Primary.copy(alpha = 0.4f)
                ),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(text, fontWeight = FontWeight.SemiBold, style = MaterialTheme.typography.labelLarge)
            }
        }
        ButtonVariant.OUTLINED -> {
            OutlinedButton(
                onClick = onClick,
                enabled = enabled,
                modifier = modifier.height(52.dp),
                border = BorderStroke(1.dp, Primary),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(text, color = Primary, fontWeight = FontWeight.SemiBold)
            }
        }
        ButtonVariant.GHOST -> {
            TextButton(onClick = onClick, enabled = enabled, modifier = modifier) {
                Text(text, color = Primary)
            }
        }
    }
}

enum class ButtonVariant { PRIMARY, OUTLINED, GHOST }

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: androidx.compose.ui.text.input.VisualTransformation = androidx.compose.ui.text.input.VisualTransformation.None,
    keyboardOptions: androidx.compose.foundation.text.KeyboardOptions = androidx.compose.foundation.text.KeyboardOptions.Default,
    keyboardActions: androidx.compose.foundation.text.KeyboardActions = androidx.compose.foundation.text.KeyboardActions.Default,
    maxLines: Int = 1,
    isError: Boolean = false,
    supportingText: String? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        leadingIcon = leadingIcon?.let { { Icon(it, null, tint = TextSecondary) } },
        trailingIcon = trailingIcon,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        maxLines = maxLines,
        isError = isError,
        supportingText = supportingText?.let { { Text(it) } },
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Primary,
            focusedLabelColor = Primary,
            cursorColor = Primary,
            unfocusedContainerColor = SurfaceColor,
            focusedContainerColor = SurfaceColor,
            unfocusedBorderColor = Outline,
            unfocusedLabelColor = TextSecondary
        )
    )
}

@Composable
fun AppCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    border: BorderStroke? = BorderStroke(1.dp, Outline),
    containerColor: Color = CardBackground,
    shape: Shape = MaterialTheme.shapes.large,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        onClick = onClick ?: {},
        enabled = onClick != null,
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = containerColor),
        shape = shape,
        border = border,
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        content = { 
            Box {
                // Subtle card glow
                Box(
                    Modifier.matchParentSize().background(
                        Brush.radialGradient(
                            colors = listOf(Color.White.copy(alpha = 0.04f), Color.Transparent),
                            center = androidx.compose.ui.geometry.Offset(0f, 0f)
                        )
                    )
                )
                Column(Modifier.padding(16.dp), content = content)
            }
        }
    )
}

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    AppCard(
        modifier = modifier,
        onClick = onClick,
        border = BorderStroke(1.dp, Color(1f, 1f, 1f, 0.06f)),
        containerColor = Color(0xFF151515),
        content = content
    )
}

@Composable
fun ProgressCard(
    progress: Float,
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    AppCard(
        modifier = modifier,
        containerColor = Color(0xFF151515),
        shape = MaterialTheme.shapes.extraLarge
    ) {
        Text(title, style = MaterialTheme.typography.titleMedium)
        Text(subtitle, style = MaterialTheme.typography.labelSmall, color = TextSubtitle)
        Spacer(Modifier.height(16.dp))
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxWidth().height(6.dp).clip(MaterialTheme.shapes.extraSmall),
            color = Accent,
            trackColor = Color(0xFF2A2A2A)
        )
    }
}

@Composable
fun LoadingScreen() {
    Box(Modifier.fillMaxSize().background(BackgroundColor), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(color = Primary)
    }
}

@Composable
fun EmptyState(title: String, subtitle: String, icon: String = "📭") {
    Column(
        Modifier.fillMaxWidth().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(icon, style = MaterialTheme.typography.headlineLarge)
        Spacer(Modifier.height(16.dp))
        Text(title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.SemiBold)
        Spacer(Modifier.height(8.dp))
        Text(subtitle, style = MaterialTheme.typography.bodyMedium, color = TextSecondary, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
    }
}

@Composable
fun ErrorState(message: String, onRetry: () -> Unit) {
    Column(
        Modifier.fillMaxWidth().padding(32.dp).background(BackgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("⚠️", style = MaterialTheme.typography.headlineLarge)
        Spacer(Modifier.height(16.dp))
        Text("Something went wrong", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.SemiBold)
        Spacer(Modifier.height(8.dp))
        Text(message, style = MaterialTheme.typography.labelSmall, color = TextSecondary, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
        Spacer(Modifier.height(16.dp))
        AppButton("Retry", onClick = onRetry, variant = ButtonVariant.OUTLINED)
    }
}
