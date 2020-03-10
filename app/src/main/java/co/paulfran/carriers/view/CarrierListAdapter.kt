package co.paulfran.carriers.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.paulfran.carriers.R
import co.paulfran.carriers.model.Carrier
import kotlinx.android.synthetic.main.item_carrier.view.*

class CarrierListAdapter(var carriers: ArrayList<Carrier>): RecyclerView.Adapter<CarrierListAdapter.CarrierViewHolder>() {

    fun updateCarriers(newCarriers: List<Carrier>) {
        carriers.clear()
        carriers.addAll(newCarriers)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = CarrierViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_carrier, parent, false)
    )

    override fun getItemCount() = carriers.size

    override fun onBindViewHolder(holder: CarrierViewHolder, position: Int) {
        holder.bind(carriers[position])
    }

    class CarrierViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val imageView = view.imageView
        private val carrierName = view.name
        private val ubi = view.ubi

        fun bind(carrier: Carrier) {
            carrierName.text = carrier.carrierName
            ubi.text = carrier.carrierUbi
            imageView.loadImage(carrier.photo)
        }
    }
}