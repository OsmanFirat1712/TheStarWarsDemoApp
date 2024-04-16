package com.example.starwarsdemoapp.ui.theme.overview

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.starwarsdemoapp.R
import com.example.starwarsdemoapp.data.remote.model.Character
import com.example.starwarsdemoapp.ui.theme.base.ContentEmptyScreen
import com.example.starwarsdemoapp.ui.theme.base.ContentErrorScreen
import com.example.starwarsdemoapp.ui.theme.base.ContentLoadingScreen
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@RootNavGraph(start = true)
@Destination
@Composable
fun OverviewScreen(
    navigator: DestinationsNavigator,
    viewModel: OverviewViewModel = koinViewModel()
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val viewModelState by viewModel.state.collectAsState()

    Scaffold(
        modifier = Modifier.nestedScroll(connection = scrollBehavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(
                title = { Text(text = stringResource(id = R.string.title_overview_screen)) },
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    scrolledContainerColor = MaterialTheme.colorScheme.surface
                )
            )
        })

    {
        Column(
            modifier = Modifier.padding(it)
        ) {
            when (viewModelState.dataState) {
                DataState.ERROR -> ContentErrorScreen { viewModel.loadCharacters(0) }
                DataState.LOADING -> ContentLoadingScreen()
                DataState.SUCCESS -> CharacterList(items = viewModelState.characterList) { character: Character ->
                    //TODO Detailview pass Character data as argument
                }

                DataState.EMPTY -> ContentEmptyScreen(
                    R.string.empty_feed
                )

                DataState.NONE -> {}
            }
        }
    }
}

@Composable
fun CharacterList(
    items: List<Character>,
    onClick: (Character) -> Unit
) {
    val index = rememberLazyListState()


    LazyColumn(
        state = index,
        modifier = Modifier
            .padding(10.dp),
        contentPadding = PaddingValues(bottom = 20.dp)
    ) {

        items.forEach { character ->
            item {
                IndexState.index.value = !index.canScrollForward
                //USE TIMBER INSTEAD
                Log.d("OverviewScreen" , "can ${index.canScrollForward}")
                CharacterListItem(character = character)
                { character: Character ->
                    onClick(character)
                }
            }
        }
    }
}

@Composable
fun CharacterListItem(
    character: Character,
    onClick: (Character) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clickable {
                onClick(character)
            },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        shape = RoundedCornerShape(20.dp)
    )

    {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(10.dp)
        ) {
            Column {
                Text(
                    text = character.name,
                    modifier = Modifier
                        .padding(
                            5.dp
                        )
                )
                Text(
                    text = "Größe: ${character.height} cm",
                    modifier = Modifier
                        .padding(
                            5.dp
                        ),
                    fontSize = 12.sp
                )
            }
        }
    }
}



object IndexState{
    val index = mutableStateOf(false)

}