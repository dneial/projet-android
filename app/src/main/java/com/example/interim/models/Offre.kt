package com.example.interim.models

import java.util.Date

class Offre(
    var name: String,
    var metier: String,
    var description: String,
    var date_debut: Date,
    var date_fin: Date,
    var remuneration: String
) {

    override fun toString(): String {
        return "Offre(name='$name', " +
                "metier='$metier'," +
                "description='$description', " +
                "date_debut=$date_debut," +
                "date_fin=$date_fin, " +
                "remuneration='$remuneration')"
    }


}