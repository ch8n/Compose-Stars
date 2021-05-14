package components

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import domain.Star

@Composable
fun drawStar(star: Star, drawScope: DrawScope) {
    drawScope.drawCircle(
        center = Offset(star.x, star.y),
        radius = 8f,
        color = Color.White
    )
}