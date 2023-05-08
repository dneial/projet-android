package com.example.interim.models

class Offre(
    var title: String,
    var metier: String,
    var description: String,
    var date_debut: String,
    var date_fin: String,
    var remuneration: String,
    var id: Int,
    var id_entreprise: String = ""
) {


    override fun toString(): String {
        return "Offre(name='$title', " +
                "metier='$metier'," +
                "description='$description', " +
                "date_debut=$date_debut," +
                "date_fin=$date_fin, " +
                "remuneration='$remuneration')"
    }


}