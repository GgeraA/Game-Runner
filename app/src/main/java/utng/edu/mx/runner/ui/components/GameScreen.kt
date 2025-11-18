package utng.edu.mx.runner.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import utng.edu.mx.runner.ui.GameViewModel

/**
 * GameScreen es la pantalla principal donde se juega.
 *
 * COMPOSABLE: Una función que describe UI
 * - Recibe datos (parámetros)
 * - Devuelve UI (no explícitamente, sino por composición)
 * - Se recompone (redibuja) cuando los datos cambian
 *
 * ANALOGÍA: Es como una receta de cocina.
 * Cada vez que la ejecutas con los mismos ingredientes (parámetros),
 * obtienes el mismo plato (UI).
 *
 * @param viewModel El ViewModel que controla la lógica
 */
@Composable
fun GameScreen(viewModel: GameViewModel) {

    /**
     * Observamos el estado del juego.
     *
     * CONCEPTO: Reactive Programming
     * Cuando gameState cambia, este Composable se "recompone" (redibuja)
     *
     * collectAsState() convierte el Flow en un State observable por Compose
     */
    val gameState by viewModel.gameState.collectAsState()

    /**
     * LaunchedEffect inicia el juego solo una vez.
     *
     * CONCEPTO: Side Effect
     * Es una acción que ocurre "al lado" de la UI, no es UI en sí.
     *
     * La key "game_start" asegura que solo se ejecute una vez
     * (no cada vez que se recompone)
     */
    LaunchedEffect(key1 = "game_start") {
        viewModel.startGame()
    }

    /**
     * Box es un contenedor que apila elementos uno sobre otro.
     *
     * ANALOGÍA: Como apilar papeles sobre un escritorio.
     * El último elemento dibujado está arriba de todos.
     *
     * ESTRUCTURA:
     * - Fondo (cielo azul)
     * - Suelo (línea verde)
     * - Jugador
     * - Obstáculos
     * - HUD (puntuación)
     * - Dialog Game Over (si aplica)
     */
    Box(
        modifier = Modifier
            .fillMaxSize()  // Ocupa toda la pantalla
            .background(Color(0xFF87CEEB))  // Color celeste (cielo)
            .pointerInput(Unit) {
                // Detecta toques en toda la pantalla
                detectTapGestures {
                    viewModel.onJump()  // Cuando toca, salta
                }
            }
    ) {

        // SUELO: Línea verde en la parte inferior
        /**
         * Box con color verde que representa el suelo.
         *
         * CONCEPTO: Layout positioning
         * - fillMaxWidth() = ocupa todo el ancho
         * - height(8.dp) = 8 píxeles de alto
         * - align(Alignment.BottomCenter) = pegado al fondo
         */
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .align(Alignment.BottomCenter)
                .background(Color(0xFF228B22))  // Verde bosque
        )

        // JUGADOR: Nuestro personaje (alumno UTNG)
        /**
         * PlayerCharacter se posiciona dinámicamente según gameState.playerY
         *
         * CONCEPTO: Data-driven UI
         * La posición del jugador viene del estado, no de animaciones manuales
         */
        PlayerCharacter(
            modifier = Modifier.align(Alignment.BottomStart),
            yOffset = gameState.playerY
        )

        // OBSTÁCULOS: Dibujamos cada obstáculo de la lista
        /**
         * Iteramos sobre todos los obstáculos y los dibujamos.
         *
         * CONCEPTO: List rendering
         * Cada obstáculo es independiente pero comparte la misma lógica de dibujo
         */
        gameState.obstacles.forEach { obstacle ->
            ObstacleComponent(
                obstacle = obstacle,
                modifier = Modifier.align(Alignment.BottomStart)
            )
        }

        // HUD: Heads-Up Display (información en pantalla)
        /**
         * Mostramos la puntuación en la esquina superior derecha.
         *
         * CONCEPTO: HUD (Heads-Up Display)
         * Información que siempre está visible sobre el juego
         *
         * ANALOGÍA: Como el velocímetro en un coche.
         * Siempre visible pero no interfiere con la carretera.
         */
        Text(
            text = "Puntos: ${gameState.score}",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.TopEnd)  // Esquina superior derecha
                .padding(16.dp)
        )

        // INSTRUCCIONES: Texto de ayuda
        /**
         * Mostramos instrucciones en la parte superior.
         */
        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        ) {
            Text(
                text = "UTNG Runner",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1976D2)  // Azul UTNG
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Toca para saltar y evitar malos hábitos",
                fontSize = 14.sp,
                color = Color.DarkGray
            )
        }

        // GAME OVER DIALOG
        /**
         * Si el juego terminó, mostramos el diálogo de Game Over.
         *
         * CONCEPTO: Conditional rendering
         * Solo dibujamos el diálogo si isGameOver es true
         */
        if (gameState.isGameOver) {
            GameOverDialog(
                score = gameState.score,
                onRestart = { viewModel.restartGame() }
            )
        }
    }
}