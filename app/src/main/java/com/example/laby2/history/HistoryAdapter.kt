package com.example.laby2.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.laby2.R
import com.example.laby2.logic.BmiCategory
import com.example.laby2.logic.BmiRecord
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.history_recyclerview_item.view.*

class HistoryAdapter(private val entries: List<BmiRecord>) : RecyclerView.Adapter<HistoryAdapter.Companion.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryAdapter.Companion.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.history_recyclerview_item, parent, false)

        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return entries.size
    }

    override fun onBindViewHolder(holder: HistoryAdapter.Companion.ViewHolder, position: Int) {
        val entry = entries[position]
        holder.weight.text = entry.weight
        holder.height.text = entry.height
        holder.bmiValue.text = entry.bmiValue
        holder.bmiValue.setTextColor(entry.bmiCategoryColor)
        holder.bmiCategory.text = entry.bmiCategoryWord
        holder.date.text = entry.getShortDateAsString()

        Picasso.get()
            .load(BmiCategory.getCategory(entry.bmiValue.toDouble()).imgResource)
            .into(holder.image)
    }

    companion object {
        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val weight = itemView.history_weight
            val height = itemView.history_height
            val bmiValue = itemView.history_bmi_value
            val bmiCategory = itemView.history_category
            val date = itemView.history_date
            val image = itemView.history_bmi_category_img
        }
    }
}