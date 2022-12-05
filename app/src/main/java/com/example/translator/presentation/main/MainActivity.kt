package com.example.translator.presentation.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.translator.App
import com.example.translator.R
import com.example.translator.databinding.ActivityMainBinding
import com.example.translator.entities.DataModel
import com.example.translator.presentation.base.BasePresenter
import com.example.translator.presentation.word.WordActivity

class MainActivity : AppCompatActivity(),
    com.example.translator.presentation.base.BaseView {
    private lateinit var binding: ActivityMainBinding
    private lateinit var presenter: BasePresenter

    /**
     * Слово для перевода. Вынесли его в глобальную переменную, поскольку оно
     * понадобится для повторного запроса в случае ошибки
     */
    private var word: String? = null

    private var adapter: MainAdapter? = null
    private val onListItemClickListener: MainAdapter.OnListItemClickListener =
        object : MainAdapter.OnListItemClickListener {
            override fun onItemClick(data: DataModel) {
                val intent = Intent(this@MainActivity, WordActivity::class.java)
                intent.putExtra("data", data)
                intent.putExtra("word", word)
                startActivity(intent)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = App.instance.mainActivityPresenterImpl
        presenter.attach(this)

        binding.search.queryHint = "Введите слово для поиска"
        binding.search.onActionViewExpanded()
        binding.search.clearFocus()

        binding.search.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    word = query
                    presenter.getData(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    override fun showData(data: List<DataModel>) {
        if (data.isEmpty()) {
            showError(getString(R.string.empty_server_response_on_success))
        } else {
            showViewSuccess()
            if (adapter == null) {
                binding.mainActivityRecyclerview.layoutManager =
                    LinearLayoutManager(applicationContext)
                binding.mainActivityRecyclerview.adapter =
                    MainAdapter(onListItemClickListener, data)
            } else {
                adapter!!.setData(data)
            }
        }
    }

    override fun showError(message: String?) {
        showViewError()
        binding.errorTextview.text =
            message ?: getString(R.string.undefined_error)
        binding.reloadButton.setOnClickListener { _ ->
            word?.let { it -> presenter.getData(it) }
        }
    }

    override fun showLoading() {
        showViewLoading()
    }

    private fun showViewSuccess() {
        binding.successLinearLayout.visibility = View.VISIBLE
        binding.loadingFrameLayout.visibility = View.GONE
        binding.errorLinearLayout.visibility = View.GONE
    }

    private fun showViewLoading() {
        binding.successLinearLayout.visibility = View.GONE
        binding.loadingFrameLayout.visibility = View.VISIBLE
        binding.errorLinearLayout.visibility = View.GONE
    }

    private fun showViewError() {
        binding.successLinearLayout.visibility = View.GONE
        binding.loadingFrameLayout.visibility = View.GONE
        binding.errorLinearLayout.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        presenter.detach()
        super.onDestroy()
    }
}