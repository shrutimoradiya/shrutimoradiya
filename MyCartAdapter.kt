package com.sampsolution.contactlessdining.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sampsolution.contactlessdining.databinding.ItemMyCartBinding
import com.sampsolution.contactlessdining.model.myCartModel.MyCartData
import com.sampsolution.contactlessdining.utils.Constant

data class MyCartAdapter(
    var context: Context,
    val list: ArrayList<MyCartData>,
    val listener: OnClick
) :
    RecyclerView.Adapter<MyCartAdapter.TextModelViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextModelViewHolder {
        return TextModelViewHolder(
            ItemMyCartBinding.inflate(
                LayoutInflater.from(
                    context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: TextModelViewHolder, position: Int) {
        try {
            val data = list[position]
            var count = data.itemQty

            Glide.with(context).load(data.itemImage).into(holder.binding.img)

            holder.binding.tvAddress.text = list[position].itemDescription
            holder.binding.tvName.text = list[position].itemName

            holder.binding.tvRs.text = "$${data.originalAmount}"

            holder.binding.tv1.text = count.toString()

            holder.binding.tvAddition.setOnClickListener {
                count = count!! + 1
                holder.binding.tv1.text = count.toString()
                listener.onClick(data.itemId, count!!)
            }
            if (data.variations != null && data.variations != "") {
                holder.binding.tvVariation.visibility = View.VISIBLE
                holder.binding.tvVariation.text = "Variations: ${data.variations}"
            } else {
                holder.binding.tvVariation.visibility = View.GONE
            }

            holder.binding.tvMinus.setOnClickListener {
                if (count != 0) {
                    count = count!! - 1
                }
                holder.binding.tv1.text = count.toString()
                Constant.intSaveData(
                    context,
                    Constant.ADDCARTCOUNt,
                    (Constant.intGetData(
                        context,
                        Constant.ADDCARTCOUNt
                    ) - 1)
                )
                listener.onClick(data.itemId, count!!)
            }


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun getItemCount(): Int {
        return list.size
    }

    data class TextModelViewHolder(val binding: ItemMyCartBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface OnClick {
        fun onClick(itemId: Int?, count: Int)
    }

}

