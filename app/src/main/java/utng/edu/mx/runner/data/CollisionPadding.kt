package utng.edu.mx.runner.data

/**
 * Define los márgenes de colisión para cada lado del obstáculo.
 * Esto crea una hitbox más pequeña y precisa dentro del área visual.
 */
data class CollisionPadding(
    val top: Float = 0f,
    val bottom: Float = 0f,
    val left: Float = 0f,
    val right: Float = 0f
)