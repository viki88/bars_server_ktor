package id.bikebosque.routes

import com.google.gson.Gson
import id.bikebosque.connectDatabase
import id.bikebosque.models.data.ChildData
import id.bikebosque.models.data.HistoryRaceData
import id.bikebosque.models.response.BaseResponse
import id.bikebosque.models.tables.Child
import id.bikebosque.models.tables.HistoryRace
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

        post("/add-history-race"){
            val historyRaceParam = call.receiveParameters()
            val idChildParam = historyRaceParam[ConstantsParameters.ID_CHILD_PARAM]
            val kelasParam = historyRaceParam[ConstantsParameters.KELAS_PARAM]
            val namaEventParam = historyRaceParam[ConstantsParameters.NAMA_EVENT_PARAM]
            val podiumParam = historyRaceParam[ConstantsParameters.PODIUM_PARAM]
            val idEventParam = historyRaceParam[ConstantsParameters.ID_EVENT_PARAM]

            val result = connectDatabase().insertOrUpdate(HistoryRace){
//                set(HistoryRace.idEvent, 0)
                set(HistoryRace.idChild, idChildParam?.toInt())
                set(HistoryRace.kelas, kelasParam)
                set(HistoryRace.namaEvent, namaEventParam)
                set(HistoryRace.podium, podiumParam)
                set(HistoryRace.isVerified, false)
//                set(HistoryRace.idVerificator, 0)
//                set(HistoryRace.verifiedDate, 0)
            }

            call.respondText {
                BaseResponse.toResponseString(
                    if (result > 0) HttpStatusCode.OK.value else HttpStatusCode.BadRequest.value,
                    if (result > 0) "Sukses input history race" else "Gagal input history race, silahkan coba lagi",
                    null
                )
            }
        }

        get("/history-race"){
            val historyRaceParameter = call.parameters
            val idChildParameter = historyRaceParameter[ConstantsParameters.ID_CHILD_PARAM]

            val query = connectDatabase().from(HistoryRace).select().where { HistoryRace.idChild.eq(idChildParameter?.toInt() ?: 1) }

            val data = HistoryRaceData.toHistoryRaceDataList(query)

            call.respondText {
                BaseResponse.toResponseString(
                    HttpStatusCode.OK.value,
                    "Sukses mengambil data",
                    data
                )
            }
        }
    }
}