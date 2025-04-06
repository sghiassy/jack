package com.shaheenghiassy.jack.ui.mainview

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.shaheenghiassy.jack.ui.theme.JACKTheme

@Composable
fun CounterText(modifier: Modifier = Modifier, str: String) {
    Text(
        text = str,
        modifier = modifier,
        style = MaterialTheme.typography.headlineLarge.copy(fontSize = 40.sp)
    )
}

@Preview(showBackground = true)
@Composable
fun CounterTextPreview() {
    JACKTheme {
        CounterText(str = "654")
    }
}
