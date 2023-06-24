package id.bikebosque.models.tables

import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object Parent :Table<Nothing>(ConstantsTable.TABLE_PARENT){
    val id = int(ConstantsTable.ID).primaryKey()
    val nama = varchar(ConstantsTable.NAMA)
    val email = varchar(ConstantsTable.EMAIL)
    val password = varchar(ConstantsTable.PASSWORD)
    val tempatLahir = varchar(ConstantsTable.TEMPAT_LAHIR)
    val tanggalLahir = varchar(ConstantsTable.TANGGAL_LAHIR)
    var noHp = varchar(ConstantsTable.NO_HP)
    var alamat = varchar(ConstantsTable.ALAMAT)
    var photoUrl = varchar(ConstantsTable.PHOTO_URL)
    var komunitas = varchar(ConstantsTable.KOMUNITAS)
    var noKtp = varchar(ConstantsTable.NO_KTP)
}