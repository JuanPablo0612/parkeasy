package com.parkeasy.android.ui.parkinglotslist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.parkeasy.android.R
import com.parkeasy.android.domain.model.parkinglots.ParkingLot
import com.parkeasy.android.domain.model.parkingspaces.ParkingSpace

@Composable
fun ParkingLotCard(parkingLot: ParkingLot, parkingSpaces: List<ParkingSpace>, onClick: () -> Unit) {
    OutlinedCard(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(text = parkingLot.name, fontSize = 24.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = stringResource(
                    id = R.string.parking_lots_parking_spaces,
                    parkingSpaces.count()
                ),
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = onClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            ) {
                Text(text = stringResource(id = R.string.parking_lots_reserve_button))
            }
        }
    }
}