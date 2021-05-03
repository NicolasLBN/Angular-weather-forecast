package fr.isen.lebonj.delirestonew.modele

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class OrderList (
        @SerializedName("order") val order: MutableList<Order>,
) : Serializable