package ky.apelaralash.fontines.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ky.apelaralash.fontines.domain.model.FontMatch

@Composable
fun FontMatchItem(
    fontMatch: FontMatch,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Основная информация
            Column(
                modifier = Modifier.weight(1f)
            ) {
                // Название шрифта
                Text(
                    text = fontMatch.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                
                if (fontMatch.category != null) {
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    Text(
                        text = fontMatch.category,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Индикатор похожести
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Похожесть:",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = "${fontMatch.similarityPercent}%",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = when {
                                fontMatch.similarity >= 0.8f -> MaterialTheme.colorScheme.primary
                                fontMatch.similarity >= 0.6f -> MaterialTheme.colorScheme.secondary
                                else -> MaterialTheme.colorScheme.tertiary
                            }
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(6.dp))
                    
                    // Прогресс бар похожести
                    LinearProgressIndicator(
                        progress = { fontMatch.similarity },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp)),
                        color = when {
                            fontMatch.similarity >= 0.8f -> MaterialTheme.colorScheme.primary
                            fontMatch.similarity >= 0.6f -> MaterialTheme.colorScheme.secondary
                            else -> MaterialTheme.colorScheme.tertiary
                        },
                        trackColor = MaterialTheme.colorScheme.surfaceVariant,
                        strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
                    )
                }
            }
            
            // Иконка перехода
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Подробнее",
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}
