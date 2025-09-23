package io.demo.productcatalog

import io.demo.productcatalog.repository.InMemoryProductRepository
import io.demo.productcatalog.route.ProductRoutes
import io.demo.productcatalog.service.ProductService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    val repo = InMemoryProductRepository()
    val service = ProductService(repo)
    routing {
        get("/") {
            call.respondText(
                "Product Catalog API", ContentType.Text.Plain
            )
        }

        get("/ping") {
            call.respond(mapOf("message" to "pong"))
        }

        route("/health") {
            get {
                call.respond(mapOf("status" to "ok"))
            }
        }

        route("/products") {
            ProductRoutes(service).register(this)
        }
    }
}
