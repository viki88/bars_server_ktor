package id.bikebosque.models

import kotlinx.serialization.Serializable
import org.ktorm.dsl.Query
import org.ktorm.dsl.forEach

@Serializable
data class RoleData(
    val id :Int,
    val roleName :String,
    val roleDesc :String
){
    companion object{
        fun toRoleDataList(query :Query) :List<RoleData>{
            val roleList = arrayListOf<RoleData>()
            query.forEach { roleTable ->
                roleList.add(
                    RoleData(
                        roleTable[Role.id] ?: 0,
                        roleTable[Role.roleName] ?: "",
                        roleTable[Role.roleDesc] ?: ""
                    )
                )
            }
            return roleList
        }
    }
}