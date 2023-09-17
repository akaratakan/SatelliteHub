package com.example.satellitehub.compose.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.SatelliteListItemObject
import com.example.satellitehub.R


@Composable
fun SatelliteItem(
    item: SatelliteListItemObject,
    onItemClicked: (id: SatelliteListItemObject) -> Unit
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .clickable(enabled = item.active) { onItemClicked(item) },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.wrapContentSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            IndicatorSignal(isActive = item.active)
            TitleAndSubtitle(item.name, item.active)
        }
        Separator()
    }

}

@Composable
fun IndicatorSignal(isActive: Boolean) {
    val color = if (isActive) Color.Green else Color.Red

    Box(modifier = Modifier.wrapContentSize().padding(12.dp)) {
        Canvas(
            modifier = Modifier
                .size(8.dp)
                .fillMaxSize()
        ) {
            drawIntoCanvas { canvas ->
                val radius = size.minDimension
                val paint = Paint().apply {
                    this.color = color
                    isAntiAlias = true
                }
                canvas.drawCircle(center = Offset(radius, radius), radius = radius, paint = paint)
            }
        }
    }

}

@Composable
fun TitleAndSubtitle(title: String, isActive: Boolean) {
    val color = if (isActive) Color.Black else Color.Gray
    val subtitle = if (isActive) stringResource(id = R.string.active)
    else stringResource(id = R.string.passive)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = title,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = color
            )
        )
        Text(
            text = subtitle,
            style = TextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = color
            ),
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
fun Separator() {
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .size(1.dp)
            .background(Color.Gray)
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewItem() {
    SatelliteItem(item = dummySatellite, onItemClicked = {})
}

val dummySatellite = SatelliteListItemObject(active = true, 1, name = "Alpha")