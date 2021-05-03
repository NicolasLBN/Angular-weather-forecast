package fr.isen.lebonj.delirestonew.modele

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Price(
        @SerializedName("price") val price: String,
        @SerializedName("size") val size: String,
) : Serializable