@file:OptIn(ExperimentalMaterial3Api::class)

package com.klyschenko.news.presentation.screen.settings
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.klyschenko.news.R
import androidx.compose.material3.TextField
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.klyschenko.news.data.mapper.toIntervalDropdown
import com.klyschenko.news.data.mapper.toLanguage
import com.klyschenko.news.data.mapper.toMinutes
import kotlin.collections.listOf


@Composable
fun SettingScreen(
    modifier: Modifier = Modifier,
    onBackButtonClick: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
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
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable { onBackButtonClick() },
//                        imageVector = CustomIcons.AutoMirrored.materialIconsArrowBack,
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.return_back)
                    )
                }
            )
        }


    ) { innerPadding ->
        val state by viewModel.state.collectAsState()
        Column(
            modifier = Modifier
                .padding(innerPadding)
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
                        text = stringResource(R.string.search_language),
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = stringResource(R.string.select_language_for_news_search),
                        color = MaterialTheme.colorScheme.secondary
                    )
                    DropdownMenu(
                        options = listOf("English", "Русский", "Français", "Deutsch"),
                        selected = state.language.lang,
                        onSelected = { chosen ->
                            viewModel.processCommand(SettingsCommands.UpdateLanguage(chosen.toLanguage()))
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
                        text = stringResource(R.string.update_interval),
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = stringResource(R.string.how_often_to_update_news),
                        color = MaterialTheme.colorScheme.secondary
                    )
                    DropdownMenu(
                        options = listOf("15 minutes", "30 minutes", "1 hour", "2 hours", "8 hours", "24 hours"),
                        selected = state.interval.minutes.toIntervalDropdown(),
                        onSelected = { chosen ->
                            viewModel.processCommand(SettingsCommands.UpdateInterval(chosen.toMinutes()))
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
                        text = stringResource(R.string.notifications),
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = stringResource(R.string.show_notifications_about_new_articles),
                        color = MaterialTheme.colorScheme.secondary
                    )
                    NotificationsSwitcher(state, viewModel)
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
                        text = stringResource(R.string.update_only_via_wi_fi),
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = stringResource(R.string.save_mobile_data),
                        color = MaterialTheme.colorScheme.secondary
                    )

                    Switch(
                        checked = state.wifiOnly,
                        onCheckedChange = { enabled ->
                            viewModel.processCommand(SettingsCommands.UpdateWifiOnly(enabled))
                        }
                    )
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenu(
    options: List<String>,
    selected: String,
    onSelected: (String) -> Unit
) {

    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = selected,
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            modifier = Modifier.menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun NotificationsSwitcher(
    state: SettingsState,
    viewModel: SettingsViewModel
) {
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        viewModel.processCommand(SettingsCommands.NotificationUpdate(granted))
    }
    val context = LocalContext.current

    val areNotificationsAllowed =
        NotificationManagerCompat.from(context).areNotificationsEnabled()

    val checked = if (areNotificationsAllowed) {
        state.notificationsEnabled
    } else {
        viewModel.processCommand(SettingsCommands.NotificationUpdate(false))
        false
    }

    Switch(
        checked = checked,
        onCheckedChange = { enabled ->
            if (!enabled) {
                viewModel.processCommand(SettingsCommands.NotificationUpdate(false))
                return@Switch
            }

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                viewModel.processCommand(SettingsCommands.NotificationUpdate(true))
            } else {
                permissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    )
}