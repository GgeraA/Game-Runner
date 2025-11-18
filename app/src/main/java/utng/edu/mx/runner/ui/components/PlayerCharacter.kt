package utng.edu.mx.runner.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * PlayerCharacter dibuja el alumno UTNG (nuestro personaje).
 */
@Composable
fun PlayerCharacter(
    modifier: Modifier = Modifier,
    yOffset: Float
) {
    Box(
        modifier = modifier
            .offset(x = 100.dp, y = yOffset.dp)
            .size(100.dp),  // Tamaño visual del jugador
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "🧑‍🎓",
            fontSize = 60.sp
        )
    }
}