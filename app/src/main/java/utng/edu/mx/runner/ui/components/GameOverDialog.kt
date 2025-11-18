package utng.edu.mx.runner.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

/**
 * GameOverDialog muestra el diálogo cuando el jugador pierde.
 *
 * CONCEPTO: Modal Dialog
 * Es una ventana que aparece sobre el juego y bloquea la interacción
 * hasta que el usuario tome una decisión.
 *
 * ANALOGÍA: Como una alerta en tu teléfono.
 * No puedes hacer nada más hasta que la cierres o respondas.
 *
 * CONTENIDO:
 * - Título "Game Over"
 * - Mensaje motivacional
 * - Puntuación final
 * - Botón para reiniciar
 *
 * @param score Puntuación final del jugador
 * @param onRestart Callback que se ejecuta al presionar "Reintentar"
 */
@Composable
fun GameOverDialog(
    score: Int,
    onRestart: () -> Unit  // Función lambda sin parámetros que no devuelve nada
) {
    /**
     * Dialog es un componente de Material Design.
     *
     * PROPIEDADES:
     * - onDismissRequest: Qué hacer al tocar fuera del diálogo
     *   (aquí lo dejamos vacío para que NO se pueda cerrar sin reiniciar)
     */
    Dialog(onDismissRequest = { /* No permitimos cerrar sin reiniciar */ }) {
        /**
         * Card personalizado para el contenido del diálogo.
         *
         * DISEÑO:
         * - Fondo blanco
         * - Bordes redondeados
         * - Padding generoso
         */
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)  // 90% del ancho de la pantalla
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(16.dp)  // Esquinas redondeadas
                )
                .padding(24.dp)  // Espacio interno
        ) {
            /**
             * Column organiza los elementos verticalmente.
             */
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)  // Espacio entre elementos
            ) {

                // TÍTULO "GAME OVER"
                Text(
                    text = "¡Game Over!",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFD32F2F)  // Rojo de advertencia
                )

                // EMOJI TRISTE
                Text(
                    text = "😢",
                    fontSize = 48.sp
                )

                // MENSAJE MOTIVACIONAL
                /**
                 * Mensaje que relaciona el juego con la vida real.
                 *
                 * OBJETIVO EDUCATIVO:
                 * Reforzar el mensaje de tomar decisiones saludables.
                 */
                Text(
                    text = "Los malos hábitos te alcanzaron.\nRecuerda: Tu salud es tu mejor inversión.",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    color = Color.DarkGray
                )

                // PUNTUACIÓN FINAL
                /**
                 * Mostramos los puntos obtenidos.
                 *
                 * GAMIFICACIÓN:
                 * Ver tu puntuación motiva a intentar superarla en el siguiente intento.
                 */
                Box(
                    modifier = Modifier
                        .background(
                            color = Color(0xFF1976D2),  // Azul UTNG
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 24.dp, vertical = 12.dp)
                ) {
                    Text(
                        text = "Puntuación: $score",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                // CONSEJOS DE SALUD
                /**
                 * Mensaje educativo sobre hábitos saludables.
                 *
                 * PROPÓSITO:
                 * Aprovechar el "momento de reflexión" del Game Over
                 * para reforzar el aprendizaje.
                 */
                Text(
                    text = "💡 Consejo: Come bien, hidrátate, y evita sustancias nocivas.",
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    color = Color(0xFF388E3C)  // Verde esperanza
                )

                Spacer(modifier = Modifier.height(8.dp))

                // BOTÓN REINTENTAR
                /**
                 * Button es el componente de Material Design para botones.
                 *
                 * CALLBACK: onRestart es una función que se pasa como parámetro.
                 * Cuando el usuario presiona el botón, se ejecuta esta función.
                 */
                Button(
                    onClick = onRestart,  // Ejecutar el callback
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4CAF50)  // Verde "Go"
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Text(
                        text = "🔄 Reintentar",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}