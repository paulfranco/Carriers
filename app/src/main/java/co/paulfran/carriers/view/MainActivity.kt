package co.paulfran.carriers.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import co.paulfran.carriers.R
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import co.paulfran.carriers.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: ListViewModel
    private val carriersAdapter = CarrierListAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        viewModel.refresh()

        carriersList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = carriersAdapter
        }

        observeViewModel()
    }

    fun observeViewModel() {
        viewModel.carriers.observe(this, Observer { carriers ->
            carriers?.let {
                carriersList.visibility = View.VISIBLE
                carriersAdapter.updateCarriers(it)
            }
        })
        viewModel.carrierLoadError.observe(this, Observer { isError ->
            list_error.visibility = if(isError == "") View.GONE else View.VISIBLE
        })
        viewModel.loading.observe(this, Observer { isLoading ->
            isLoading?.let {
                loading_view.visibility = if(it) View.VISIBLE else View.GONE
                if(it) {
                    list_error.visibility = View.GONE
                    carriersList.visibility = View.GONE
                }
            }
        })
    }
}
