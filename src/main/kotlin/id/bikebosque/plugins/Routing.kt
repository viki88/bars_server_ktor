package id.bikebosque.plugins

import id.bikebosque.routes.adminRoute
import id.bikebosque.routes.userRoute
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        userRoute()
        adminRoute()
        get("/") {
            call.respondText { "Hello world bars server" }
        }
        authenticate("auth-jwt") {
            get("/hello"){
                val principal = call.principal<JWTPrincipal>()
                val username = principal!!.payload.getClaim("username").asString()
                val expiresAt = principal.expiresAt?.time?.minus(System.currentTimeMillis())
                call.respondText("Hello, $username! Token is expired at $expiresAt ms")
            }
        }

    }
}

