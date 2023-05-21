package com.example.interim.models

class Candidature (
    var id: Long,
    var id_offre: Long,
    var id_candidat: Long,
    var id_entreprise: Long,
    var status: String,
    var date: String
        ){


    override fun toString(): String {
        return "Candidature(id='$id', " +
                "id_offre='$id_offre'," +
                "id_candidat='$id_candidat', " +
                "id_entreprise=$id_entreprise," +
                "status=$status, " +
                "date='$date')"
    }
}