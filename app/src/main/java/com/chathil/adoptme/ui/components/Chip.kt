package com.chathil.adoptme.ui.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.chathil.adoptme.R
import com.chathil.adoptme.ui.theme.AdoptmeTheme

@Composable
fun Chip(
    modifier: Modifier = Modifier,
    color: Color = AdoptmeTheme.colors.primary,
    start: @Composable (() -> Unit)? = null,
    content: @Composable () -> Unit,
    trailing: @Composable (() -> Unit)? = null
) {
    Row(
        modifier.height(32.dp).padding(end = 4.dp).wrapContentWidth()
            .wrapContentHeight(Alignment.CenterVertically)
            .border(2.dp, color, RoundedCornerShape(50)).padding(8.dp)
    ) {
        ProvideTextStyle(value = MaterialTheme.typography.caption) {
            Spacer(modifier = Modifier.preferredWidth(4.dp))
            start?.let {
                it()
                Spacer(modifier = Modifier.preferredWidth(8.dp))
            }
            content()
            trailing?.let {
                Spacer(modifier = Modifier.preferredWidth(8.dp))
                it()
            }
            Spacer(modifier = Modifier.preferredWidth(4.dp))
        }
    }
}


@Preview
@Composable
fun ChipPreview() {
    Chip(start = {
        Image(
            asset = imageResource(id = R.drawable.icn_otter),
            modifier = Modifier.size(16.dp).padding(start = 4.dp)
        )
    },
        content = { Text("Account", modifier = Modifier.padding(end = 4.dp)) }, trailing = {
            Image(
                asset = imageResource(id = R.drawable.icn_otter),
                modifier = Modifier.size(16.dp).padding(start = 4.dp)
            )
        })
}
