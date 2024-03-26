package com.example.celsiosxfahrenheit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.celsiosxfahrenheit.ui.theme.CelsiosxFahrenheitTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CelsiosxFahrenheitTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GetContent()
                }
            }
        }
    }
}

enum class ConversionType{
    CelsiusToFahrenheit,
    FahrenheitToCelsius
}

@Composable
fun GetContent(){
    val celsiusValue = remember { mutableStateOf(TextFieldValue()) }
    val fahrenheitValue = remember { mutableStateOf(TextFieldValue()) }
    val conversionType = remember { mutableStateOf(ConversionType.CelsiusToFahrenheit) }

    val entryText1 = if (conversionType.value == ConversionType.CelsiusToFahrenheit) "Celsius(ºC)" else "Fahrenheit(ºF)"
    val entryText2 = if (conversionType.value == ConversionType.FahrenheitToCelsius) "Celsius(ºC)" else "Fahrenheit(ºF)"

    val entryValue1 = if (conversionType.value == ConversionType.CelsiusToFahrenheit) celsiusValue else fahrenheitValue
    val entryValue2 = if (conversionType.value == ConversionType.FahrenheitToCelsius) celsiusValue else fahrenheitValue


    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally){
        GetEntryTemperature(text = entryText1, value = entryValue1)
        GetEntryTemperature(text = entryText2, value = entryValue2, enable = false)
        GetButtonResult(celsiusValue = celsiusValue, fahrenheitValue = fahrenheitValue, conversionType = conversionType)
        GetRadioButton(conversionType = conversionType, toType = ConversionType.CelsiusToFahrenheit, text = "Celsius para Fahrenheit")
        GetRadioButton(conversionType = conversionType, toType = ConversionType.FahrenheitToCelsius, text = "Fahrenheit para Celsius")

    }
}

@Composable()
fun GetEntryTemperature(text: String, value: MutableState<TextFieldValue>, enable: Boolean = true){
    Text(
        text = text,
        Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        fontSize = 24.sp
    )

    OutlinedTextField(
        enabled = enable ,
        value = value.value,
        onValueChange = { newValue ->
            value.value = newValue
        },
        modifier = Modifier.padding(10.dp),
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal)
    )
}

@Composable
fun GetButtonResult(celsiusValue: MutableState<TextFieldValue>, fahrenheitValue: MutableState<TextFieldValue>, conversionType: MutableState<ConversionType>){
    Button(onClick = {
        if (conversionType.value == ConversionType.CelsiusToFahrenheit) {
            val celsius = celsiusValue.value.text.toFloatOrNull()
            if (celsius != null) {
                val fahrenheit = celsius * 9 / 5 + 32
                fahrenheitValue.value = TextFieldValue("$fahrenheit")
            }
        } else {
            val fahrenheit = fahrenheitValue.value.text.toFloatOrNull()
            if (fahrenheit != null) {
                val celsius = (fahrenheit - 32) * 5 / 9
                celsiusValue.value = TextFieldValue("$celsius")
            }
        }
    }) {
        Text(text = "Calcular")
    }
}

@Composable
fun GetDropdownMenu(conversionType: MutableState<ConversionType>){
    Box(modifier = Modifier.fillMaxWidth()){
        DropdownMenu(
            modifier = Modifier.fillMaxWidth(),
            onDismissRequest = {},
            expanded = false,
        ) {
            DropdownMenuItem(onClick = { conversionType.value = ConversionType.CelsiusToFahrenheit }, text = {
                Text("Celsius para Fahrenheit")
            })
            DropdownMenuItem(onClick = { conversionType.value = ConversionType.FahrenheitToCelsius }, text = {
                Text("Fahrenheit para Celsius")
            })
        }
    }
}

@Composable
fun GetRadioButton(conversionType: MutableState<ConversionType>, toType: ConversionType, text: String){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        RadioButton(
            selected = conversionType.value == toType,
            onClick = { conversionType.value = toType },
            colors = RadioButtonDefaults.colors(MaterialTheme.colorScheme.primary)
        )
        Text(text)
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CelsiosxFahrenheitTheme {
        GetContent()
    }
}