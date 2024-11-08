package com.example.androidstudio_koala_template

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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
                // Definir estado para el slider, el ícono y el número del badge
                var selectedIcon by remember { mutableStateOf("Add") }
                var badgeNumber by remember { mutableStateOf(0) }
                var sliderPosition by remember { mutableStateOf(5f) }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        // Parte superior: Título, Dropdown y Slider
                        Title()
                        Spacer(modifier = Modifier.height(8.dp))

                        MyDropDownMenu(modifier = Modifier.padding(horizontal = 16.dp), onIconSelected = { icon ->
                            selectedIcon = icon // Actualiza el ícono seleccionado
                        })

                        Spacer(modifier = Modifier.height(32.dp))

                        // Slider para controlar el número
                        RatingSlider(sliderPosition = sliderPosition, onSliderValueChange = { newValue ->
                            sliderPosition = newValue // Actualiza el valor del slider
                        })

                        // Espacio para empujar la parte inferior hacia abajo
                        Spacer(modifier = Modifier.weight(1f))

                        // Parte inferior: Botón y Badge Icon
                        BottomSection(
                            selectedIcon = selectedIcon,
                            badgeNumber = badgeNumber,
                            onSendClick = {
                                badgeNumber = sliderPosition.toInt() // Solo actualiza el número cuando se hace clic en "Enviar"
                            }
                        )
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
fun MyDropDownMenu(modifier: Modifier = Modifier, onIconSelected: (String) -> Unit) {
    var selectedText: String by remember { mutableStateOf("Add") }
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

    Column(modifier = modifier) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = {},
            enabled = false,
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(
                    imageVector = iconsMap[selectedText] ?: Icons.Filled.Add,
                    contentDescription = "Selected Icon",
                    tint = Color.Blue
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Blue,
                unfocusedBorderColor = Color.Gray,
                disabledBorderColor = Color.LightGray,
                focusedLabelColor = Color.Blue,
                unfocusedLabelColor = Color.Yellow,
                disabledLabelColor = Color.LightGray,
                cursorColor = Color.Black
            )
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            iconsMap.forEach { (name, icon) ->
                DropdownMenuItem(
                    text = { Text(text = name) },
                    onClick = {
                        selectedText = name
                        onIconSelected(name) // Actualiza el ícono seleccionado
                        expanded = false
                    }
                )
            }
        }

        // Botón para abrir el menú desplegable
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = "Dropdown Arrow",
                tint = Color.Blue
            )
        }
    }
}

@Composable
fun RatingSlider(sliderPosition: Float, onSliderValueChange: (Float) -> Unit) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
            text = "${sliderPosition.toInt()}",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Slider(
            value = sliderPosition,
            onValueChange = { onSliderValueChange(it) },
            valueRange = 1f..10f,
            steps = 9,
            onValueChangeFinished = { },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun BottomSection(
    selectedIcon: String,
    badgeNumber: Int,
    onSendClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp)
    ) {
        Divider(
            color = Color.Gray,
            thickness = 1.dp,
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )

        // Botón para enviar
        Button(
            onClick = {
                onSendClick() // Actualiza el número solo cuando se hace clic en "Enviar"
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Enviar")
        }

        Spacer(modifier = Modifier.height(8.dp)) // Espaciado entre el botón y el ícono
        MyBadgeIconWithNumber(selectedText = selectedIcon, number = badgeNumber) // Muestra el ícono con el número
    }
}

@Composable
fun MyBadgeIconWithNumber(selectedText: String, number: Int) {
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

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(100.dp)
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = iconsMap[selectedText] ?: Icons.Filled.Add,
                contentDescription = "Selected Icon",
                modifier = Modifier.size(50.dp),
                tint = Color.Blue
            )
        }

        // Mostrar el número como un badge
        if (number > 0) {
            Box(
                contentAlignment = Alignment.TopEnd,
                modifier = Modifier
                    .size(30.dp)
                    .align(Alignment.TopEnd)
            ) {
                Text(
                    text = number.toString(),
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.White),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    AndroidStudioKoalaTemplateTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            Title()
            Spacer(modifier = Modifier.height(8.dp))

            MyDropDownMenu(modifier = Modifier.padding(horizontal = 16.dp), onIconSelected = {})

            Spacer(modifier = Modifier.height(32.dp))
            RatingSlider(sliderPosition = 5f, onSliderValueChange = {})

            Spacer(modifier = Modifier.weight(1f)) // Empujar la parte inferior hacia abajo

            BottomSection(selectedIcon = "Add", badgeNumber = 0, onSendClick = {})
        }
    }
}
