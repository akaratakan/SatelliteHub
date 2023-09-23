package com.example.satellitehub.compose.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.model.SatelliteListItemObject
import com.example.model.generic.Magic
import com.example.satellitehub.R
import com.example.satellitehub.compose.common.ErrorPopUp
import com.example.satellitehub.compose.common.ProgressScreen
import com.example.satellitehub.style.AppTheme
import com.example.satellitehub.viewmodels.SatelliteListViewModel

@Composable
fun SatelliteListScreen(onItemClicked: (item: SatelliteListItemObject) -> Unit) {
    val viewModel: SatelliteListViewModel = hiltViewModel()
    val searchedText by viewModel.searchTextState.collectAsState()
    LaunchedEffect(searchedText) {
        viewModel.fetchList(searchedText)
    }
    val resultState = viewModel.listFlow.collectAsState(initial = Magic.loading())

    SatelliteListContainer(resultState = resultState.value,
        initText = searchedText,
        onSearch = {
            viewModel.setSearchState(it)
        },
        onItemClicked = { onItemClicked(it) }
    )
}

@Composable
fun SatelliteListContainer(
    resultState: Magic<List<SatelliteListItemObject>>,
    initText: String,
    onSearch: (name: String) -> Unit,
    onItemClicked: (id: SatelliteListItemObject) -> Unit
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            SearchField(initText = initText, onSearch = { onSearch(it) })
            when (resultState) {
                is Magic.Progress -> {
                    ProgressScreen()
                }

                is Magic.Success -> {
                    if (resultState.data.isNotEmpty()) {
                        SatelliteList(resultState.data.toList()) { onItemClicked(it) }
                    } else {
                        EmptyScreen()
                    }
                }

                is Magic.Failure -> {
                    ErrorPopUp(errorText = resultState.errorMessage)
                }

                else -> {}
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchField(initText: String = "", onSearch: (name: String) -> Unit) {
    var searchText by remember { mutableStateOf(initText) }
    SearchBar(
        query = searchText,
        onQueryChange = { newText ->
            searchText = newText
            onSearch(searchText)
        },
        onSearch = { query ->
            onSearch(query)
        },
        active = false,
        onActiveChange = {},
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        placeholder = {
            Text(text = "Search")
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = null,
                modifier = Modifier.clip(RoundedCornerShape(24.dp))
            )
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = null,
                modifier = Modifier
                    .clip(RoundedCornerShape(24.dp))
                    .clickable {
                        searchText = ""
                        onSearch("")
                    }
            )
        }, content = {

        }
    )
}

@Composable
fun SatelliteList(
    satellites: List<SatelliteListItemObject>,
    onItemClicked: (id: SatelliteListItemObject) -> Unit
) {
    LazyColumn {
        items(satellites) { satellite ->
            SatelliteItem(item = satellite) {
                onItemClicked(it)
            }
        }
    }
}

@Composable
fun EmptyScreen(
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = stringResource(id = R.string.empty_list_warn))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewListScreen() {
    AppTheme {
        SatelliteList(satellites = dummyList, onItemClicked = {})
    }
}

val dummyList = listOf(
    SatelliteListItemObject(
        active = false,
        id = 1,
        name = "Alpha-01"
    ), SatelliteListItemObject(
        active = true,
        id = 1,
        name = "Alpha-02",
    ), SatelliteListItemObject(
        active = false,
        id = 1,
        name = "Alpha-03",
    )
)