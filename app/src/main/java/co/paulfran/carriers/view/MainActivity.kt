package co.paulfran.carriers.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import co.paulfran.carriers.R
import co.paulfran.carriers.viewmodel.ListViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    lateinit var mAdView : AdView

    lateinit var viewModel: ListViewModel
    private val carriersAdapter = CarrierListAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MobileAds.initialize(this) {}
        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

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
            list_error.visibility = if(isError == null) View.GONE else View.VISIBLE
            Toast.makeText(this, "Error: Data Not Found", Toast.LENGTH_SHORT).show()
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
