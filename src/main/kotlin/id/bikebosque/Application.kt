package id.bikebosque

import id.bikebosque.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.ktorm.database.Database

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
//    configureSecurity()
    configureRouting()
}

fun database() = Database.connect(
    "jdbc:mysql://containers-us-west-38.railway.app:6055/railway",
    user = "root",
    password = "bi1UAptl5eQzrQgUcC4D",
    driver = "com.mysql.jdbc.Driver"
)
