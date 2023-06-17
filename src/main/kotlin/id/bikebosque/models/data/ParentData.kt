package id.bikebosque.models.data

import org.ktorm.dsl.Query

data class ParentData(
    var id :Int,
    var name :String,
    var email :String,
    var passsword :String,
    var tempatLahir :String,
    var tanggalLahir :String,
    var noHp :String,
    var alamat :String,
    var photoUrl :String,
    var komunitas :String,
    var noKtp :String
){
    fun toParentData(query: Query){

    }
}
