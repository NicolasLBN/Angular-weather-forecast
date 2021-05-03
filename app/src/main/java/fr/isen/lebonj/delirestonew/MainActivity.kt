package fr.isen.lebonj.delirestonew

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import fr.isen.lebonj.delirestonew.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.starters.setOnClickListener()
        {
        startCategoryActivity(ItemType.STARTER)
        }

        binding.dishes.setOnClickListener()
        {
            startCategoryActivity(ItemType.MAIN)

        }

        binding.desert.setOnClickListener()
        {
            startCategoryActivity(ItemType.DESSERT)

        }


            binding.bleButton.setOnClickListener {
                val intent = Intent(this@MainActivity, BleScanActivity::class.java)
                startActivity(intent)
            }


    }
    private fun startCategoryActivity(item: ItemType) {
        val intent = Intent(this, CategoryActivity::class.java)
        intent.putExtra(CATEGORY_NAME, item)
        startActivity(intent)
    }

    companion object {
        const val CATEGORY_NAME = "CATEGORY_NAME"
    }
}


