package com.example.translator.presentation.word

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.translator.databinding.ActivityWordBinding
import com.example.translator.entities.DataModel
import com.example.translator.entities.Meanings

class WordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWordBinding

    private var adapter: WordAdapter? = null
    private val onListItemClickListener: WordAdapter.OnListItemClickListener =
        object : WordAdapter.OnListItemClickListener {
            override fun onItemClick(data: Meanings) {
                Toast.makeText(this@WordActivity, data.translation?.translation, Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val word = intent.getStringExtra("word")
        binding.wordTextView.text = word

        val data = intent.getParcelableExtra<DataModel>("data")
        binding.wordTextView.text = data?.text
        showTranslations(data?.meanings)
    }

    private fun showTranslations(data: List<Meanings>?) {

        if (data != null && data.isNotEmpty()) {
            if (adapter == null) {
                binding.wordActivityRecyclerview.layoutManager = LinearLayoutManager(applicationContext)
                binding.wordActivityRecyclerview.adapter = WordAdapter(onListItemClickListener, data)
            } else {
                adapter!!.setData(data)
            }
        }
    }
}