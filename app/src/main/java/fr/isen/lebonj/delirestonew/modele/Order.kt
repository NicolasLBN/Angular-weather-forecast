package fr.isen.lebonj.delirestonew.modele

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Order(
        @SerializedName("item") var item:Item,
        @SerializedName("quantity") var quantity: Int,
) : Serializable