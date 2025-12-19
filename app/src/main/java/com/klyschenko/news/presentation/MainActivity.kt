package com.klyschenko.news.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.klyschenko.news.domain.repository.NewsRepository
import com.klyschenko.news.presentation.navigation.NavGraph
import com.klyschenko.news.presentation.ui.theme.NewsTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var repository: NewsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsTheme {
                NavGraph()
                // !!!!!!!!!!!!! Вернуть запрос на разрешение уведомлений
//                val permissionLauncher = rememberLauncherForActivityResult(
//                    contract = ActivityResultContracts.RequestPermission(),
//                    onResult = {}
//                )
//                SubscriptionsScreen(
//                    onNavigateToSettings = {
////                        val intent = Intent( this, MainActivity::class.java) // это явный intent
//                        // мы передаём какое Activity нужно открыть
////                        val intent = Intent( Intent.ACTION_DIAL, "tel:+84894848484".toUri()) // это не явный intent параметром uri
//                        // мы передаём действие которое должно быть выполнено (здесь: открыть звонилку и позвонить по этому номеру,
//                        // но не знаем какой Activity его выполнит
////                        val intent = Intent( Intent.ACTION_VIEW, "https://google.com".toUri()) // это не явный intent параметром uri
////                        startActivity(intent)
//                        // так можно отправить контент
////                        val intent = Intent( Intent.ACTION_SEND).apply {
////                            type = "text/plain"
////                            putExtra(Intent.EXTRA_TEXT, "Hello World!")
////                        }
////                        startActivity(intent)
//
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                            permissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
//                        }
//                    }
//                )
////                SettingScreen()
            }
        }
    }
}
