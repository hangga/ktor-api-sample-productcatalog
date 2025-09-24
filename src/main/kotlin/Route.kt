package io.demo.productcatalog

import io.demo.productcatalog.model.Product
import io.demo.productcatalog.repository.ProductRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respond(mapOf("message" to "Product Catalog API"))
        }

        // Health check
        get("/ping") {
            call.respond(mapOf("message" to "pong"))
        }

        // List all products
        get("/products") {
            call.respond(ProductRepository.all())
        }

        // Get product by ID
        get("/products/{id}") {
            val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest)
            val product = ProductRepository.find(id)
            if (product == null) {
                call.respond(HttpStatusCode.NotFound)
            } else {
                call.respond(product)
            }
        }

        // Add new product
        post("/products") {
            val product = call.receive<Product>()
            val saved = ProductRepository.add(product)
            call.respond(HttpStatusCode.Created, saved)
        }

        // Update product
        put("/products/{id}") {
            val id = call.parameters["id"] ?: return@put call.respond(HttpStatusCode.BadRequest)
            val product = call.receive<Product>()
            val updated = ProductRepository.update(id, product)
            if (updated == null) {
                call.respond(HttpStatusCode.NotFound)
            } else {
                call.respond(updated)
            }
        }

        // Delete product
        delete("/products/{id}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
            val deleted = ProductRepository.delete(id)
            if (deleted) {
                call.respond(HttpStatusCode.NoContent)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }
    }
}