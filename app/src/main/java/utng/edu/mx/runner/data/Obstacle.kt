package utng.edu.mx.runner.data

/**
 * Obstacle representa un obstáculo individual en el juego.
 *
 * ANALOGÍA: Es como una persona u objeto que te encuentras en tu camino.
 * Tiene una posición (dónde está) y un tipo (qué es).
 *
 * @property x Posición horizontal del obstáculo (de derecha a izquierda)
 * @property type Tipo de obstáculo (JunkFood, Alcohol, Drugs, etc.)
 */
data class Obstacle(
    val x: Float,                      // Posición en el eje X
    val type: ObstacleType            // Qué tipo de obstáculo es
) {
    companion object {
        /**
         * Ancho visual del obstáculo en píxeles.
         * Usado para detectar colisiones.
         */
        const val WIDTH = 70f

        /**
         * Alto visual del obstáculo en píxeles.
         * Usado para detectar colisiones.
         */
        const val HEIGHT = 80f

        /**
         * Márgenes de colisión personalizados por tipo de obstáculo.
         * Esto define cuánto "recortar" de los bordes para la detección real.
         */
        fun getCollisionPadding(type: ObstacleType): CollisionPadding {
            return when (type) {
                ObstacleType.JunkFood -> CollisionPadding(
                    top = 15f,     // Recortar parte superior del emoji
                    bottom = 5f,   // Recortar muy poco del fondo
                    left = 10f,    // Recortar bordes izquierdos
                    right = 10f    // Recortar bordes derechos
                )
                ObstacleType.Alcohol -> CollisionPadding(
                    top = 10f,
                    bottom = 5f,
                    left = 8f,
                    right = 8f
                )
                ObstacleType.Drugs -> CollisionPadding(
                    top = 5f,
                    bottom = 5f,
                    left = 12f,
                    right = 12f
                )
                ObstacleType.Procrastination -> CollisionPadding(
                    top = 20f,     // TV tiene mucho espacio vacío arriba
                    bottom = 5f,
                    left = 15f,
                    right = 15f
                )
                ObstacleType.SleepDeprivation -> CollisionPadding(
                    top = 25f,     // Emoji de sueño tiene mucho espacio arriba
                    bottom = 5f,
                    left = 10f,
                    right = 10f
                )
                ObstacleType.SocialMedia -> CollisionPadding(
                    top = 10f,
                    bottom = 5f,
                    left = 8f,
                    right = 8f
                )
                ObstacleType.Stress -> CollisionPadding(
                    top = 5f,
                    bottom = 5f,
                    left = 10f,
                    right = 10f
                )
            }
        }
    }
}