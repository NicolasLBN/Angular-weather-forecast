package fr.isen.lebonj.delirestonew

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.lebonj.delirestonew.databinding.CellCategoryBinding
import fr.isen.lebonj.delirestonew.modele.Item


class CategoryAdapter(private val categories: List<Item>, private val clickListener: (Item) -> Unit): RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> (){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = CellCategoryBinding
                .inflate(LayoutInflater.from(parent.context),parent,false)
        return CategoryViewHolder(binding)


    }

    override fun onBindViewHolder(holder:CategoryViewHolder, position: Int) {
        holder.title.text = categories[position].name
        holder.price.text = categories [position].getAffichagePrice()
        if(categories[position].getFirstPicture().isNullOrEmpty()){
            Picasso.get().load("https://cdn.radiofrance.fr/s3/cruiser-production/2019/12/610ac15e-0d90-4ad6-968a-747e6bdd2f84/870x489_bg.jpg").into(holder.image)
        }else{
            Picasso.get().load(categories[position].getFirstPicture()).into(holder.image)
        }
        
        holder.layout.setOnClickListener{
            clickListener.invoke(categories[position])

        }
    }

    override fun getItemCount(): Int {
        return categories.size

    }

    class CategoryViewHolder(binding: CellCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        val title : TextView = itemView.findViewById(R.id.cellBleDeviceName)
        val layout = itemView.findViewById<View>(R.id.cellLayout)
        val image = binding.cellCategoryImage
        val price = binding.cellCategoryPrice


    }

    interface onItemClickListener{
        fun onItemClick (item: String)

    }

}