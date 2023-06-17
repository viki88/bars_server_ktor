package id.bikebosque.routes

import com.google.gson.Gson
import id.bikebosque.database
import id.bikebosque.models.tables.Role
import id.bikebosque.models.data.RoleData
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.ktorm.dsl.from
import org.ktorm.dsl.select

fun Route.adminRoute(){
    route("/admin"){
        get("/roles"){
            val rows = database().from(Role).select()
            val jsonString = Gson().toJson(RoleData.toRoleDataList(rows))
            call.respondText { jsonString }
        }
    }
}