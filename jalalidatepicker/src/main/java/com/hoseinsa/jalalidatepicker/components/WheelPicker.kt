package com.hoseinsa.jalalidatepicker.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign

@Composable
internal fun WheelPicker(
    modifier: Modifier = Modifier,
    options: List<String>,
    initialValue: Int = 0,
    textStyle: TextStyle = MaterialTheme.typography.headlineMedium,
    color: Color = MaterialTheme.colorScheme.surfaceContainer,
    fontFamily: FontFamily = FontFamily.Default,
    onValueSelected: (index: Int, value: String) -> Unit,
) {

    var selectedIndex by remember { mutableIntStateOf(initialValue) }
    val denisty = LocalDensity.current
    val itemSize = with(denisty) { textStyle.fontSize.toDp() }
    val itemHeight = itemSize + itemSize / 3
    val lazyListState = rememberLazyListState()

    Box(
        modifier = modifier.height((itemSize * 4)),
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
                .background(color = color),
            state = lazyListState,
            verticalArrangement = Arrangement.Center,
        ) {
            item { FillerItem(modifier = Modifier.height(itemHeight)) }
            items(count = options.size, key = { index -> index }) { item ->
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .height(itemHeight)
                        .defaultMinSize(minWidth = itemSize)
                        .clickable { selectedIndex = item },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = options[item],
                        style = textStyle,
                        textAlign = TextAlign.Center,
                        fontFamily = fontFamily,
                        color = if (item == selectedIndex) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onBackground
                        }
                    )
                }
            }
            item { FillerItem(modifier = Modifier.height(itemHeight)) }
        }
        Canvas(
            modifier = Modifier
                .matchParentSize()
                .align(Alignment.TopStart)
        ) {
            drawRect(
                brush = Brush.verticalGradient(
                    .1f to color,
                    .5f to Color.Unspecified,
                    .9f to color,
                )
            )
        }
    }

    LaunchedEffect(selectedIndex) {
        lazyListState.animateScrollToItem(selectedIndex)
        onValueSelected(selectedIndex, options[selectedIndex])
    }

}

@Composable
private fun FillerItem(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {}
}