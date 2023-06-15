package id.bikebosque

import id.bikebosque.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.ktorm.database.Database

fun main() {
    embeddedServer(Netty, port = 8080, host = "barsserver-development.up.railway.app", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
//    configureSecurity()
    configureRouting()
}

fun database() = Database.connect(
    "jdbc:mysql://localhost:3306/bars",
    user = "root",
    password = "P@ssw0rd",
    driver = "com.mysql.jdbc.Driver"
)
