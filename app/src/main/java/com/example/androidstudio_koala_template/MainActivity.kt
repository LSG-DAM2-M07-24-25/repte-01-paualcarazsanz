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
                var selectedIcon by remember { mutableStateOf("Add") } // Estado para almacenar el ícono seleccionado
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        // Parte superior: Título, Dropdown y Slider
                        Title()
                        Spacer(modifier = Modifier.height(8.dp))

                        // Pasamos la función onIconSelected que actualiza el estado de selectedIcon
                        MyDropDownMenu(modifier = Modifier.padding(horizontal = 16.dp), onIconSelected = { icon ->
                            selectedIcon = icon
                        })

                        Spacer(modifier = Modifier.height(32.dp))
                        RatingSlider()

                        // Espacio para empujar la parte inferior hacia abajo
                        Spacer(modifier = Modifier.weight(1f))

                        // Parte inferior: Botón y Badge Icon
                        BottomSection(selectedIcon = selectedIcon)
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
    var selectedText: String by remember { mutableStateOf("Add") } // Inicializa con un valor predeterminado
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
                        onIconSelected(name)  // Llama la función para actualizar el ícono seleccionado
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

@Composable
fun BottomSection(selectedIcon: String) {
    var sliderPosition by remember { mutableStateOf(5f) }

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

        Button(
            onClick = {
                // No es necesario cambiar nada aquí por ahora
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Enviar")
        }

        Spacer(modifier = Modifier.height(8.dp)) // Espaciado entre el botón y el ícono
        MyBadgeIconWithNumber(selectedText = selectedIcon) // Muestra el ícono seleccionado
    }
}

@Composable
fun MyBadgeIconWithNumber(selectedText: String) {
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
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    AndroidStudioKoalaTemplateTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            Title()
            Spacer(modifier = Modifier.height(8.dp))

            // Llama a MyDropDownMenu pasando la función que actualiza el ícono seleccionado
            MyDropDownMenu(modifier = Modifier.padding(horizontal = 16.dp), onIconSelected = {})

            Spacer(modifier = Modifier.height(32.dp))
            RatingSlider()

            Spacer(modifier = Modifier.weight(1f)) // Empujar la parte inferior hacia abajo

            BottomSection(selectedIcon = "Add")
        }
    }
}
