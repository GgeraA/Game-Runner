package utng.edu.mx.runner.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import utng.edu.mx.runner.data.Obstacle

@Composable
fun ObstacleComponent(
    obstacle: Obstacle,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .offset(x = obstacle.x.dp, y = 0.dp)
            .size(
                width = Obstacle.WIDTH.dp,
                height = Obstacle.HEIGHT.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = obstacle.type.emoji,
            fontSize = 50.sp
        )
        Text(
            text = obstacle.type.name,
            fontSize = 10.sp
        )
    }
}