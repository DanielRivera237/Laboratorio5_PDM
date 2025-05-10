// 📁 ui/components/cards.kt
package com.falconteam.laboratorio5.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.falconteam.laboratorio5.models.Card
import com.falconteam.laboratorio5.ui.theme.Green
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Cards(
    card: Card,
    pos: Int,
    max: Int,
    delete: (Int) -> Unit = {},
    check: (Boolean, Int) -> Unit = { _, _ -> },
    changePosition: (Int, Int) -> Unit = { _, _ -> }
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (card.checked) Green else MaterialTheme.colorScheme.primary,
            contentColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = card.title,
                    style = MaterialTheme.typography.headlineSmall
                )
                Row {
                    Button(
                        onClick = { changePosition(pos, pos - 1) },
                        enabled = pos > 0,
                        modifier = Modifier.padding(start = 2.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowUp,
                            contentDescription = "Subir",
                            tint = Color.White
                        )
                    }
                    Button(
                        onClick = { changePosition(pos, pos + 1) },
                        enabled = pos < max,
                        modifier = Modifier.padding(start = 2.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = "Bajar",
                            tint = Color.White
                        )
                    }
                }
            }
            Text(
                text = card.description,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Text(
                text = "Fecha de entrega: ${card.endDate}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = { delete(pos) }) {
                    Text("Eliminar", textAlign = TextAlign.Start)
                }
                Checkbox(
                    checked = card.checked,
                    onCheckedChange = { check(it, pos) }
                )
            }
        }
    }
}

@Composable
@Preview
fun CardsPreview() {
    Cards(
        card = Card(0, "Title", "Description", Date(), false),
        pos = 0,
        max = 5,
        delete = {},
        check = { _, _ -> }
    )
}
