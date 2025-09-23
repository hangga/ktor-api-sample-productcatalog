package io.demo.productcatalog.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProductCreateRequest(
    val name: String,
    val price: Double,
    val stock: Int
)
