package id.bikebosque.routes

import id.bikebosque.connectDatabase
import id.bikebosque.models.data.ParentData
import id.bikebosque.models.response.BaseResponse
import id.bikebosque.models.tables.Parent
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.ktorm.dsl.*

fun Route.parentRoute(){
    route("/parent"){
        get("/profile") {
            val idParameter = call.parameters["id"]

            val query = connectDatabase().from(Parent).select(
                Parent.nama,
                Parent.email,
                Parent.alamat,
                Parent.tempatLahir,
                Parent.tanggalLahir,
                Parent.photoUrl,
                Parent.komunitas,
                Parent.noKtp,
                Parent.noHp
            ).limit(0,1).where { Parent.id.eq(idParameter?.toInt() ?: 0) }
            val parentData = ParentData.toParentData(query)

            val httpStatusCode = HttpStatusCode.OK.value
            val message = "Success"

            call.respondText {
                BaseResponse.toResponseString(httpStatusCode, message, parentData)
            }
        }
    }
}