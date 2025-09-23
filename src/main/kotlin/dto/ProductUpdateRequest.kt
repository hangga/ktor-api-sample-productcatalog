package io.demo.productcatalog.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProductUpdateRequest(
    val name: String? = null,
    val price: Double? = null,
    val stock: Int? = null
)
