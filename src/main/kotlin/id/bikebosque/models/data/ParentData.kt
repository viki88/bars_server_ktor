package id.bikebosque.models.data

import id.bikebosque.models.tables.Parent
import org.ktorm.dsl.Query
import org.ktorm.dsl.forEach

data class ParentData(
    var id :Int? = null,
    var name :String? = null,
    var email :String? = null,
    var passsword :String? = null,
    var tempatLahir :String? = null,
    var tanggalLahir :String? = null,
    var noHp :String? = null,
    var alamat :String? = null,
    var photoUrl :String? = null,
    var komunitas :String? = null,
    var noKtp :String? = null
){
    companion object{
        fun toParentData(query: Query) :ParentData{
            var allParentData = arrayListOf<ParentData>()
            query.forEach {
                allParentData.add(
                    ParentData(
                        it[Parent.id],
                        it[Parent.nama],
                        it[Parent.email],
                        it[Parent.password],
                        it[Parent.tempatLahir],
                        it[Parent.tanggalLahir],
                        it[Parent.noHp],
                        it[Parent.alamat],
                        it[Parent.photoUrl],
                        it[Parent.komunitas],
                        it[Parent.noKtp]
                    )
                )
            }
            return allParentData[0]
        }
    }
}
