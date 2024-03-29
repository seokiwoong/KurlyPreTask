package com.kurly.pretask.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.kurly.pretask.core.designsystem.R
import com.kurly.pretask.designsystem.theme.DiscountTextColor

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HorizontalSectionItem(
    modifier: Modifier = Modifier,
    name: String,
    image: String? = null,
    originalPrice: String,
    discountPrice: String?,
    discountRate: String?,
    isWish: Boolean,
    onWishChange: (Boolean) -> Unit = {}
) {
    Box(
        modifier = modifier
            .width(150.dp)
            .height(200.dp)
            .clip(RoundedCornerShape(8.dp))
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Gray),
            model = image,
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
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.4f)
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
                style = TextStyle(fontSize = 14.sp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            if (discountRate != null && discountPrice != null) {
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
                        style = TextStyle(fontSize = 14.sp)
                    )

                    PriceText(
                        modifier = Modifier
                            .align(Alignment.Bottom)
                            .padding(end = 4.dp), discountPrice
                    )

                    Text(
                        modifier = Modifier.align(Alignment.Bottom),
                        text = originalPrice,
                        style = TextStyle(
                            fontSize = 12.sp,
                            textDecoration = TextDecoration.LineThrough
                        )
                    )
                }
            } else {
                PriceText(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    price = originalPrice
                )
            }
        }

        IconButton(
            modifier = Modifier.align(Alignment.TopEnd),

            onClick = {
                onWishChange.invoke(!isWish)
            }) {
            Image(
                painter = painterResource(
                    id = if (isWish) R.drawable.ic_btn_heart_on
                    else R.drawable.ic_btn_heart_off
                ),
                contentDescription = "wish"
            )
        }
    }
}

@Composable
internal fun PriceText(modifier: Modifier = Modifier, price: String) {
    Text(
        modifier = modifier,
        text = price,
        style = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    )
}


@Preview
@Composable
private fun HorizontalItemPreview() {
    var isWish by remember {
        mutableStateOf(false)
    }
    Column {


        HorizontalSectionItem(
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
        HorizontalSectionItem(
            name = "[샐러딩] 레디믹스 스탠다드 150g",
            image = "",
            originalPrice = "2000원",
            discountPrice = null,
            discountRate = null,
            isWish = isWish,
            onWishChange = {
                isWish = it
            }
        )
    }
}