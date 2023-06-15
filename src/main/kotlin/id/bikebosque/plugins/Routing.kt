package id.bikebosque.plugins

import com.google.gson.Gson
import id.bikebosque.database
import id.bikebosque.models.Role
import id.bikebosque.models.RoleData
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.ktorm.dsl.from
import org.ktorm.dsl.select

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText { "Hello world" }
        }

        get("/roles"){
            val rows = database().from(Role).select()
            val jsonString = Gson().toJson(RoleData.toRoleDataList(rows))
            call.respondText { jsonString }
        }
    }
}
