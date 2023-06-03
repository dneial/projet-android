package com.example.interim.models

class Report(
    var id: Long,
    var email: String,
    var offre: Offre,
    var comment: String
) {
    override fun toString(): String {
        return "Report(id=$id, email='$email', Offre=$offre, comment='$comment')"
    }



}