package fr.isen.lebonj.delirestonew.modele

import com.google.gson.annotations.SerializedName
import fr.isen.lebonj.delirestonew.Dish
import java.io.Serializable

data class Category (@SerializedName("name_fr") val name:String,
        @SerializedName("items") val items: List<Item>,
) : Serializable