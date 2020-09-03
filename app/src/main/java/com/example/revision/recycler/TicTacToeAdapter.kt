package com.example.revision.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.revision.R
import com.example.revision.databinding.GridItemBinding

class TicTacToeAdapter(private val blankImgList: ArrayList<Int>) :
    RecyclerView.Adapter<TicTacToeHolder>() {

    private lateinit var binding: GridItemBinding

    override fun getItemCount() = blankImgList.size

    private lateinit var tapListener: ItemTapListener

    fun setOnItemClickListener(onTapClickListener: ItemTapListener) {
        this.tapListener = onTapClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicTacToeHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.grid_item, parent,
            false
        )
        return TicTacToeHolder(binding)
    }

    override fun onBindViewHolder(holder: TicTacToeHolder, position: Int) {
        holder.handleView(position, tapListener, blankImgList)

    }
}