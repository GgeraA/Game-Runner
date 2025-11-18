package utng.edu.mx.runner.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import utng.edu.mx.runner.data.GameState
import utng.edu.mx.runner.domain.GameEngine
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * GameViewModel es el "controlador" de nuestro juego.
 *
 * ANALOGÍA: Es como el director técnico de un equipo de fútbol.
 * - Los jugadores (Views) hacen lo que el director dice
 * - El director (ViewModel) toma decisiones basadas en las reglas (GameEngine)
 * - El director no juega, solo coordina
 *
 * RESPONSABILIDADES:
 * 1. Mantener el estado del juego
 * 2. Actualizar el juego cada frame
 * 3. Responder a las acciones del usuario (toques)
 * 4. Coordinar con el GameEngine
 *
 * MVVM (Model-View-ViewModel):
 * - Model: GameState, ObstacleType (datos)
 * - View: GameScreen, componentes UI (lo que ve el usuario)
 * - ViewModel: Esta clase (coordinador)
 *
 * VENTAJAS:
 * - La UI puede cambiar sin afectar la lógica
 * - Podemos testear la lógica sin la UI
 * - El estado sobrevive a rotaciones de pantalla
 */
class GameViewModel : ViewModel() {

    /**
     * _gameState es el estado PRIVADO (mutable)
     * Solo el ViewModel puede modificarlo
     *
     * CONCEPTO: Principio de encapsulación
     * Nadie de afuera puede cambiar el estado directamente
     */
    private val _gameState = MutableStateFlow(GameState())

    /**
     * gameState es el estado PÚBLICO (inmutable)
     * La UI puede observarlo pero no modificarlo
     *
     * ANALOGÍA: Es como ver un partido por TV.
     * Puedes ver lo que pasa, pero no puedes cambiar el marcador.
     */
    val gameState: StateFlow<GameState> = _gameState.asStateFlow()

    /**
     * Frame rate del juego (60 FPS = 16.67ms por frame)
     *
     * CONCEPTO: FPS (Frames Per Second)
     * - 60 FPS = actualizamos 60 veces por segundo
     * - Cada actualización tarda ~16ms
     * - El ojo humano no nota diferencia arriba de 60 FPS
     */
    private val frameDelayMillis = 16L

    /**
     * Indica si el bucle del juego está corriendo
     */
    private var isGameLoopRunning = false

    /**
     * Inicia el juego y el bucle de actualización.
     *
     * CONCEPTO: Game Loop (Bucle del Juego)
     * Es el corazón de cualquier videojuego.
     *
     * PSEUDOCÓDIGO:
     * ```
     * mientras juego_activo:
     *     procesar_input()     // Leer toques/teclas
     *     actualizar_lógica()  // Física, colisiones, IA
     *     renderizar()         // Dibujar en pantalla
     *     esperar(16ms)        // Mantener 60 FPS
     * ```
     *
     * ANALOGÍA: Es como el latido del corazón del juego.
     * Cada "latido" actualiza todo: enemigos, jugador, puntos.
     */
    fun startGame() {
        // Reiniciamos el estado
        _gameState.value = GameEngine.resetGame()

        // Si ya hay un bucle corriendo, no iniciamos otro
        if (isGameLoopRunning) return

        isGameLoopRunning = true

        // viewModelScope: Coroutine que se cancela automáticamente
        // cuando el ViewModel es destruido
        viewModelScope.launch {
            // Bucle infinito hasta que el juego termine
            while (isGameLoopRunning && !_gameState.value.isGameOver) {
                // Actualizamos el estado usando el GameEngine
                _gameState.value = GameEngine.updateGameState(_gameState.value)

                // Esperamos 16ms para mantener 60 FPS
                // CONCEPTO: Frame pacing - mantener velocidad constante
                delay(frameDelayMillis)
            }
            // Cuando salimos del bucle, marcamos que ya no está corriendo
            isGameLoopRunning = false
        }
    }

    /**
     * Maneja el salto del jugador.
     *
     * FLUJO:
     * 1. Usuario toca la pantalla
     * 2. La UI llama a este método
     * 3. Le pedimos al GameEngine que haga saltar al jugador
     * 4. Actualizamos el estado
     * 5. La UI reacciona automáticamente al cambio
     *
     * CONCEPTO: Flujo unidireccional de datos
     * User Action → ViewModel → GameEngine → New State → UI Update
     */
    fun onJump() {
        _gameState.value = GameEngine.jump(_gameState.value)
    }

    /**
     * Reinicia el juego cuando el usuario presiona "Reintentar".
     *
     * Similar a startGame() pero puede llamarse después de un Game Over
     */
    fun restartGame() {
        startGame()
    }

    /**
     * Limpia recursos cuando el ViewModel es destruido.
     *
     * CONCEPTO: Lifecycle management
     * Es importante detener el bucle para no desperdiciar recursos.
     *
     * ANALOGÍA: Es como apagar las luces cuando sales de una habitación.
     */
    override fun onCleared() {
        super.onCleared()
        isGameLoopRunning = false
    }
}