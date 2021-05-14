package domain

import Scene
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import kotlin.random.Random
import kotlin.random.nextInt

object Window {
    val DEBUG = true
    val WIDTH = if (DEBUG) 800 else 1200
    val HEIGHT = if (DEBUG) 800 else 800
}

sealed class SceneEntity {
    abstract fun update(floatDelta: Float, scene: Scene)
}

data class Star(
    var x: Float = Random.nextInt(0..Window.WIDTH).toFloat(),
    var y: Float = Random.nextInt(0..Window.HEIGHT).toFloat(),
    var z: Float = Window.WIDTH.toFloat(),
    val position: MutableState<Pair<Float, Float>> = mutableStateOf(x to y)
) : SceneEntity() {
    override fun update(floatDelta: Float, scene: Scene) {
        println(floatDelta)
        val transX = x + 1
        val transY = y + 1
        position.value = transX to transY
    }
}
