package com.klyschenko.news.presentation.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

object CustomIcons {

    val openInNew: ImageVector
        get() {
            if (_openInNew != null) return _openInNew!!

            _openInNew = ImageVector.Builder(
                name = "Open_in_new",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 960f,
                viewportHeight = 960f
            ).apply {
                path(
                    fill = SolidColor(Color(0xFF000000))
                ) {
                    moveTo(200f, 840f)
                    quadToRelative(-33f, 0f, -56.5f, -23.5f)
                    reflectiveQuadTo(120f, 760f)
                    verticalLineToRelative(-560f)
                    quadToRelative(0f, -33f, 23.5f, -56.5f)
                    reflectiveQuadTo(200f, 120f)
                    horizontalLineToRelative(280f)
                    verticalLineToRelative(80f)
                    horizontalLineTo(200f)
                    verticalLineToRelative(560f)
                    horizontalLineToRelative(560f)
                    verticalLineToRelative(-280f)
                    horizontalLineToRelative(80f)
                    verticalLineToRelative(280f)
                    quadToRelative(0f, 33f, -23.5f, 56.5f)
                    reflectiveQuadTo(760f, 840f)
                    close()
                    moveToRelative(188f, -212f)
                    lineToRelative(-56f, -56f)
                    lineToRelative(372f, -372f)
                    horizontalLineTo(560f)
                    verticalLineToRelative(-80f)
                    horizontalLineToRelative(280f)
                    verticalLineToRelative(280f)
                    horizontalLineToRelative(-80f)
                    verticalLineToRelative(-144f)
                    close()
                }
            }.build()

            return _openInNew!!
        }

    private var _openInNew: ImageVector? = null

    val materialIconsArrowBack: ImageVector
        get() {
            if (_materialIconsArrowBack != null) return _materialIconsArrowBack!!

            _materialIconsArrowBack = ImageVector.Builder(
                name = "arrow_back",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 24f,
                viewportHeight = 24f
            ).apply {
                path(
                    fill = SolidColor(Color.Transparent)
                ) {
                    moveTo(0f, 0f)
                    horizontalLineToRelative(24f)
                    verticalLineToRelative(24f)
                    horizontalLineTo(0f)
                    close()
                }
                path(
                    fill = SolidColor(Color.Black)
                ) {
                    moveTo(20f, 11f)
                    horizontalLineTo(7.83f)
                    lineToRelative(5.59f, -5.59f)
                    lineTo(12f, 4f)
                    lineToRelative(-8f, 8f)
                    lineToRelative(8f, 8f)
                    lineToRelative(1.41f, -1.41f)
                    lineTo(7.83f, 13f)
                    horizontalLineTo(20f)
                    verticalLineToRelative(-2f)
                    close()
                }
            }.build()

            return _materialIconsArrowBack!!
        }

    private var _materialIconsArrowBack: ImageVector? = null
}