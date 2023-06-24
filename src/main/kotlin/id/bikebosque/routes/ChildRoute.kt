package id.bikebosque.routes

import com.google.gson.Gson
import id.bikebosque.connectDatabase
import id.bikebosque.models.data.ChildData
import id.bikebosque.models.response.BaseResponse
import id.bikebosque.models.tables.Child
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.select
import org.ktorm.dsl.where
import org.ktorm.support.mysql.insertOrUpdate

fun Route.childRoute(){
    route("/child"){

        post("/add-child"){
            val childParameters = call.receiveParameters()
            val idParentParam = childParameters[ConstantsParameters.ID_PARENT_PARAM]
            val namaParam = childParameters[ConstantsParameters.NAMA_PARAM]
            val tempatLahirParam = childParameters[ConstantsParameters.TEMPAT_LAHIR_PARAM]
            val tanggalLahirParam = childParameters[ConstantsParameters.TANGGAL_LAHIR_PARAM]
            val noKIAParam = childParameters[ConstantsParameters.NO_KIA_PARAM]
            val noRiderParam = childParameters[ConstantsParameters.NO_RIDER_PARAM]
            val avatarParam = childParameters[ConstantsParameters.AVATAR_PARAM]

            val int = connectDatabase().insertOrUpdate(Child){ child ->
                println("id parent : $idParentParam")
                set(child.idParent, idParentParam?.toInt())
                set(child.nama, namaParam)
                set(child.tempatLahir, tempatLahirParam)
                set(child.tanggalLahir, tanggalLahirParam)
                set(child.noKIA, noKIAParam)
                set(child.noRider, noRiderParam)
                set(child.avatar, avatarParam)
            }

            call.respondText {
                BaseResponse.toResponseString(
                    if (int > 0) HttpStatusCode.OK.value else HttpStatusCode.BadRequest.value,
                    if (int > 0) "Sukses Menambah data rider" else "Gagal input data rider, silahkan coba lagi",
                    null
                )
            }

        }

        get("/children"){
            val childrenParam = call.parameters
            val parentIdParam = childrenParam[ConstantsParameters.ID_PARENT_PARAM]
            val query = connectDatabase().from(Child).select().where(Child.idParent.eq(parentIdParam?.toInt() ?: 0))

            call.respondText {
                BaseResponse.toResponseString(
                    HttpStatusCode.OK.value,
                    "Success",
                    ChildData.toChildDatas(query))
            }
        }
    }
}