package id.bikebosque.models.tables

import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar


object Role :Table<Nothing>(ConstantsTable.TABLE_ROLE){
    val id = int(ConstantsTable.ID).primaryKey()
    val roleName = varchar(ConstantsTable.ROLE_NAME)
    val roleDesc = varchar(ConstantsTable.ROLE_DESC)
}