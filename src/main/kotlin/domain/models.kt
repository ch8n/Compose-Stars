package domain

import Scene
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.flow.MutableStateFlow

object Window {
    val DEBUG = true
    val WIDTH = if (DEBUG) 800 else 1200
    val HEIGHT = if (DEBUG) 800 else 800
}

fun randomX() = (-Window.WIDTH..Window.WIDTH).random().toFloat()
fun randomY() = (-Window.WIDTH..Window.WIDTH).random().toFloat()
fun randomZ() = (0..Window.WIDTH).random().toFloat()


sealed class SceneEntity {
    abstract fun update(floatDelta: Float, scene: Scene)
}

data class Star(
    private var z: Float = randomZ(),
    val coordinates: MutableStateFlow<Pair<Float, Float>> = MutableStateFlow(Pair(randomX(), randomY()))
) : SceneEntity() {

    override fun update(floatDelta: Float, scene: Scene) {
        val (x, y) = coordinates.value
        z = if (z < 1) randomZ() else z - 1
        val transX = if (z < 1) randomX() else (x / z).map(0f to 1f, 0f to Window.WIDTH.toFloat())
        val transY = if (z < 1) randomY() else (y / z).map(0f to 1f, 0f to Window.HEIGHT.toFloat())
        coordinates.value = coordinates.value.copy(transX, transY)
    }
}

fun Float.map(fromRange: Pair<Float, Float>, toRange: Pair<Float, Float>): Float {
    val (minRange, maxRange) = fromRange
    val (minMappedRange, maxMappedRange) = toRange
    val rangePercentage = (this / minRange + maxRange) * 100
    val mappedValue = (rangePercentage / 100) * (minMappedRange + maxMappedRange)
    return mappedValue
}