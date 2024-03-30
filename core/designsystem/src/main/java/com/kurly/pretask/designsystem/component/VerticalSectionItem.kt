package com.kurly.pretask.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kurly.pretask.core.designsystem.R
import com.kurly.pretask.designsystem.theme.DiscountTextColor

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun VerticalSectionItem(
    modifier: Modifier = Modifier,
    name: String,
    image: String? = null,
    originalPrice: String,
    discountPrice: String,
    discountRate: String,
    isWish: Boolean,
    onWishChange: (Boolean) -> Unit = {}
) {
    val isWishRemember by remember(isWish) {
        mutableStateOf(isWish)
    }
    Box(
        modifier = modifier
            .aspectRatio(6 / 4f)
            .clip(RoundedCornerShape(8.dp))
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Gray),
            model = ImageRequest.Builder(LocalContext.current)
                .data(image)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .align(Alignment.BottomCenter)
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            Color.Black.copy(alpha = 0.2f),
                            Color.Black.copy(alpha = 0.6f)
                        )
                    )
                )
                .padding(vertical = 6.dp)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                text = name,
                style = TextStyle(fontSize = 20.sp),
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            if (discountRate.isNotEmpty() && discountPrice.isNotEmpty()) {
                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                ) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.Bottom)
                            .padding(end = 4.dp),
                        text = discountRate,
                        color = DiscountTextColor,
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    VerticalPriceText(
                        modifier = Modifier
                            .align(Alignment.Bottom)
                            .padding(end = 4.dp), discountPrice
                    )

                    Text(
                        modifier = Modifier.align(Alignment.Bottom),
                        text = originalPrice,
                        color = Color.White,
                        style = TextStyle(
                            fontSize = 16.sp,
                            textDecoration = TextDecoration.LineThrough
                        )
                    )
                }
            } else {
                VerticalPriceText(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    price = originalPrice
                )
            }
        }

        IconButton(
            modifier = Modifier.align(Alignment.TopEnd),

            onClick = {
                onWishChange.invoke(!isWishRemember)
            }) {
            Image(
                painter = painterResource(
                    id = if (isWishRemember) R.drawable.ic_btn_heart_on
                    else R.drawable.ic_btn_heart_off
                ),
                contentDescription = "wish"
            )
        }
    }
}

@Composable
internal fun VerticalPriceText(modifier: Modifier = Modifier, price: String) {
    Text(
        modifier = modifier,
        text = price,
        color = Color.White,
        style = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    )
}


@Preview(showBackground = true)
@Composable
private fun VerticalSectionItemPreview() {
    var isWish by remember {
        mutableStateOf(false)
    }
    Column {
        VerticalSectionItem(
            name = "[샐러딩] 레디믹스 스탠다드 150g",
            image = "",
            originalPrice = "2000원",
            discountPrice = "1000원",
            discountRate = "50%",
            isWish = isWish,
            onWishChange = {
                isWish = it
            }
        )
        Spacer(modifier = Modifier.height(10.dp))
        VerticalSectionItem(
            name = "[샐러딩] 레디믹스 스탠다드 150g",
            image = "",
            originalPrice = "2000원",
            discountPrice = "",
            discountRate = "",
            isWish = isWish,
            onWishChange = {
                isWish = it
            }
        )
    }
}