package id.bikebosque

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import id.bikebosque.plugins.configureRouting
import id.bikebosque.utils.FirebaseAdmin
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.doublereceive.*
import io.ktor.server.response.*
import org.ktorm.database.Database

fun main(args: Array<String>) = EngineMain.main(args)

fun Application.module(){
    val secret = environment.config.property("jwt.secret").getString()
    val issuer = environment.config.property("jwt.issuer").getString()
    val myRealm = environment.config.property("jwt.realm").getString()
    install(Authentication){
        jwt("auth-jwt") {
            realm = myRealm
            verifier(
                JWT.require(Algorithm.HMAC256(secret))
                    .withIssuer(issuer)
                    .build()
            )
            validate {credential ->
                if (credential.payload.getClaim("username").asString() != "")
                    JWTPrincipal(credential.payload)
                else null
            }
            challenge{_, _ ->
                call.respond(HttpStatusCode.Unauthorized, "Token is not valid or has expired")
            }
        }
    }
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
    "jdbc:mysql://52.64.206.219:3306/bars",
    user = "root",
    password = "@Vikination123",
    driver = "com.mysql.cj.jdbc.Driver"
)

fun connectDatabase(useLocal :Boolean = false) = if (useLocal) localDatabase() else database()
