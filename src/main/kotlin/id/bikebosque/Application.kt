package id.bikebosque

import id.bikebosque.plugins.configureRouting
import id.bikebosque.utils.FirebaseAdmin
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.doublereceive.*
import org.ktorm.database.Database

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
//    configureSecurity()
    install(DoubleReceive)
    FirebaseAdmin.init()
    configureRouting()
}

fun database() = Database.connect(
    "jdbc:mysql:///bars?cloudSqlInstance=bars-web-service:us-central1:bars-database&socketFactory=com.google.cloud.sql.mysql.SocketFactory",
    user = "root",
    password = "P@ssword"
)

fun localDatabase() = Database.connect(
    "jdbc:mysql://localhost:3306/bars",
    user = "root",
    password = "P@ssw0rd",
    driver = "com.mysql.cj.jdbc.Driver"
)

fun connectDatabase(useLocal :Boolean = false) = if (useLocal) localDatabase() else database()
