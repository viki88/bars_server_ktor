package id.bikebosque.models.tables

import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object Child :Table<Nothing>(ConstantsTable.TABLE_CHILD) {
    val id = int(ConstantsTable.ID).primaryKey()
    val idParent = int(ConstantsTable.ID_PARENT)
    val nama = varchar(ConstantsTable.NAMA)
    val tempatLahir = varchar(ConstantsTable.TEMPAT_LAHIR)
    val tanggalLahir = varchar(ConstantsTable.TANGGAL_LAHIR)
    val noKIA = varchar(ConstantsTable.NO_KIA)
    val noRider = varchar(ConstantsTable.NOMOR_RIDER)
    val avatar = varchar(ConstantsTable.AVATAR)
}