package com.kt.naviagent.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.IconToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.kt.naviagent.core.designsystem.R


@Composable
fun WishButton(
    modifier: Modifier = Modifier,
    onWishChange: (Boolean) -> Unit,
    isWish: () -> Boolean
) {
    IconToggleButton(
        modifier = modifier,
        checked = isWish(), onCheckedChange = {
        onWishChange.invoke(!isWish())
    }) {
        if (isWish()) {
            WishImage()
        } else {
            RemoveWishImage()
        }
    }
}


@Stable
@Composable
private fun WishImage() = Image(
    painter = painterResource(
        id = R.drawable.ic_btn_heart_on
    ),
    contentDescription = "wish"
)

@Stable
@Composable
private fun RemoveWishImage() = Image(
    painter = painterResource(
        R.drawable.ic_btn_heart_off
    ),
    contentDescription = "remove wish"
)

@Preview
@Composable
private fun WishButtonPreview() {
    Column {
        WishButton(onWishChange = {}, isWish = { true })
        WishButton(onWishChange = {}, isWish = { false })
    }
}