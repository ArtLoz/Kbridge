package com.l2bot.bridge.api

import com.l2bot.bridge.models.gps.GpsPoint

interface L2Gps {

    /**
     * Load GPS map from SQLite .db3 file.
     * Загрузить GPS карту из SQLite .db3 файла.
     *
     * @param path Path to .db3 file / Путь к .db3 файлу
     * @return Number of loaded points / Количество загруженных точек
     */
    fun loadBase(path: String): Int

    /**
     * Compute path between two coordinates using A*.
     * Рассчитать маршрут между двумя координатами через A*.
     */
    fun getPath(
        fromX: Double, fromY: Double, fromZ: Double,
        toX: Double, toY: Double, toZ: Double
    ): List<GpsPoint>

    /**
     * Compute path from coordinates to named point.
     * Рассчитать маршрут от координат до именованной точки.
     */
    fun getPathByName(
        fromX: Double, fromY: Double, fromZ: Double,
        spotName: String
    ): List<GpsPoint>

    /**
     * Get point by name.
     * Получить точку по имени.
     */
    fun getPointByName(name: String): GpsPoint?

    /**
     * Navigate to coordinates (pathfinding + movement).
     * Навигация к координатам (поиск пути + движение).
     */
    suspend fun navigateTo(x: Double, y: Double, z: Double): Boolean

    /**
     * Navigate to named point (pathfinding + movement).
     * Навигация к именованной точке (поиск пути + движение).
     */
    suspend fun navigateTo(spotName: String): Boolean
}
