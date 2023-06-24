package id.bikebosque.models.data

import id.bikebosque.models.tables.HistoryRace
import org.ktorm.dsl.Query
import org.ktorm.dsl.forEach

data class HistoryRaceData(
    var id :Int? = null,
    var idEvent :Int? = null,
    var idChild :Int? = null,
    var kelas :String? = null,
    var podium :String? = null,
    var isVerified :Boolean? = null,
    var idVerificator :Int? = null,
    var verifiedDate :String? = null,
    var namaEvent :String? = null
){
    companion object{
        fun toHistoryRaceDataList(query: Query) :List<HistoryRaceData>{
            val listHistoryRace = arrayListOf<HistoryRaceData>()
            query.forEach { rowSet ->
                listHistoryRace.add(
                    HistoryRaceData(
                        rowSet[HistoryRace.id],
                        rowSet[HistoryRace.idEvent],
                        rowSet[HistoryRace.idChild],
                        rowSet[HistoryRace.kelas],
                        rowSet[HistoryRace.podium],
                        rowSet[HistoryRace.isVerified],
                        rowSet[HistoryRace.idVerificator],
                        rowSet[HistoryRace.verifiedDate],
                        rowSet[HistoryRace.namaEvent]
                    )
                )
            }
            return listHistoryRace
        }
    }
}