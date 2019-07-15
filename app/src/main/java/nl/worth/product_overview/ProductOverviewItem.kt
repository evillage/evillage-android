package nl.worth.product_overview

import androidx.annotation.DrawableRes

data class ProductOverviewItem(
    val name: String = "",
    @DrawableRes val thumbnailId: Int = 0,
    val price: String = "0"
)