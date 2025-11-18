package utng.edu.mx.runner.data

/**
 * ObstacleType representa los diferentes tipos de obstáculos (malos hábitos)
 * que el estudiante debe evitar en su camino universitario.
 *
 * ANALOGÍA: Es como una lista de tentaciones que encuentras en tu día a día:
 * - La comida chatarra en el kiosko
 * - Las fiestas con alcohol
 * - Las drogas que algunos ofrecen
 * - La procrastinación que te hace perder tiempo
 *
 * @property emoji El emoji que representa visualmente el obstáculo
 * @property name El nombre descriptivo del obstáculo
 * @property description Una breve descripción del riesgo que representa
 */
sealed class ObstacleType(
    val emoji: String,
    val name: String,
    val description: String
) {
    /**
     * Representa la comida chatarra y mala alimentación.
     * EJEMPLO: Comer pizza y hamburguesas todos los días en vez de comida balanceada
     */
    object JunkFood : ObstacleType(
        emoji = "🍔",
        name = "Comida Chatarra",
        description = "La mala alimentación afecta tu rendimiento académico"
    )

    /**
     * Representa el consumo de alcohol.
     * EJEMPLO: Las fiestas excesivas que te hacen perder clases y concentración
     */
    object Alcohol : ObstacleType(
        emoji = "🍺",
        name = "Alcohol",
        description = "El alcohol daña tu cerebro y tu futuro"
    )

    /**
     * Representa las drogas.
     * EJEMPLO: Cualquier sustancia que destruye tu salud y vida universitaria
     */
    object Drugs : ObstacleType(
        emoji = "\uD83D\uDEAC",
        name = "Drogas",
        description = "Las drogas destruyen tu vida y sueños"
    )

    /**
     * Representa la procrastinación y dejar todo para después.
     * EJEMPLO: Ver Netflix en vez de estudiar para el examen
     */
    object Procrastination : ObstacleType(
        emoji = "\uD83D\uDCFA",
        name = "Procrastinación",
        description = "Dejar todo para después te hace perder oportunidades"
    )

    /**
     * Representa la falta de sueño por malos hábitos.
     * EJEMPLO: Jugar videojuegos hasta altas horas de la madrugada
     */
    object SleepDeprivation : ObstacleType(
        emoji = "\uD83D\uDCA4",
        name = "Falta de Sueño",
        description = "Dormir poco afecta tu memoria y concentración"
    )

    /**
     * Representa el uso excesivo de redes sociales.
     * EJEMPLO: Pasar horas en TikTok en vez de hacer tareas
     */
    object SocialMedia : ObstacleType(
        emoji = "\uD83D\uDCF1",
        name = "Redes Sociales",
        description = "El uso excesivo te distrae de tus metas académicas"
    )

    /**
     * Representa el estrés académico mal manejado.
     * EJEMPLO: Preocuparse tanto que no se puede estudiar efectivamente
     */
    object Stress : ObstacleType(
        emoji = "\uD83E\uDD2F",
        name = "Estrés Académico",
        description = "El estrés excesivo bloquea tu capacidad de aprendizaje"
    )

    companion object {
        /**
         * Función que devuelve un obstáculo aleatorio.
         *
         * ANALOGÍA: Es como girar una ruleta de tentaciones.
         * A veces te toca uno, a veces otro.
         *
         * @return Un tipo de obstáculo seleccionado aleatoriamente
         */
        fun random(): ObstacleType {
            return when ((0..6).random()) {
                0 -> JunkFood
                1 -> Alcohol
                2 -> Drugs
                3 -> Procrastination
                4 -> SleepDeprivation
                5 -> SocialMedia
                else -> Stress
            }
        }

        /**
         * Función que devuelve todos los tipos de obstáculos disponibles.
         * Útil para mostrar información o estadísticas.
         *
         * @return Lista con todos los tipos de obstáculos
         */
        fun getAllTypes(): List<ObstacleType> {
            return listOf(
                JunkFood,
                Alcohol,
                Drugs,
                Procrastination,
                SleepDeprivation,
                SocialMedia,
                Stress
            )
        }
    }
}