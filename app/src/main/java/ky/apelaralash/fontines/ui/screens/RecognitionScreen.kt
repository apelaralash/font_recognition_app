package ky.apelaralash.fontines.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import ky.apelaralash.fontines.data.repository.FontRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecognitionScreen(
    onRecognitionComplete: () -> Unit,
    onBack: () -> Unit
) {
    val repository = remember { FontRepository() }
    var progress by remember { mutableFloatStateOf(0f) }
    var statusText by remember { mutableStateOf("Подготовка изображения...") }
    
    // Анимация вращения иконки
    val infiniteTransition = rememberInfiniteTransition(label = "rotation")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )
    
    // Симуляция прогресса распознавания
    LaunchedEffect(Unit) {
        val steps = listOf(
            0.2f to "Загрузка изображения...",
            0.4f to "Анализ текста...",
            0.6f to "Поиск шрифтов в базе...",
            0.8f to "Сравнение характеристик...",
            1.0f to "Завершение..."
        )
        
        steps.forEach { (targetProgress, text) ->
            statusText = text
            while (progress < targetProgress) {
                if (progress > 0.98f) {
                    progress = 1.0f
                } else {
                    progress += 0.02f
                }
                delay(100)
            }
        }
        
        delay(500)
        onRecognitionComplete()
    }
    
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = { Text("Распознавание") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Назад"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Анимированная иконка
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .rotate(rotation),
                    tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
                )
                
                Spacer(modifier = Modifier.height(32.dp))
                
                // Статус
                Text(
                    text = "Распознавание шрифта",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = statusText,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(32.dp))
                
                // Прогресс бар
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .height(8.dp),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.primaryContainer
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "${(progress * 100).toInt()}%",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
