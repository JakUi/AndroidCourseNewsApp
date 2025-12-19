@file:OptIn(ExperimentalMaterial3Api::class)

package com.klyschenko.news.presentation.screen.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.klyschenko.news.R
import com.klyschenko.news.presentation.ui.theme.CustomIcons
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import com.klyschenko.news.domain.entity.Language


@Preview
@Composable
fun SettingScreen(
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.padding(horizontal = 8.dp),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.settings)
                    )
                },
                navigationIcon = {
                    Icon(
                        modifier = Modifier.padding(8.dp),
                        imageVector = CustomIcons.materialIconsArrowBack,
                        contentDescription = stringResource(R.string.return_back)
                    )
                }
            )
        }


    ) { innerPadding ->
//        val state by viewModel.state.collectAsState()
        Column(
            modifier = Modifier
                .padding(innerPadding)
//            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Card(
                modifier = modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(top = 8.dp, start = 8.dp),
                        text = "Search Language",
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = "Select language for news search",
                        color = MaterialTheme.colorScheme.secondary
                    )
                    LanguageDropdown()
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Card(
                modifier = modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(top = 8.dp, start = 8.dp),
                        text = "Update Interval",
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = "How often to update news",
                        color = MaterialTheme.colorScheme.secondary
                    )
                    IntervalDropdown()
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Card(
                modifier = modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(top = 8.dp, start = 8.dp),
                        text = "Notifications",
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = "Show notifications about new articles",
                        color = MaterialTheme.colorScheme.secondary
                    )

                    var checked by remember { mutableStateOf(true) } // ????????
                    Switch(
                        checked = checked,
                        onCheckedChange = {
                            checked = it
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Card(
                modifier = modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        modifier = Modifier.padding(top = 8.dp, start = 8.dp),
                        text = "Update only via Wi-Fi",
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = "Save mobile data",
                        color = MaterialTheme.colorScheme.secondary
                    )

                    var checked by remember { mutableStateOf(true) } // ????????
                    Switch(
                        checked = checked,
                        onCheckedChange = {
                            checked = it
                        }
                    )
                }
            }

        }
    }
}

//@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageDropdown() {

    val options = Language.entries.map { it.lang }
    var expanded by remember { mutableStateOf(false) }
    var selected by remember { mutableStateOf(options[0]) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {

        TextField(
            value = selected,
            onValueChange = {},
            readOnly = true,
            label = { },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded)
            },
            modifier = Modifier
                .menuAnchor() // ОБЯЗАТЕЛЬНО
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        selected = option
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun IntervalDropdown() {

    val options = listOf("15 minutes", "30 minutes", "1 hour", "2 hours", "8 hours", "24 hours")
    var expanded by remember { mutableStateOf(false) }
    var selected by remember { mutableStateOf(options[0]) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {

        TextField(
            value = selected,
            onValueChange = {},
            readOnly = true,
            label = { },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded)
            },
            modifier = Modifier
                .menuAnchor() // ОБЯЗАТЕЛЬНО
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        selected = option
                        expanded = false
                    }
                )
            }
        }
    }
}