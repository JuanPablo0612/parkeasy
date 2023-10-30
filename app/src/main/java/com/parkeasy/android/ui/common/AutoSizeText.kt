package com.parkeasy.android.ui.common

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

/**
 * Composable function that displays a text with auto-sizing capability.
 *
 * @param text The text to be displayed.
 * @param modifier The modifier to be applied to the text.
 * @param maxLines The maximum number of lines to be displayed.
 * @param style The style to be applied to the text.
 * @param minFontSize The minimum font size allowed for the text.
 * @param maxFontSize The maximum font size allowed for the text.
 */
@Composable
fun AutoSizeText(
    text: String,
    modifier: Modifier = Modifier,
    maxLines: Int = 1,
    style: TextStyle = LocalTextStyle.current,
    minFontSize: TextUnit = 12.sp,
    maxFontSize: TextUnit = 24.sp
) {
    var fontSize by remember { mutableStateOf(maxFontSize) }
    var readyToDraw by remember { mutableStateOf(false) }

    Text(
        text = text,
        modifier = modifier.drawWithContent {
            if (readyToDraw) drawContent()
        },
        style = style.copy(fontSize = fontSize),
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        onTextLayout = { textLayoutResult ->
            if (textLayoutResult.isLineEllipsized(0)) {
                val newFontSize = fontSize * 0.9
                if (newFontSize >= minFontSize) {
                    fontSize = newFontSize
                } else {
                    readyToDraw = true
                }
            } else {
                readyToDraw = true
            }
        }
    )
}