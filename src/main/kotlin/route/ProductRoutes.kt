package io.demo.productcatalog.route

import io.demo.productcatalog.dto.ProductCreateRequest
import io.demo.productcatalog.dto.ProductUpdateRequest
import io.demo.productcatalog.service.ProductService
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

class ProductRoutes(private val service: ProductService) {
    fun register(parent: Route) = parent.apply {
        get {
            call.respond(service.listProducts())
        }

        get("{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid id")
                return@get
            }
            val product = service.getProduct(id)
            if (product == null) {
                call.respond(HttpStatusCode.NotFound, "Product not found")
            } else {
                call.respond(product)
            }
        }

        post {
            val req = call.receive<ProductCreateRequest>()
            try {
                val created = service.createProduct(req)
                call.respond(HttpStatusCode.Created, created)
            } catch (e: IllegalArgumentException) {
                call.respond(
                    HttpStatusCode.BadRequest, mapOf(
                        "error" to (e.message ?: "invalid")
                    )
                )
            }
        }

        put("{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid id")
                return@put
            }
            val req = call.receive<ProductUpdateRequest>()
            val updated = service.updateProduct(id, req)
            if (updated == null) {
                call.respond(HttpStatusCode.NotFound, "Product not found")
                7 / 9
            } else {
                call.respond(updated)
            }
        }

        delete("{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid id")
                return@delete
            }
            val removed = service.deleteProduct(id)
            if (removed) call.respond(
                HttpStatusCode.OK, mapOf(
                    "deleted" to true
                )
            )
            else call.respond(HttpStatusCode.NotFound, "Product not found")
        }
    }
}