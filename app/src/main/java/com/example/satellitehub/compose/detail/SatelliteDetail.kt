package com.example.satellitehub.compose.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.model.Position
import com.example.model.SatelliteDetailItemObject
import com.example.model.SatelliteListItemObject
import com.example.model.generic.Magic
import com.example.satellitehub.R
import com.example.satellitehub.compose.common.ErrorPopUp
import com.example.satellitehub.compose.common.ProgressScreen
import com.example.satellitehub.style.AppTheme
import com.example.satellitehub.viewmodels.SatelliteDetailViewModel


@Composable
fun DetailScreen(clickedItem: SatelliteListItemObject) {
    val viewModel: SatelliteDetailViewModel = hiltViewModel()
    LaunchedEffect(Unit) {
        viewModel.fetchDetail(clickedItem.id)
        viewModel.fetchPositions(clickedItem.id)
    }
    val detailState = viewModel.detailFlow.collectAsState(initial = Magic.loading())
    val positionState = viewModel.positionFlow.collectAsState(initial = Magic.loading())
    DetailComposer(
        resultState = detailState.value,
        positionState = positionState.value,
        satelliteName = clickedItem.name
    )
}


@Composable
fun DetailComposer(
    resultState: Magic<SatelliteDetailItemObject>,
    positionState: Magic<Position>?,
    satelliteName: String
) {
    when (resultState) {
        is Magic.Progress -> {
            ProgressScreen()
        }

        is Magic.Success -> {
            DetailContainer(
                detail = resultState.data,
                positionState = positionState,
                satelliteName = satelliteName

            )
        }

        is Magic.Failure -> {
            ErrorPopUp(errorText = resultState.errorMessage)
        }
    }
}

@Composable
fun DetailContainer(
    detail: SatelliteDetailItemObject?,
    positionState: Magic<Position>?,
    satelliteName: String
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SatelliteTitle(title = satelliteName)
                Date(date = detail?.firstFlight ?: "")
                Spacer(modifier = Modifier.size(24.dp))
                HeightAndMass(height = detail?.height ?: 0, weight = detail?.mass ?: 0)
                CostText(cost = detail?.costPerLaunch ?: 0)
                PositionText(positionState)
            }
        }
    }
}

@Composable
fun SatelliteTitle(title: String) {
    Text(
        modifier = Modifier.padding(4.dp),
        text = title,
        style = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
    )
}

@Composable
fun Date(date: String = "") {
    Text(
        text = date,
        style = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color.Gray
        )
    )
}

@Composable
fun HeightAndMass(height: Int, weight: Int) {
    val title = stringResource(id = R.string.height_and_mass)
    val description = "$height/$weight"
    TitleAndDescription(title = title, description = description)
}

@Composable
fun CostText(cost: Int) {
    val title = stringResource(id = R.string.cost)
    TitleAndDescription(title = title, description = cost.toString())
}


@Composable
fun PositionText(positionState: Magic<Position>?) {
    when (positionState) {
        is Magic.Progress -> {
            val title = stringResource(id = R.string.positions)
            val desc = stringResource(id = R.string.fetching)
            TitleAndDescription(title = title, description = desc)
        }

        is Magic.Success -> {
            val title = stringResource(id = R.string.positions)
            val description = "${positionState.data.posX}/${positionState.data.posY}"
            TitleAndDescription(title = title, description = description)
        }

        is Magic.Failure -> {
            ErrorPopUp(errorText = positionState.errorMessage)
            val title = stringResource(id = R.string.positions)
            val desc = stringResource(id = R.string.position_error)
            TitleAndDescription(title = title, description = desc)
        }

        else -> {}
    }

}

@Composable
fun TitleAndDescription(title: String, description: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "${title}:  ",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        )
        Text(
            text = description,
            style = TextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            )
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewDetailScreen(title: String = "Alpha-1") {
    AppTheme {
        DetailContainer(
            detail = dummyDetail,
            positionState = Magic.success(dummyPosition),
            satelliteName = title
        )
    }
}

val dummyDetail = SatelliteDetailItemObject(
    costPerLaunch = 1800000,
    firstFlight = "17.09.2023",
    height = 150,
    id = 1,
    mass = 23200000
)
val dummyPosition = Position(
    posX = 0.12312451,
    posY = 1.732411
)