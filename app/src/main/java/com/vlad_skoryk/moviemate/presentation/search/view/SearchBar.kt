package com.vlad_skoryk.moviemate.presentation.search.view

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.delete
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import com.vlad_skoryk.moviemate.R
import com.vlad_skoryk.moviemate.data.remote.Movie
import com.vlad_skoryk.moviemate.presentation.search.components.MovieList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSearchBar(
    textFieldState: TextFieldState,
    onSearch: (String) -> Unit,
    searchResults: List<Movie>,
    onResultClick: (Movie) -> Unit,
    modifier: Modifier = Modifier,
) {
    val backgroundColor = MaterialTheme.colorScheme.background
    val placeholderColor = MaterialTheme.colorScheme.primary
    val textColor = MaterialTheme.colorScheme.primary

    var query by rememberSaveable { mutableStateOf(textFieldState.text.toString()) }
    var active by rememberSaveable { mutableStateOf(false) }

    SearchBar(
        query = query,
        onQueryChange = {
            query = it
            textFieldState.edit { replace(0, length, it) }
            onSearch(it)
        },
        onSearch = {
            onSearch(query)
            active = false
        },
        active = active,
        onActiveChange = { active = it },
        placeholder = {
            Text("Search", color = placeholderColor)
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",
                tint = placeholderColor
            )
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = {
                    query = ""
                    textFieldState.edit { delete(0, length) }
                }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Clear Search",
                        tint = placeholderColor
                    )
                }
            }
        },
        colors = SearchBarDefaults.colors(
            containerColor = backgroundColor,
            dividerColor = placeholderColor,
        ),
        modifier = modifier
            .semantics { isTraversalGroup = true }
    ) {
        MovieList(movies = searchResults, onResultClick = onResultClick)
    }
}