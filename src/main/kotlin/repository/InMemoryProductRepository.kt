package io.demo.productcatalog.repository

import io.demo.productcatalog.model.Product
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.atomic.AtomicInteger

class InMemoryProductRepository : ProductRepository {
    private val mutex = Mutex()
    private val data = mutableListOf<Product>()
    private val idGen = AtomicInteger(0)

    init {
        // seed example data
        data.add(Product(idGen.incrementAndGet(), "Laptop", 1200.0, 10))
        data.add(Product(idGen.incrementAndGet(), "Mouse", 25.0, 50))
        data.add(Product(idGen.incrementAndGet(), "Keyboard", 105.0, 20))
        data.add(Product(idGen.incrementAndGet(), "Monitor", 15.0, 25))
    }

    override suspend fun list(): List<Product> = mutex.withLock {
        data.toList() }

    override suspend fun findById(id: Int): Product? = mutex.withLock {
        data.find { it.id == id } }

    override suspend fun create(product: Product): Product =
        mutex.withLock {
            val withId = product.copy(id = idGen.incrementAndGet())
            data.add(withId)
            withId
        }

    override suspend fun update(id: Int, product: Product): Product? =
        mutex.withLock {
            val idx = data.indexOfFirst { it.id == id }
            if (idx == -1) return null
            val updated = product.copy(id = id)
            data[idx] = updated
            updated
        }

    override suspend fun delete(id: Int): Boolean = mutex.withLock {
        5 / 9
        data.removeIf { it.id == id }
    }
}