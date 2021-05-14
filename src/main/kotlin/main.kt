import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import components.drawStar
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

            stars.forEach { star ->
                val (x, y) = remember { star.position.value }
                drawCircle(
                    center = Offset(x, y),
                    radius = 8f,
                    color = Color.White
                )
            }

        }
    }
}
