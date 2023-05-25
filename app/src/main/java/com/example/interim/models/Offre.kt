package com.example.interim.models

import android.os.Parcel
import android.os.Parcelable

class Offre(
    var title: String?,
    var metier: String?,
    var description: String?,
    var date_debut: String?,
    var date_fin: String?,
    var remuneration: String?,
    var id: Long,
    var ville: String? = ""
): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readLong(),
        parcel.readString()
    ) {
    }

    override fun toString(): String {
        return "Offre(name='$title', " +
                "metier='$metier'," +
                "description='$description', " +
                "date_debut=$date_debut," +
                "date_fin=$date_fin, " +
                "remuneration='$remuneration')"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(metier)
        parcel.writeString(description)
        parcel.writeString(date_debut)
        parcel.writeString(date_fin)
        parcel.writeString(remuneration)
        parcel.writeLong(id)
        parcel.writeString(ville)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Offre> {
        override fun createFromParcel(parcel: Parcel): Offre {
            return Offre(parcel)
        }

        override fun newArray(size: Int): Array<Offre?> {
            return arrayOfNulls(size)
        }
    }


}