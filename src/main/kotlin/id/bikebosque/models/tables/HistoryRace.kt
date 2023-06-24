package id.bikebosque.models.tables

import org.ktorm.schema.Table
import org.ktorm.schema.boolean
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object HistoryRace :Table<Nothing>(ConstantsTable.TABLE_HISTORY_RACE) {
    val id = int(ConstantsTable.ID).primaryKey()
    val idEvent = int(ConstantsTable.ID_EVENT)
    val idChild = int(ConstantsTable.ID_CHILD)
    val kelas = varchar(ConstantsTable.KELAS)
    val podium = varchar(ConstantsTable.PODIUM)
    val isVerified = boolean(ConstantsTable.IS_VERIFIED)
    val idVerificator = int(ConstantsTable.ID_VERIFICATOR)
    val verifiedDate = varchar(ConstantsTable.VERIFIED_DATE)
    val namaEvent = varchar(ConstantsTable.NAMA_EVENT)
}