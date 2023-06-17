package id.bikebosque.models.tables

import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object Parent :Table<Nothing>("parent"){
    val id = int("id").primaryKey()
    val nama = varchar("nama")
    val email = varchar("email")
    val password = varchar("password")
    val tempatLahir = varchar("tempat_lahir")
    val tanggalLahir = varchar("tanggal_lahir")
    var noHp = varchar("no_hp")
    var alamat = varchar("alamat")
    var photoUrl = varchar("photo_url")
    var komunitas = varchar("komunitas")
    var noKtp = varchar("no_ktp")
}