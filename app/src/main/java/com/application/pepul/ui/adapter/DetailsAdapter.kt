package com.application.pepul.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.application.pepul.R
import com.application.pepul.databinding.ItemDetailsBinding
import com.application.pepul.ui.model.GetData
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions


class DetailsAdapter(val context: Context) : RecyclerView.Adapter<DetailsAdapter.ViewHolder>() {

    private var list = emptyList<GetData>()
    var onItemClick: ((model: GetData) -> Unit)? = null
    var onItemLongClick: ((model: GetData) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_details, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]

        with(holder) {

            if(model.file_type.equals("0")){
                binding.imgPlay.visibility = View.GONE
                Glide.with(context).load(model.file).diskCacheStrategy(DiskCacheStrategy.DATA).into(binding.imgItem)
            }else{
                binding.imgPlay.visibility = View.VISIBLE
                val requestOptions = RequestOptions()
                Glide.with(context)
                    .load(model.file)
                    .apply(requestOptions)
                    .thumbnail(Glide.with(context).load(model.file))
                    .into(binding.imgItem)
            }

            itemView.setOnClickListener {
                onItemClick!!.invoke(model)
            }

            itemView.setOnLongClickListener(OnLongClickListener {
                onItemLongClick!!.invoke(model)
                true
            })

        }
    }

    internal fun setItems(items: List<GetData>, size: Int, sizeNew: Int) {
        this.list = items
        notifyItemRangeChanged(size,sizeNew)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemDetailsBinding.bind(itemView)
    }
}