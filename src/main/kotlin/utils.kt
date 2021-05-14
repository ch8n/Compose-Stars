import androidx.compose.desktop.Window
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntSize

/**
 * To support instant preview (replacement for android's @Preview annotation)
 */
fun Preview(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Window(
        title = "Compose-Star-Debug",
        size = IntSize(domain.Window.WIDTH, domain.Window.HEIGHT),
        resizable = false,
        centered = true,
    ) {
        MaterialTheme {
            Row(
                modifier = modifier.fillMaxSize()
            ) {
                content()
            }
        }
    }
}
