package io.demo.productcatalog.service

import io.demo.productcatalog.dto.ProductCreateRequest
import io.demo.productcatalog.dto.ProductUpdateRequest
import io.demo.productcatalog.model.Product
import io.demo.productcatalog.repository.ProductRepository

class ProductService(private val repo: ProductRepository) {

    suspend fun listProducts(): List<Product> = repo.list()

    suspend fun getProduct(id: Int): Product? = repo.findById(id)

    suspend fun createProduct(req: ProductCreateRequest): Product {
        require(req.name.isNotBlank()) { "name must not be blank" }
        require(req.price >= 0) { "price must be >= 0" }
        require(req.stock >= 0) { "stock must be >= 0" }

        val product = Product(
            id = 0, // repository will assign real id
            name = req.name.trim(),
            price = req.price,
            stock = req.stock
        )
        return repo.create(product)
    }

    suspend fun updateProduct(id: Int, req: ProductUpdateRequest):
      Product? {
        val existing = repo.findById(id) ?: return null
        val updated = existing.copy(
            name = req.name ?: existing.name,
            price = req.price ?: existing.price,
            stock = req.stock ?: existing.stock
        )
        return repo.update(id, updated)
    }

    suspend fun deleteProduct(id: Int): Boolean = repo.delete(id)
}