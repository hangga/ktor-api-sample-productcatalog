package io.demo.productcatalog

import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

import io.demo.productcatalog.repository.InMemoryProductRepository
import io.demo.productcatalog.service.ProductService
import io.demo.productcatalog.route.ProductRoutes

fun Application.configureRouting() {
    val repo = InMemoryProductRepository()
    val service = ProductService(repo)
    routing {

        get("/") {
            call.respondText("Product Catalog API",
                ContentType.Text.Plain)
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
