package fr.isen.lebonj.delirestonew

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import fr.isen.lebonj.delirestonew.databinding.ActivityCategoryBinding
import org.json.JSONObject
import com.google.gson.GsonBuilder
import fr.isen.lebonj.delirestonew.modele.DataResult
import fr.isen.lebonj.delirestonew.modele.Item


enum class ItemType {
    STARTER, MAIN, DESSERT;

    companion object {
        fun categoryTitle(item: ItemType?) : String {
            return when(item) {
                STARTER -> "Entrées"
                MAIN -> "Plats"
                DESSERT -> "Dessert"
                else -> ""
            }
        }
    }
}

class CategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //val categoryName = intent.getStringExtra("category")
        //findViewById<TextView>(R.id.categoryTitle).text = categoryName
        val selectedItem = intent.getSerializableExtra(MainActivity.CATEGORY_NAME) as? ItemType
        binding.categoryTitle.text = getCategoryTitle(selectedItem)


        //binding.categoryList.layoutManager = LinearLayoutManager(this)
        getApi(getCategoryTitle(selectedItem))

    }

    private fun getApi(category: String?) {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = "http://test.api.catering.bluecodegames.com/menu"
        val jsonData = JSONObject().put("id_shop", "1")
        val jsonObjectRequest = JsonObjectRequest(Request.Method.POST, url, jsonData,
                { it ->
                        Log.d("Json result", it.toString())
                        parseResult(it.toString(), category)
                },
                { error ->
                    error.printStackTrace()
                }
        )
        queue.add(jsonObjectRequest)

    }

    private fun parseResult(res: String, selectedItem: String?) {
        val menuResult = GsonBuilder().create().fromJson(res, DataResult::class.java)
        val items = menuResult.data.firstOrNull { it.name == selectedItem }
        loadList(items?.items)
    }

    private fun loadList(items: List<Item>?) {

        items?.let {
            val adapter = CategoryAdapter(it) { item ->
                val intent = Intent(this, DetailActivity::class.java)
                intent.putExtra("item", item)
                startActivity(intent)

            }
            binding.categoryList.layoutManager = LinearLayoutManager(this)
            binding.categoryList.adapter = adapter

        }
    }

    private fun getCategoryTitle(item: ItemType?): String {
        return when (item) {
            ItemType.STARTER -> "Entrées"
            ItemType.MAIN -> "Plats"
            ItemType.DESSERT -> "Desserts"
            else -> ""
        }
    }
}
