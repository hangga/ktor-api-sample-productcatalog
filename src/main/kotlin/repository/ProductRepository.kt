package io.demo.productcatalog.repository

import io.demo.productcatalog.model.Product

object ProductRepository {
    private val products = mutableListOf<Product>()

    init {
        // Sample data
        products.add(Product(name = "Laptop", price = 1200.0, stock = 20))
        products.add(Product(name = "Smartphone", price = 800.0, stock = 10))
        products.add(Product(name = "Headphones", price = 150.0, stock = 30))
    }

    fun all(): List<Product> = products
    fun find(id: String): Product? = products.find { it.id == id }
    fun add(product: Product): Product {
        products.add(product)
        return product
    }
    fun update(id: String, product: Product): Product? {
        val index = products.indexOfFirst { it.id == id }
        return if (index != -1) {
            val updated = product.copy(id = id)
            products[index] = updated
            updated
        } else null
    }
    fun delete(id: String): Boolean = products.removeIf { it.id == id }
}