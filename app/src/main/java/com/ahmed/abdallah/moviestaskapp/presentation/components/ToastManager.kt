package com.ahmed.abdallah.moviestaskapp.presentation.components

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.savedstate.SavedStateRegistryOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.ahmed.abdallah.moviestaskapp.ui.theme.OliveGreen
import com.ahmed.abdallah.moviestaskapp.ui.theme.Typography

class CustomToastManager(private val context: Context) : Toast(context) {

    fun showToast(
        message: String,
        duration: Int = LENGTH_SHORT,
        color: Color = OliveGreen,
        icon: ImageVector = Icons.Filled.Info,
        tintIconColor: Color = Color.Black,
        textColor: Color = Color.Black
    ) {
        val lifecycleOwner = context as? LifecycleOwner
            ?: throw IllegalStateException("Context does not contain a LifecycleOwner")

        val savedStateRegistryOwner = context as? SavedStateRegistryOwner
            ?: throw IllegalStateException("Context does not contain a SavedStateRegistryOwner")

        val composeView = ComposeView(context).apply {
            layoutParams = android.view.ViewGroup.LayoutParams(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT
            )

            setViewTreeLifecycleOwner(lifecycleOwner)
            setViewTreeSavedStateRegistryOwner(savedStateRegistryOwner)
        }

        composeView.setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        onClick = { },
                        colors = ButtonDefaults.buttonColors(containerColor = color)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Icon(
                                imageVector = icon,
                                contentDescription = null,
                                tint = tintIconColor
                            )
                            Text(
                                text = message,
                                style = Typography.labelLarge.copy(
                                    textAlign = TextAlign.Center
                                ),
                                color = textColor,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(horizontal = 8.dp)
                            )
                        }
                    }
                }
            }
        }

        val toast = Toast(context).apply {
            view = composeView
            setGravity(Gravity.TOP or Gravity.FILL_HORIZONTAL, 0, 250)
        }

        val durationMillis = if (duration == LENGTH_LONG) 3500L else 2000L
        toast.show()

        // Cancel toast after the specified duration
        Handler(Looper.getMainLooper()).postDelayed({
            toast.cancel()
        }, durationMillis)
    }
}

