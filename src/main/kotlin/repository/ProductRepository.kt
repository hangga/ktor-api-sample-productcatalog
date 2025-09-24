package io.demo.productcatalog.repository

import io.demo.productcatalog.model.Product
import kotlinx.coroutines.delay

object ProductRepository {
    private val products = mutableListOf<Product>()

    init {
        // Sample data
        products.add(Product(name = "Laptop", price = 1200.0, stock = 20))
        products.add(Product(name = "Smartphone", price = 800.0, stock = 10))
        products.add(Product(name = "Headphones", price = 150.0, stock = 30))
    }

    suspend fun all(): List<Product> {
        delay(50) // simulating I/O async
        return products
    }

    suspend fun find(id: String): Product? {
        delay(50)
        return products.find { it.id == id }
    }

    suspend fun add(product: Product): Product {
        delay(50)
        products.add(product)
        return product
    }

    suspend fun update(id: String, product: Product): Product? {
        delay(50)
        val index = products.indexOfFirst { it.id == id }
        return if (index != -1) {
            val updated = product.copy(id = id)
            products[index] = updated
            updated
        } else null
    }

    suspend fun delete(id: String): Boolean {
        delay(50)
        return products.removeIf { it.id == id }
    }
}
