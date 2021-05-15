import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import domain.SceneEntity
import domain.Star

fun main() {
    Preview {
        val scene = remember { Scene() }
        scene.startScene()
        LaunchedEffect(Unit) {
            withFrameNanos {
                scene.update(it)
            }
        }
        scene.render()
    }
}

enum class SceneState {
    STOPPED, RUNNING
}

class Scene {

    var prevTime = 0L
    var sceneEntity = mutableStateListOf<SceneEntity>()
    var sceneState by mutableStateOf(SceneState.RUNNING)
    val stars = mutableListOf<Star>()

    fun startScene() {
        sceneEntity.clear()
        repeat(100) { stars.add(Star()) }
        sceneEntity.addAll(stars)
        sceneState = SceneState.RUNNING
    }

    fun update(time: Long) {
        val delta = time - prevTime
        val floatDelta = (delta / 1E8).toFloat()
        prevTime = time

        if (sceneState == SceneState.STOPPED) return

        for (entity in sceneEntity) {
            entity.update(floatDelta, this)
        }
    }

    @Composable
    fun render() {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Black),
        ) {

            val canvasWidth = size.width
            val canvasHeight = size.height
            val centerX = canvasWidth / 2
            val centerY = canvasHeight / 2

            for (star in stars) {
                val (starX,starY) = star.coordinates.value
                drawCircle(
                    center = Offset(centerX - starX, centerY - starY),
                    radius = 8f,
                    color = Color.White
                )
            }

        }
    }
}

fun map(value: Float, range: Pair<Float, Float>, targetRange: Pair<Float, Float>): Float {
    val (minRange, maxRange) = range
    val (minMappedRange, maxMappedRange) = targetRange
    val rangePercentage = (value / minRange + maxRange) * 100
    val mappedValue = (rangePercentage / 100) * (minMappedRange + maxMappedRange)
    return mappedValue
}
