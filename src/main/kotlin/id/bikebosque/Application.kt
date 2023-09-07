package id.bikebosque

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import id.bikebosque.plugins.configureRouting
import id.bikebosque.utils.FirebaseAdmin
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.doublereceive.*
import io.ktor.server.response.*
import org.ktorm.database.Database

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0"){
//        val secret = environment.config.property("jwt.secret").getString()
//        val issuer = environment.config.property("jwt.issuer").getString()
//        val audience = environment.config.property("jwt.audience").getString()
//        val myRealm = environment.config.property("jwt.realm").getString()

        val secret = "bars-secret-string"
        val issuer = "https://bars-web-service.et.r.appspot.com/"
        val audience = "https://bars-web-service.et.r.appspot.com/hello"
        val myRealm = "Access to 'hello'"
        install(Authentication){
            jwt("auth-jwt") {
                realm = myRealm
                verifier(
                    JWT
                    .require(Algorithm.HMAC256(secret))
                    .withAudience(audience)
                    .withIssuer(issuer)
                    .build()
                )
                validate {credential ->
                    if (credential.payload.getClaim("username").asString() != "")
                        JWTPrincipal(credential.payload)
                    else null
                }
                challenge{defaultScheme, realm ->
                    call.respond(HttpStatusCode.Unauthorized, "Token is not valid or has expired")
                }
            }
        }
        install(DoubleReceive)
        FirebaseAdmin.init()
        configureRouting()
    }.start(wait = true)
}

//fun main(args: Array<String>) = io.ktor.server.netty.EngineMain.main(args)

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
