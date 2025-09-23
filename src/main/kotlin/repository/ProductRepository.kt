package io.demo.productcatalog.repository

import io.demo.productcatalog.model.Product

interface ProductRepository {
    suspend fun list(): List<Product>
    suspend fun findById(id: Int): Product?
    suspend fun create(product: Product): Product
    suspend fun update(id: Int, product: Product): Product?
    suspend fun delete(id: Int): Boolean
}