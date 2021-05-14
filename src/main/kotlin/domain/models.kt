package domain

import kotlin.math.roundToInt
import kotlin.random.Random
import kotlin.random.nextInt

object Window {
    val DEBUG = true
    val WIDTH = if (DEBUG) 400 else 1200
    val HEIGHT = if (DEBUG) 400 else 800
}

data class Star(
    var x: Float = Random.nextInt(-Window.WIDTH / 2..Window.WIDTH / 2).toFloat(),
    var y: Float = Random.nextInt(-Window.HEIGHT / 2..Window.HEIGHT / 2).toFloat(),
    var z: Float = Window.WIDTH.toFloat(),
) {
    fun update(){
        x -= 1
        y -= 1
    }
}