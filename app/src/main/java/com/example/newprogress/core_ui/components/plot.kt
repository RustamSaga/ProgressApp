package com.example.newprogress.core_ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.madrapps.plot.line.DataPoint
import com.madrapps.plot.line.LineGraph
import com.madrapps.plot.line.LinePlot

@Composable
fun Graph(lines: List<List<DataPoint>>) {
    LineGraph(
        plot = LinePlot(
            listOf(
                LinePlot.Line(
                    lines[0],
                    LinePlot.Connection(color = Color.Red),
                    LinePlot.Intersection(color = Color.Green),
                    LinePlot.Highlight(color = Color.Blue),
                )
            ),
            grid = LinePlot.Grid(Color.Yellow, steps = 4),
        ),
        modifier = Modifier.fillMaxWidth().height(200.dp),
        onSelection = { xLine, points ->
            // Do whatever you want here
        }
    )
}

fun listDataPoint(): List<DataPoint> {
    return listOf(
        DataPoint(0f, 0f),
        DataPoint(1f, 1f),
        DataPoint(2f, 5f),
        DataPoint(3f, 3f),
        DataPoint(4f, 6f),
        DataPoint(5f, 7f),
        DataPoint(6f, 9f),
        DataPoint(7f, 10f),
        DataPoint(8f, 4f),
        DataPoint(9f, 11f)
    )
}