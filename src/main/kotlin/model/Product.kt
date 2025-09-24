package io.demo.productcatalog.model

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Product(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val price: Double,
    val stock: Int
)