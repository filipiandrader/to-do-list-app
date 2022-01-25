package com.far.to_dolistapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.far.to_dolistapp.data.Todo

@Composable
fun CustomDialog(
    todo: Todo,
    onEvent: () -> Unit,
    title: String,
    textContent: String = "",
    positiveText: String = "YES",
    negativeText: String = "NO",
    onNegativeClick: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = { onNegativeClick() },
        title = {
            Text(text = title)
        },
        text = {
            Text(text = textContent)
        },
        buttons = {
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextButton(onClick = { onNegativeClick() }) {
                    Text(text = negativeText)
                }
                Spacer(modifier = Modifier.width(4.dp))
                TextButton(
                    onClick = {
                        onEvent()
                        onNegativeClick()
                    }
                ) {
                    Text(text = positiveText)
                }
            }
        }
    )
}