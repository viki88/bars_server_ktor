package id.bikebosque.models

import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar


object Role :Table<Nothing>("role"){
    val id = int("id").primaryKey()
    val roleName = varchar("role_name")
    val roleDesc = varchar("role_desc")
}