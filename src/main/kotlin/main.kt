import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import domain.SceneEntity
import domain.Star

fun main() {
    Preview {
        val scene = remember { Scene() }
        scene.setupScene()
        val frameState = StepFrame {
            scene.update()
        }
        scene.render(frameState)
    }
}

class Scene {

    var sceneEntity = mutableStateListOf<SceneEntity>()
    val stars = mutableListOf<Star>()

    fun setupScene() {
        sceneEntity.clear()
        stars.clear()
        repeat(800 * 1) { stars.add(Star()) }
        sceneEntity.addAll(stars)
    }

    fun update() {
        for (entity in sceneEntity) {
            entity.update(this)
        }
    }

    @Composable
    fun render(frameState: State<Long>) {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {


            Box {
                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.Black),
                ) {

                    val frameTime = frameState.value
                    val canvasWidth = size.width
                    val canvasHeight = size.height
                    val centerX = canvasWidth / 2
                    val centerY = canvasHeight / 2

                    for (star in stars) {
                        val (headX, headY) = star.currentCoordinates
                        val (tailX, tailY) = star.previousCoordinates

                        drawLine(
                            color = Color.White,
                            start = Offset(centerX - tailX, centerY - tailY),
                            end = Offset(centerX - headX, centerY - headY),
                            strokeWidth = star.radius,
                        )
                    }


                }

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Spacer(modifier = Modifier.height(100.dp))
                    Text(
                        text = "JetPack Compose",
                        style = MaterialTheme.typography.h1,
                        color = Color.White,
                        modifier = Modifier.offset(y = 70.dp)
                    )

                    Text(
                        text = "Star".toUpperCase(),
                        style = MaterialTheme.typography.h1,
                        color = Color.White,
                        fontSize = 150.sp,
                    )
                    Text(
                        text = "Wars".toUpperCase(),
                        style = MaterialTheme.typography.h1,
                        color = Color.White,
                        fontSize = 150.sp,
                        modifier = Modifier.offset(y = (-100).dp)
                    )
                    Text(
                        text = "Chetan Gupta",
                        style = MaterialTheme.typography.h1,
                        color = Color.White,
                        modifier = Modifier.offset(y = (-140).dp)
                    )
                }
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
