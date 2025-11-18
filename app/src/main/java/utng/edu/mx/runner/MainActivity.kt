package utng.edu.mx.runner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import utng.edu.mx.runner.ui.components.GameScreen
import utng.edu.mx.runner.ui.GameViewModel

/**
 * MainActivity es la actividad principal de la aplicación.
 *
 * CONCEPTO: Activity
 * Una Activity es una pantalla en Android.
 * Es el punto de entrada de tu app.
 *
 * ANALOGÍA: Es como la puerta principal de una casa.
 * Todo el que entra a tu app, entra por aquí.
 *
 * ComponentActivity es la clase base para apps con Jetpack Compose.
 */
class MainActivity : ComponentActivity() {

    /**
     * onCreate es el método que se ejecuta cuando la actividad se crea.
     *
     * CICLO DE VIDA:
     * onCreate() → onStart() → onResume() → (App corriendo)
     *
     * ANALOGÍA: Es como llegar a una fiesta.
     * - onCreate: Entras y te presentas
     * - onStart: Te quitas el abrigo
     * - onResume: Empiezas a socializar
     *
     * @param savedInstanceState Estado guardado de ejecuciones anteriores
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**
         * enableEdgeToEdge permite usar toda la pantalla,
         * incluyendo las barras de sistema (status bar, navigation bar).
         *
         * EFECTO: La app se ve moderna y "full screen"
         */
        enableEdgeToEdge()

        /**
         * setContent define el contenido UI de la Activity.
         *
         * CONCEPTO: Compose UI
         * En lugar de usar XML (activity_main.xml), definimos la UI con código.
         *
         * VENTAJA:
         * - Más fácil de modificar
         * - Menos archivos que mantener
         * - UI reactiva automáticamente
         */
        setContent {
            /**
             * MaterialTheme aplica el diseño Material Design 3.
             *
             * Material Design es el sistema de diseño de Google.
             * Define colores, formas, tipografías consistentes.
             *
             * ANALOGÍA: Es como usar una plantilla de diseño profesional
             * en PowerPoint. Todo se ve coherente automáticamente.
             */
            MaterialTheme {
                /**
                 * Surface es un contenedor básico con color de fondo.
                 *
                 * PROPÓSITO:
                 * Proporciona un fondo coherente con el tema de Material
                 */
                Surface(
                    modifier = Modifier.fillMaxSize(),  // Ocupa toda la pantalla
                    color = MaterialTheme.colorScheme.background  // Color del tema
                ) {
                    /**
                     * viewModel() crea o recupera el ViewModel.
                     *
                     * CONCEPTO: ViewModel Lifecycle
                     * El ViewModel sobrevive a rotaciones de pantalla.
                     *
                     * EJEMPLO:
                     * 1. Usuario está jugando (puntaje = 500)
                     * 2. Usuario rota el teléfono
                     * 3. Activity se destruye y recrea
                     * 4. ViewModel sigue vivo con puntaje = 500
                     *
                     * Sin ViewModel, perderías el progreso al rotar.
                     */
                    val viewModel: GameViewModel = viewModel()

                    /**
                     * GameScreen es nuestra pantalla de juego.
                     * Le pasamos el ViewModel para que pueda controlar el juego.
                     */
                    GameScreen(viewModel = viewModel)
                }
            }
        }
    }
}