package domain

import Scene

object Window {
    val DEBUG = true
    val WIDTH = if (DEBUG) 800 else 1200
    val HEIGHT = if (DEBUG) 800 else 800
}

fun randomX() = (-Window.WIDTH..Window.WIDTH).random().toFloat()
fun randomY() = (-Window.WIDTH..Window.WIDTH).random().toFloat()
fun randomZ() = (0..Window.WIDTH).random().toFloat()
fun expandingRadius(z: Float) = z.mapRange(0f to Window.WIDTH.toFloat(), 8f to 0f)



sealed class SceneEntity {
    abstract fun update(scene: Scene)
}

data class Star(
    private var z: Float = randomZ(),
    private var x: Float = randomX(),
    private var y: Float = randomY(),
    var radius: Float = expandingRadius(z),
    var previousCoordinates: Triple<Float, Float, Float> = Triple(x, y, z),
    var currentCoordinates: Triple<Float, Float, Float> = Triple(x, y, z)
) : SceneEntity() {

    companion object {
        var calls = 0
    }

    override fun update(scene: Scene) {

        // coordinates for tailing line
        previousCoordinates = previousCoordinates.copy(x, y, z)

        // current coordinates for head
        z = if (z < 1) Window.WIDTH.toFloat() else z - 1
        x = if (z < 1) randomX() else (x / z).mapRange(0f to 1f, 0f to Window.WIDTH.toFloat())
        y = if (z < 1) randomY() else (y / z).mapRange(0f to 1f, 0f to Window.HEIGHT.toFloat())
        currentCoordinates = currentCoordinates.copy(x, y, z)
        radius = expandingRadius(z)
    }
}

fun Float.mapRange(fromRange: Pair<Float, Float>, toRange: Pair<Float, Float>): Float {
    val (minRange, maxRange) = fromRange
    val (minMappedRange, maxMappedRange) = toRange
    val rangePercentage = (this / maxRange) * 100
    val mappedValue = (rangePercentage / 100) * (minMappedRange + maxMappedRange)
    return mappedValue
}