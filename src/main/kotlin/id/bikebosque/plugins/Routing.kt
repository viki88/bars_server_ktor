package id.bikebosque.plugins

import id.bikebosque.routes.adminRoute
import id.bikebosque.routes.userRoute
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        userRoute()
        adminRoute()
        get("/") {
            call.respondText { "Hello world" }
        }

    }
}

