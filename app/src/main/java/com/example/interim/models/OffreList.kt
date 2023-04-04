package com.example.interim.models

import java.util.*

class OffreList {

    private var offreList: List<Offre> = listOf(
        Offre("Offre 1", "Développeur", "Description de l'offre 1", Date(), Date(), "1000€"),
        Offre("Offre 2", "Développeur", "Description de l'offre 2", Date(), Date(), "1000€"),
        Offre("Offre 3", "Développeur", "Description de l'offre 3", Date(), Date(), "1000€"),
        Offre("Offre 4", "Développeur", "Description de l'offre 4", Date(), Date(), "1000€"),
        Offre("Offre 5", "Chef de projet", "Description de l'offre 5", Date(), Date(), "1000€"),
        Offre("Offre 6", "Chef de projet", "Description de l'offre 6", Date(), Date(), "1000€"),
        Offre("Offre 7", "Chef de projet", "Description de l'offre 7", Date(), Date(), "1000€"),
        Offre("Offre 8", "UX Designer", "Description de l'offre 8", Date(), Date(), "1000€"),
        Offre("Offre 9", "UX Designer", "Description de l'offre 9", Date(), Date(), "1000€"),
        Offre("Offre 10", "UX Designer", "Description de l'offre 10", Date(), Date(), "1000€"),
    )

    public fun getOffreList(): List<Offre> {
        return offreList
    }
}