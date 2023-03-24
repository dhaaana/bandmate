package com.dhana.bandmate.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dhana.bandmate.R
import com.dhana.bandmate.model.BandMember

class ListBandMemberAdapter(private val listBandMember: ArrayList<BandMember>) : RecyclerView.Adapter<ListBandMemberAdapter.ListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_band_member, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (name, positions) = listBandMember[position]
        holder.bandMemberName.text = name
        holder.bandMemberPosition.text = positions
    }

    override fun getItemCount(): Int = listBandMember.size

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bandMemberName: TextView = itemView.findViewById(R.id.detail_band_member_name)
        val bandMemberPosition: TextView = itemView.findViewById(R.id.detail_band_member_position)
    }

}