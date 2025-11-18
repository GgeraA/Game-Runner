package utng.edu.mx.runner.domain

import utng.edu.mx.runner.data.GameState
import utng.edu.mx.runner.data.Obstacle
import utng.edu.mx.runner.data.ObstacleType

/**
 * GameEngine es el motor del juego, la lógica central.
 *
 * ANALOGÍA: Es como el árbitro en un partido de fútbol.
 * El árbitro no juega, pero:
 * - Decide si hay falta (colisión)
 * - Cuenta los puntos
 * - Controla el tiempo
 * - Hace cumplir las reglas
 *
 * RESPONSABILIDADES:
 * 1. Actualizar el estado del juego cada frame
 * 2. Detectar colisiones
 * 3. Generar obstáculos
 * 4. Calcular la física del salto
 * 5. Incrementar la puntuación
 *
 * PRINCIPIO DE RESPONSABILIDAD ÚNICA:
 * Esta clase SOLO se encarga de la lógica del juego.
 * No sabe nada sobre la UI (botones, colores, etc.)
 */
object GameEngine {

    /**
     * Ancho de la pantalla del juego en píxeles lógicos.
     * Los obstáculos aparecen desde aquí.
     */
    private const val SCREEN_WIDTH = 1080f

    private const val COLLISION_PADDING = 10f

    /**
     * Distancia mínima entre obstáculos.
     * ANALOGÍA: Es como el espacio mínimo entre dos personas en una fila.
     */
    private const val MIN_OBSTACLE_DISTANCE = 300f

    /**
     * Actualiza el estado del juego en cada frame (fotograma).
     *
     * ANALOGÍA: Es como actualizar la posición de todas las piezas
     * en un juego de ajedrez después de cada turno.
     *
     * ¿QUÉ PASA EN CADA FRAME?
     * 1. Si el juego terminó, no hacemos nada
     * 2. Movemos los obstáculos hacia la izquierda
     * 3. Eliminamos obstáculos que salieron de pantalla
     * 4. Generamos nuevos obstáculos si es necesario
     * 5. Actualizamos la física del salto
     * 6. Detectamos colisiones
     * 7. Incrementamos la puntuación
     * 8. Aumentamos la velocidad gradualmente
     *
     * @param currentState El estado actual del juego
     * @return Un nuevo estado del juego actualizado
     */
    fun updateGameState(currentState: GameState): GameState {
        // Si el juego terminó, devolvemos el estado sin cambios
        // ANALOGÍA: Si el partido terminó, no seguimos jugando
        if (currentState.isGameOver) return currentState

        // Paso 1: Mover todos los obstáculos hacia la izquierda
        // CONCEPTO: Restamos gameSpeed de la posición X
        // Si X disminuye, el objeto se mueve a la izquierda
        val movedObstacles = currentState.obstacles.map { obstacle ->
            obstacle.copy(x = obstacle.x - currentState.gameSpeed)
        }

        // Paso 2: Filtrar obstáculos que salieron de pantalla (x < -100)
        // ANALOGÍA: Como quitar del juego las piezas que cayeron de la mesa
        val visibleObstacles = movedObstacles.filter { it.x > -100 }

        // Paso 3: Generar nuevos obstáculos si es necesario
        // LÓGICA: Solo generamos si no hay obstáculos o si el último
        // está lo suficientemente lejos
        val obstacles = if (shouldSpawnObstacle(visibleObstacles)) {
            visibleObstacles + createNewObstacle()
        } else {
            visibleObstacles
        }

        // Paso 4: Actualizar la física del jugador
        val (newPlayerY, newVelocity, newIsJumping) = updatePlayerPhysics(
            currentState.playerY,
            currentState.playerVelocity,  // ← AGREGAR
            currentState.isJumping
        )
        // Paso 5: Detectar colisiones
        // CONCEPTO: Verificamos si algún obstáculo está tocando al jugador
        val collision = detectCollision(newPlayerY, obstacles)

        // Paso 6: Calcular nueva puntuación
        // LÓGICA: Sumamos 1 punto por cada frame que sobrevivimos
        val newScore = if (collision) currentState.score else currentState.score + 1

        // Paso 7: Aumentar velocidad gradualmente
        // CONCEPTO: Cada 500 puntos, aumentamos 0.5 la velocidad
        // Esto hace el juego más difícil con el tiempo
        val newSpeed = calculateGameSpeed(newScore)

        // Devolvemos el nuevo estado del juego
        // INMUTABILIDAD: No modificamos currentState, creamos uno nuevo
        return currentState.copy(
            playerY = newPlayerY,
            playerVelocity = newVelocity,
            obstacles = obstacles,
            score = newScore,
            isGameOver = collision,
            isJumping = newIsJumping,
            gameSpeed = newSpeed
        )
    }

