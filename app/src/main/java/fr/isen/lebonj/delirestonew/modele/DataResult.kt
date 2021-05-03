package fr.isen.lebonj.delirestonew.modele

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class DataResult (
        @SerializedName("data") val data: List<Category>,
) : Serializable