package com.example.cse225

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class CA1 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Dashboard()
        }
    }
}

@Composable
fun Dashboard() {

    val techList = listOf(
        "Java",
        "Kotlin",
        "Android",
        "AI",
        "Cybersecurity"
    )

    // ✅ Spinner options (fixed spelling)
    val options = listOf("All", "Java", "Kotlin", "Android", "AI", "Cybersecurity")
    var selectedOption by remember { mutableStateOf("All") }

    var ratings = remember { mutableStateMapOf<String, Int>() }

    // ✅ Filtering logic
    val filteredList = if (selectedOption == "All") {
        techList
    } else {
        techList.filter { it.equals(selectedOption, ignoreCase = true) }
    }

    Column(modifier = Modifier.padding(16.dp)) {

        // 🔹 Title
        Text(
            text = "Dashboard",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 🔽 Spinner
        DropdownMenuBox(options, selectedOption) {
            selectedOption = it
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 🔲 Grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(filteredList) { tech ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp),
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {

                        Text(
                            text = tech,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )

                        RatingBar(
                            rating = ratings[tech] ?: 0
                        ) { newRating ->
                            ratings[tech] = newRating
                        }

                        Text(
                            text = "Rating: ${ratings[tech] ?: 0}",
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DropdownMenuBox(
    options: List<String>,
    selected: String,
    onSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        Button(onClick = { expanded = true }) {
            Text(selected)
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach {
                DropdownMenuItem(
                    text = { Text(it) },
                    onClick = {
                        onSelected(it)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun RatingBar(
    rating: Int,
    onRatingChanged: (Int) -> Unit
) {
    Row {
        for (i in 1..5) {
            Icon(
                imageVector = if (i <= rating)
                    Icons.Default.Star
                else
                    Icons.Default.StarBorder,
                contentDescription = null,
                tint = Color(0xFFFFC107),
                modifier = Modifier
                    .size(30.dp)
                    .clickable { onRatingChanged(i) }
            )
        }
    }
}