package id.bikebosque.routes

import com.google.gson.Gson
import id.bikebosque.connectDatabase
import id.bikebosque.models.data.RoleData
import id.bikebosque.models.response.BaseResponse
import id.bikebosque.models.tables.Role
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.ktorm.dsl.from
import org.ktorm.dsl.select
import org.ktorm.support.mysql.insertOrUpdate

fun Route.adminRoute(){
    route("/admin"){
        get("/roles"){
            val rows = connectDatabase().from(Role).select()
            val jsonString = Gson().toJson(RoleData.toRoleDataList(rows))
            call.respondText { jsonString }
        }
        post("/addrole"){
            val addRoleParameters = call.receiveParameters()
            val nameParameter = addRoleParameters["name"]
            val descParameter = addRoleParameters["desc"]

            var messageResponse = "Success Register"
            var statusCode = HttpStatusCode.OK.value
            try {
                val int = connectDatabase().insertOrUpdate(Role){
                    set(it.roleName, nameParameter)
                    set(it.roleDesc, descParameter)
                }
            }catch (sqlException : Exception){
                sqlException.message?.let {errorMessage ->
//                    if (errorMessage.contains("duplicate", true)){
                        messageResponse = errorMessage
                        statusCode = HttpStatusCode.Forbidden.value
//                    }
                }

            }

            call.respondText { BaseResponse.toJsonString(HttpStatusCode(statusCode, messageResponse)) }
        }
    }
}