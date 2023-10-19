package com.parkeasy.android.ui.reservation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.parkeasy.android.R
import com.parkeasy.android.ui.common.AutoSizeText

@Composable
fun ConfirmReservationDialog(
    parkingLotName: String,
    parkingSpaceNumber: Int,
    startText: String,
    endText: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = stringResource(id = R.string.reservation_confirm_dialog_title))
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(state = rememberScrollState())
            ) {
                Text(text = stringResource(id = R.string.reservation_confirm_dialog_details))

                Text(text = stringResource(id = R.string.reservation_confirm_dialog_parking_lot))

                AutoSizeText(
                    text = parkingLotName,
                    style = TextStyle(fontWeight = FontWeight.Bold),
                    maxFontSize = 24.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(text = stringResource(id = R.string.reservation_parking_space_number))

                AutoSizeText(
                    text = parkingSpaceNumber.toString(),
                    style = TextStyle(fontWeight = FontWeight.Bold),
                    maxFontSize = 24.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(text = stringResource(R.string.reservation_start_date))

                AutoSizeText(
                    text = startText,
                    style = TextStyle(fontWeight = FontWeight.Bold),
                    maxFontSize = 24.sp
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(text = stringResource(R.string.reservation_end_date))

                AutoSizeText(
                    text = endText,
                    style = TextStyle(fontWeight = FontWeight.Bold),
                    maxFontSize = 24.sp
                )
            }
        },
        confirmButton = {
            Button(onClick = onConfirm) {
                Text(text = stringResource(id = R.string.reservation_confirm_dialog_reserve_button))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(id = R.string.common_cancel))
            }
        }
    )
}