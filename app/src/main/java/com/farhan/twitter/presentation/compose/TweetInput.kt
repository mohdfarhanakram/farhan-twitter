package com.farhan.twitter.presentation.compose

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.farhan.twitter.R

/**
 * Created by Mohd Farhan on 5/7/2021.
 */
@Composable
fun TweetInput(onSendMessage : (message:String)->Unit){
    val inputValue = remember { mutableStateOf("") }

    Row {
        TextField(
            value = inputValue.value,
            onValueChange = { inputValue.value = it },
            placeholder = { Text(text = "Type tweet here..") },
            modifier = Modifier.weight(1f),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
            keyboardActions = KeyboardActions {
                onSendMessage.invoke(inputValue.value)
                inputValue.value = ""
            },
        )
        Button(
            modifier = Modifier.height(56.dp),
            onClick = {
                onSendMessage.invoke(inputValue.value)
                inputValue.value = ""
            },
            enabled = inputValue.value.isNotBlank(),
        ) {
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = stringResource(R.string.app_name)
            )
        }
    }
}