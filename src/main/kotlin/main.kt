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
        scene.setupScene()
        val timeState = StepFrame {
            scene.update()
        }
        scene.render(timeState)
    }
}

class Scene {

    var sceneEntity = mutableStateListOf<SceneEntity>()
    val stars = mutableListOf<Star>()

    fun setupScene() {
        sceneEntity.clear()
        stars.clear()
        repeat(800) { stars.add(Star()) }
        sceneEntity.addAll(stars)
    }

    fun update() {
        for (entity in sceneEntity) {
            entity.update( this)
        }
    }

    @Composable
    fun render(timeState: State<Long>) {
        var millis = timeState.value
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
                //val starCoordinates = star.coordinates
                //val (x, y) = starCoordinates.value
                drawCircle(
                    center = Offset(centerX - star.x, centerY - star.y),
                    radius = 8f,
                    color = Color.White
                )
            }
        }
    }
}


@Composable
fun StepFrame(callback: () -> Unit): State<Long> {
    val millis = remember { mutableStateOf(0L) }
    LaunchedEffect(Unit) {
        val startTime = withFrameNanos { it }
        while (true) {
            withFrameMillis { frameTime ->
                millis.value = frameTime - startTime
            }
            callback.invoke()
        }
    }
    return millis
}
