package utng.edu.mx.runner.data

import utng.edu.mx.runner.data.ObstacleType



/**
 * GameState representa el estado completo del juego en cualquier momento.
 *
 * ANALOGÍA: Es como el tablero de un juego de mesa.
 * Puedes ver todos los datos importantes de un vistazo:
 * - ¿Dónde está el jugador?
 * - ¿Dónde están los obstáculos?
 * - ¿Cuántos puntos llevas?
 * - ¿El juego está corriendo o terminó?
 *
 * INMUTABILIDAD: Usamos 'data class' porque cada estado es como
 * una foto. No modificamos la foto, creamos una nueva foto con los cambios.
 *
 * @property playerY Posición vertical del jugador (altura del salto)
 * @property obstacles Lista de todos los obstáculos en pantalla
 * @property score Puntuación actual del jugador
 * @property isGameOver Si el juego ha terminado o no
 * @property isJumping Si el jugador está saltando actualmente
 * @property gameSpeed Velocidad del juego (aumenta con el tiempo)
 */
data class GameState(
    val playerY: Float = 0f,           // 0f = en el suelo, >0 = en el aire
    val playerVelocity: Float = 0f,  // ← NUEVO
    val obstacles: List<Obstacle> = emptyList(),
    val score: Int = 0,
    val isGameOver: Boolean = false,
    val isJumping: Boolean = false,
    val gameSpeed: Float = 5f          // Píxeles por frame
) {
    companion object {
        /**
         * Altura máxima que puede alcanzar el jugador al saltar.
         *
         * ANALOGÍA: Es como la altura que puedes alcanzar al saltar
         * en la vida real. No puedes saltar hasta el techo, hay un límite.
         */
        const val MAX_JUMP_HEIGHT = 240f

        /**
         * Velocidad inicial de salto (pixeles por frame).
         *
         * CONCEPTO: Una velocidad negativa significa "hacia arriba" en pantalla.
         * En Android, Y=0 está arriba, y Y aumenta hacia abajo.
         */
        const val JUMP_VELOCITY = -20f

        /**
         * Gravedad aplicada al jugador (pixeles por frame).
         *
         * CONCEPTO: La gravedad es siempre positiva (tira hacia abajo).
         * Es la fuerza que te hace volver al suelo después de saltar.
         */
        const val GRAVITY = 1f
    }
}

/**
 * Obstacle representa un obstáculo individual en el juego.
 *
 * ANALOGÍA: Es como una persona u objeto que te encuentras en tu camino.
 * Tiene una posición (dónde está) y un tipo (qué es).
 *
 * @property x Posición horizontal del obstáculo (de derecha a izquierda)
 * @property type Tipo de obstáculo (JunkFood, Alcohol, o Drugs)
 */