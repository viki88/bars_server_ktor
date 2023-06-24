package id.bikebosque.routes

import com.google.gson.Gson
import id.bikebosque.connectDatabase
import id.bikebosque.models.data.ParentData
import id.bikebosque.models.response.BaseResponse
import id.bikebosque.models.response.UploadImageResponse
import id.bikebosque.models.tables.Child
import id.bikebosque.models.tables.Parent
import id.bikebosque.utils.UploadFileService
import id.bikebosque.utils.md5
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.ktorm.dsl.*
import org.ktorm.support.mysql.insertOrUpdate
import java.io.File
import java.sql.SQLIntegrityConstraintViolationException
import kotlin.math.tan

fun Route.userRoute(){
    route("/user"){

        childRoute()
        parentRoute()

        post("/register") {
            val registerParameters = call.receiveParameters()
            val emailParam = registerParameters[ConstantsParameters.EMAIL_PARAM]
            val namaParam = registerParameters[ConstantsParameters.NAMA_PARAM]
            val passwordParam = registerParameters[ConstantsParameters.PASSWORD_PARAM]
            val tempatLahirParam = registerParameters[ConstantsParameters.TEMPAT_LAHIR_PARAM]
            val tanggalLahirParam = registerParameters[ConstantsParameters.TANGGAL_LAHIR_PARAM]
            val noHpParam = registerParameters[ConstantsParameters.NOHP_PARAM]
            val alamatParam = registerParameters[ConstantsParameters.ALAMAT_PARAM]
            val photoUrlParam = registerParameters[ConstantsParameters.PHOTO_URL_PARAM]
            val komunitasParam = registerParameters[ConstantsParameters.KOMUNITAS_PARAM]
            val noKtpParam = registerParameters[ConstantsParameters.NOKTP_PARAM]

            var messageResponse = "Success Register"
            var statusCode = HttpStatusCode.OK.value
            try {
                val int = connectDatabase().insertOrUpdate(Parent){
                    set(it.nama, namaParam)
                    set(it.email, emailParam)
                    set(it.password, passwordParam?.md5())
                    set(it.tempatLahir, tempatLahirParam)
                    set(it.tanggalLahir, tanggalLahirParam)
                    set(it.noHp, noHpParam)
                    set(it.alamat, alamatParam)
                    set(it.photoUrl, photoUrlParam)
                    set(it.komunitas, komunitasParam)
                    set(it.noKtp, noKtpParam)
                }
            }catch (sqlException :SQLIntegrityConstraintViolationException){
                sqlException.message?.let {errorMessage ->
                    if (errorMessage.contains("duplicate", true)){
                        messageResponse = "Alamat email dan/atau noktp sudah terdaftar"
                        statusCode = HttpStatusCode.Forbidden.value
                    }
                }

            }

            call.respondText { BaseResponse.toJsonString(HttpStatusCode(statusCode, messageResponse)) }
        }

        post("/upload-image"){
            var fileDescription = ""
            var fileName = ""
            var fileImage :File? = null

            val multipartData = call.receiveMultipart()
            multipartData.forEachPart { part ->
                when(part){
                    is PartData.FormItem -> {
                        fileDescription = part.value
                    }
                    is PartData.FileItem -> {
                        fileName = part.originalFileName as String
                        val fileBytes = part.streamProvider().readBytes()
                        fileImage = File("uploads/$fileName")
                        fileImage?.writeBytes(fileBytes)
                    }
                    else -> {}
                }
                part.dispose
            }

            var mapImageResponse = mapOf<String, String>()
            @Suppress("UNCHECKED_CAST")
            if (fileImage != null) mapImageResponse = UploadFileService().uploadFile(fileImage!!) as Map<String, String>
            fileImage?.delete()
            val urlResponse = mapImageResponse["url"] as String
            val response = UploadImageResponse(
                statusCode = if (urlResponse.isNotEmpty()) HttpStatusCode.OK.value else HttpStatusCode.Forbidden.value,
                url = urlResponse,
                message = if (urlResponse.isNotEmpty())  "Upload Image Success" else "Failed upload image"
            )
            call.respondText { Gson().toJson(response) }
        }

        post("/login"){
            val loginParameters = call.receiveParameters()
            val emailParam = loginParameters[ConstantsParameters.EMAIL_PARAM]
            val passwordParam = loginParameters[ConstantsParameters.PASSWORD_PARAM]

            val queryLogin = connectDatabase().from(Parent).select(Parent.password).limit(0,1).where{ Parent.email.eq(emailParam as String)}
            var passwordIsMatched = false
            val totalRecords = queryLogin.totalRecords
            if (totalRecords > 0) {
                run start@{
                    queryLogin.forEach { password ->
                        passwordIsMatched = password[Parent.password] == passwordParam?.md5()
                    }
                    call.respondText {
                        BaseResponse.toResponseString(
                            if (passwordIsMatched) HttpStatusCode.OK.value else HttpStatusCode.NotFound.value,
                            if (passwordIsMatched) "Sukses Login" else "Password anda salah silahkan coba lagi",
                            null) }
                }
            }else call.respondText { BaseResponse.toResponseString(HttpStatusCode.NotFound.value, "Akun anda belum terdaftar", null) }

        }


    }


}