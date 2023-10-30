package com.parkeasy.android.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.parkeasy.android.R
import com.parkeasy.android.ui.common.AutoSizeText

/**
 * Composable function that displays the top app bar for the home screen.
 *
 * @param firstName The first name of the user.
 * @param lastName The last name of the user.
 * @param email The email of the user.
 * @param scrollBehavior The scroll behavior of the top app bar.
 * @param onSettingsClick The callback function for when the settings icon is clicked.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar(
    firstName: String,
    lastName: String,
    email: String,
    scrollBehavior: TopAppBarScrollBehavior,
    onSettingsClick: () -> Unit
) {
    LargeTopAppBar(
        title = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .height(250.dp)
                        .padding(8.dp)
                        .align(Alignment.Center)
                        .alpha(1f - scrollBehavior.state.collapsedFraction)
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.size(100.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    AutoSizeText(
                        text = "$firstName $lastName",
                        style = MaterialTheme.typography.titleLarge,
                        maxFontSize = MaterialTheme.typography.titleLarge.fontSize
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    AutoSizeText(
                        text = email,
                        style = MaterialTheme.typography.titleSmall,
                        maxFontSize = MaterialTheme.typography.titleSmall.fontSize
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .height(if (scrollBehavior.state.collapsedFraction > 0.5f) 50.dp else 0.dp)
                        .align(Alignment.CenterStart)
                        .padding(start = 16.dp)
                        .alpha(scrollBehavior.state.collapsedFraction)
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.fillMaxHeight(0.8f)
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    AutoSizeText(
                        text = stringResource(
                            id = R.string.parking_lots_map_title,
                            firstName
                        ),
                        style = MaterialTheme.typography.titleLarge,
                        maxFontSize = MaterialTheme.typography.titleLarge.fontSize
                    )
                }
            }
        },
        actions = {
            IconButton(onClick = onSettingsClick) {
                Icon(imageVector = Icons.Default.Settings, contentDescription = null)
            }
        },
        scrollBehavior = scrollBehavior,
        modifier = Modifier.heightIn(50.dp, 300.dp)
    )
}