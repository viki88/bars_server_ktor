package id.bikebosque.models.data

import id.bikebosque.models.tables.Child
import org.ktorm.dsl.Query
import org.ktorm.dsl.forEach

data class ChildData(
    var id :Int? = null,
    var idParent :Int? = null,
    var nama :String? = null,
    var tempatLahir :String? = null,
    var tanggalLahir :String? = null,
    var noKIA :String? = null,
    var nomorRider :String? = null,
    var avatar :String? = null
){
    companion object{
        fun toChildDatas(query: Query) :List<ChildData>{
            val listChildData = arrayListOf<ChildData>()
            query.forEach { data ->
                listChildData.add(
                    ChildData(
                        data[Child.id],
                        data[Child.idParent],
                        data[Child.nama],
                        data[Child.tempatLahir],
                        data[Child.tanggalLahir],
                        data[Child.noKIA],
                        data[Child.noRider],
                        data[Child.avatar]
                    )
                )
            }
            return listChildData
        }
    }
}
