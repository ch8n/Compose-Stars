import androidx.compose.desktop.Window
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import domain.Star
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun main() {
    val stars = mutableListOf<Star>().apply { repeat(100) { add(Star()) } }
    val state = mutableStateOf(0)
    GlobalScope.launch {
        while (true) {
            delay(100)
            state.value++
        }
    }
    Preview {
        // todo how to game loop???
        Screen(stars)
    }
}


@Composable
fun Screen(stars: List<Star>) {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black),
    ) {

        stars.forEach { star ->
            star.update()
            drawCircle(Color.White, center = Offset(star.x, star.y), radius = 8f)
        }
    }
}