    /**
     * Inicia un salto si el jugador está en el suelo.
     *
     * ANALOGÍA: Es como cuando flexionas las piernas para saltar.
     * Solo puedes saltar si estás en el suelo, no en el aire.
     *
     * @param currentState Estado actual del juego
     * @return Nuevo estado con el salto iniciado (o sin cambios si ya está saltando)
     */
    fun jump(currentState: GameState): GameState {
        // Solo permitimos saltar si:
        // 1. No estamos ya saltando
        // 2. El juego no ha terminado
        return if (!currentState.isJumping && !currentState.isGameOver) {
            currentState.copy(
                isJumping = true,
                playerVelocity = GameState.JUMP_VELOCITY  // ← AGREGAR ESTA LÍNEA
            )
        } else {
            currentState
        }
    }

    /**
     * Reinicia el juego al estado inicial.
     *
     * ANALOGÍA: Como reiniciar un juego de mesa,
     * volvemos todas las piezas a su posición inicial.
     *
     * @return Un nuevo GameState con valores iniciales
     */
    fun resetGame(): GameState {
        return GameState()  // Estado inicial por defecto
    }

    /**
     * Actualiza la física del jugador (gravedad y salto).
     *
     * FÍSICA DEL SALTO:
     * 1. Al saltar, el jugador tiene velocidad hacia arriba (negativa)
     * 2. La gravedad reduce esta velocidad gradualmente
     * 3. Eventualmente, la velocidad se vuelve positiva (cae)
     * 4. El jugador regresa al suelo (Y = 0)
     *
     * ANALOGÍA: Es como lanzar una pelota al aire:
     * - Sube rápido al principio
     * - Pierde velocidad
     * - Se detiene en el punto más alto
     * - Cae de vuelta
     *
     * @param currentY Posición Y actual del jugador
     * @param isJumping Si el jugador está saltando
     * @return Par de (nueva posición Y, si sigue saltando)
     */
    // En GameEngine.kt - updatePlayerPhysics usa física correcta
    private fun updatePlayerPhysics(
        currentY: Float,
        currentVelocity: Float,  // ← AGREGAR
        isJumping: Boolean
    ): Triple<Float, Float, Boolean>  // ← CAMBIAR Pair a Triple

// REEMPLAZAR todo el cuerpo con (VERSIÓN CORREGIDA v1.2):
    {
        if (!isJumping) return Triple(0f, 0f, false)

        // Aplicar gravedad y velocidad
        val newVelocity = currentVelocity + GameState.GRAVITY
        val newY = currentY + newVelocity

        // PRIMERO: Verificar si tocó el suelo
        if (newY >= 0f) return Triple(0f, 0f, false)

        // SEGUNDO: Límite superior - MANTENER velocidad (no resetear)
        if (newY < -GameState.MAX_JUMP_HEIGHT) {
            // ✅ CORRECCIÓN v1.2: Mantener velocidad para caída natural
            return Triple(-GameState.MAX_JUMP_HEIGHT, newVelocity, true)
        }

        // Sigue en el aire normalmente
        return Triple(newY, newVelocity, true)
    }

