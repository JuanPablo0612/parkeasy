package com.parkeasy.android.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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

/**
 * Composable function that displays a card showing the count of user reservations for parking lots.
 *
 * @param count The number of user reservations for parking lots.
 */
@Composable
fun UserReservationsParkingLotsCountCard(count: Int) {
    OutlinedCard(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(
                text = stringResource(id = R.string.home_user_reservations_parking_lots_first_text),
                fontSize = 16.sp
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = count.toString(), fontSize = 28.sp, fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.width(5.dp))

                Text(
                    text = stringResource(id = R.string.home_user_reservations_parking_lots_last_text),
                    fontSize = 16.sp
                )
            }
        }
    }
}