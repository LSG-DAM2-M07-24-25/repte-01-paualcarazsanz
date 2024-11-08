package com.example.androidstudio_koala_template

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import com.example.androidstudio_koala_template.ui.theme.AndroidStudioKoalaTemplateTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidStudioKoalaTemplateTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        Title()
                        Spacer(modifier = Modifier.height(8.dp))
                        MyDropDownMenu(modifier = Modifier.padding(horizontal = 16.dp))
                        Spacer(modifier = Modifier.height(32.dp))
                        RatingSlider()
                    }
                }
            }
        }
    }
}

@Composable
fun Title() {
    Text(
        text = "Repte 01",
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        color = Color.Blue,
        style = MaterialTheme.typography.titleLarge
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDropDownMenu(modifier: Modifier = Modifier) {
    var selectedText: String by remember { mutableStateOf("") }
    var expanded: Boolean by remember { mutableStateOf(false) }
    val iconsMap = mapOf(
        "Add" to Icons.Filled.Add,
        "Call" to Icons.Filled.Call,
        "Email" to Icons.Filled.Email,
        "Home" to Icons.Filled.Home,
        "Settings" to Icons.Filled.Settings,
        "Search" to Icons.Filled.Search,
        "Favorite" to Icons.Filled.Favorite,
        "Location" to Icons.Filled.LocationOn
    )

    // UI for dropdown menu
    Column(modifier = modifier) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = { selectedText = it },
            enabled = false,
            readOnly = true,
            modifier = Modifier
                .clickable { expanded = true }
                .fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Blue,
                unfocusedBorderColor = Color.Gray,
                disabledBorderColor = Color.LightGray,
                focusedLabelColor = Color.Blue,
                unfocusedLabelColor = Color.Yellow,
                disabledLabelColor = Color.LightGray,
                cursorColor = Color.Black,
            )
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.Black, RoundedCornerShape(4.dp))
        ) {
            iconsMap.forEach { (name, _) ->
                DropdownMenuItem(
                    text = { Text(text = name) },
                    onClick = {
                        selectedText = name // update selected text but not the icon immediately
                        expanded = false
                    }
                )
            }
        }
    }

    // Button to confirm the selection
    Button(
        onClick = {
            // Update the icon when the button is clicked
            // The icon will only update when the button is pressed
        },
        modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
    ) {
        Text("Enviar")
    }

    // Display the icon based on the selectedText only when the button is pressed
    selectedText?.let {
        Icon(
            imageVector = iconsMap[it] ?: Icons.Default.Add,
            contentDescription = "Selected Icon",
            modifier = Modifier
                .size(100.dp)
                .padding(bottom = 16.dp),
            tint = Color.Blue
        )
    }
}

@Composable
fun RatingSlider() {
    var sliderPosition by remember { mutableStateOf(5f) }

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
            text = "${sliderPosition.toInt()}",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Slider(
            value = sliderPosition,
            onValueChange = { sliderPosition = it },
            valueRange = 1f..10f,
            steps = 9,
            onValueChangeFinished = { },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    AndroidStudioKoalaTemplateTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            // Content section at the top
            Title()
            Spacer(modifier = Modifier.height(8.dp))
            MyDropDownMenu(modifier = Modifier.padding(horizontal = 16.dp))
            Spacer(modifier = Modifier.height(32.dp))
            RatingSlider()
        }
    }
}