    /**
     * Detecta si hay colisión entre el jugador y algún obstáculo.
     *
     * CONCEPTO DE COLISIÓN:
     * Dos rectángulos chocan si sus áreas se superponen.
     *
     * ANALOGÍA: Es como saber si dos cajas se están tocando.
     * Si las esquinas de una caja están dentro de la otra caja, chocan.
     *
     * HITBOX DEL JUGADOR:
     * - X: 100 a 200 (ancho de 100px)
     * - Y: playerY a playerY + 100 (alto de 100px)
     *
     * @param playerY Posición Y del jugador
     * @param obstacles Lista de obstáculos a verificar
     * @return true si hay colisión, false si está seguro
     */
    /**
     * Detecta si hay colisión entre el jugador y algún obstáculo.
     */
    private fun detectCollision(
        playerY: Float,
        obstacles: List<Obstacle>
    ): Boolean {
        // HITBOX DEL JUGADOR - Más precisa (solo la parte inferior)
        val playerPadding = 25f  // Aumentado para mejor precisión
        val playerLeft = 100f + playerPadding
        val playerRight = 200f - playerPadding
        val playerTop = playerY + 40f  // Ignorar parte superior del jugador
        val playerBottom = playerY + 80f  // Solo los pies colisionan

        // Verificamos colisión con cada obstáculo
        return obstacles.any { obstacle ->
            // Obtener márgenes específicos para este tipo de obstáculo
            val padding = Obstacle.getCollisionPadding(obstacle.type)

            // HITBOX DEL OBSTÁCULO - Aplicar márgenes personalizados
            val obstacleLeft = obstacle.x + padding.left
            val obstacleRight = obstacle.x + Obstacle.WIDTH - padding.right
            val obstacleTop = padding.top  // Recortar parte superior
            val obstacleBottom = Obstacle.HEIGHT - padding.bottom  // Recortar parte inferior

            // Lógica de colisión AABB mejorada
            val horizontalCollision = playerRight > obstacleLeft && playerLeft < obstacleRight
            val verticalCollision = playerBottom > obstacleTop && playerTop < obstacleBottom

            horizontalCollision && verticalCollision
        }
    }

    /**
     * Determina si debemos generar un nuevo obstáculo.
     *
     * LÓGICA:
     * - Si no hay obstáculos, generamos uno
     * - Si el último obstáculo está suficientemente lejos, generamos otro
     *
     * ANALOGÍA: Es como poner más conos en una pista de obstáculos.
     * Solo pones uno nuevo cuando hay suficiente espacio.
     *
     * @param obstacles Lista actual de obstáculos
     * @return true si debemos crear un nuevo obstáculo
     */
    private fun shouldSpawnObstacle(obstacles: List<Obstacle>): Boolean {
        if (obstacles.isEmpty()) return true

        // Obtenemos el obstáculo más a la derecha (el último generado)
        val lastObstacle = obstacles.maxByOrNull { it.x } ?: return true

        // Verificamos si está lo suficientemente lejos
        return (SCREEN_WIDTH - lastObstacle.x) > MIN_OBSTACLE_DISTANCE
    }

    /**
     * Crea un nuevo obstáculo en el borde derecho de la pantalla.
     *
     * @return Un nuevo obstáculo con tipo aleatorio
     */
    private fun createNewObstacle(): Obstacle {
        return Obstacle(
            x = SCREEN_WIDTH,              // Aparece en el borde derecho
            type = ObstacleType.random()   // Tipo aleatorio
        )
    }

    /**
     * Calcula la velocidad del juego basada en la puntuación.
     *
     * CONCEPTO: Dificultad progresiva
     * Mientras más tiempo sobrevives, más rápido va el juego.
     *
     * FÓRMULA:
     * velocidad = velocidad_base + (puntos / 500) * 0.5
     *
     * EJEMPLO:
     * - 0 puntos: velocidad = 5.0
     * - 500 puntos: velocidad = 5.5
     * - 1000 puntos: velocidad = 6.0
     * - 2000 puntos: velocidad = 7.0
     *
     * @param score Puntuación actual
     * @return Nueva velocidad del juego
     */
    private fun calculateGameSpeed(score: Int): Float {
        val baseSpeed = 5f
        val speedIncrease = (score / 500) * 0.5f
        return baseSpeed + speedIncrease
    }
}
