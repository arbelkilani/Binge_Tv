package com.arbelkilani.bingetv.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.arbelkilani.bingetv.R
import com.arbelkilani.bingetv.data.model.credit.Credit
import com.arbelkilani.bingetv.databinding.ItemCreditBindingImpl

class CreditAdapter(private val credits: List<Credit>) :
    RecyclerView.Adapter<CreditAdapter.CreditHolder>() {

    class CreditHolder(val itemCreditBindingImpl: ItemCreditBindingImpl) :
        RecyclerView.ViewHolder(itemCreditBindingImpl.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreditHolder {
        val itemCastBindingImpl = DataBindingUtil.inflate<ItemCreditBindingImpl>(
            LayoutInflater.from(parent.context), R.layout.item_credit, parent, false
        )
        return CreditHolder(itemCastBindingImpl)
    }

    override fun getItemCount(): Int {
        return credits.size
    }

    override fun onBindViewHolder(holder: CreditHolder, position: Int) {
        holder.itemCreditBindingImpl.credit = credits[position]
    }
}