package com.parkeasy.android.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.parkeasy.android.R

@Composable
fun UserReservationsCountCard(count: Int) {
    OutlinedCard {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(10.dp)
        ) {
            Text(text = count.toString(), fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Text(text = stringResource(id = R.string.home_user_reservations), fontSize = 16.sp)
        }
    }
}