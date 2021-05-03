package fr.isen.lebonj.delirestonew

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import com.synnapps.carouselview.ImageListener
import fr.isen.lebonj.delirestonew.databinding.ActivityDetailBinding
import fr.isen.lebonj.delirestonew.modele.Item
import kotlin.math.max

class DetailActivity : AppCompatActivity() {
    lateinit var binding : ActivityDetailBinding
    private var itemCount = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val detail = intent.getSerializableExtra("item") as? Item

        if (detail!=null)
        {
            binding.detailName.text = detail.name
            binding.carouselView.pageCount = detail.images.size

            var imageListener : ImageListener = object  : ImageListener {
                override fun setImageForPosition(position: Int, imageView: ImageView?) {
                    if(detail.images[0].isNullOrEmpty()) {
                        Picasso.get().load("http://www.essdetbol.ru/images/no_photo.png").into((imageView))
                    }else{
                        Picasso.get().load(detail.images[position]).into((imageView))
                    }
                }

            }
            binding.carouselView.setImageListener(imageListener)
            //if(detail.getFirstPicture().isNullOrEmpty()){
               // Picasso.get().load("https://cdn.radiofrance.fr/s3/cruiser-production/2019/12/610ac15e-0d90-4ad6-968a-747e6bdd2f84/870x489_bg.jpg").into(binding.detailImage)
            //}else{
               // Picasso.get().load(detail.getFirstPicture()).into(binding.detailImage)
            //}
        }

            binding.detailLessButton.setOnClickListener {
                itemCount = max(1, itemCount - 1)
                binding.detailCount.text = itemCount.toString()

            }

            binding.detailMoreButton.setOnClickListener {
                itemCount += 1
                binding.detailCount.text = itemCount.toString()
            }

            binding.detailShopButton.setOnClickListener {
                binding.detailCount.text = itemCount.toString()
            }







        }
}