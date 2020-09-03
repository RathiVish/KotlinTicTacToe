package com.example.revision.recycler

import androidx.recyclerview.widget.RecyclerView
import com.example.revision.databinding.GridItemBinding

class TicTacToeHolder(private val binding: GridItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun handleView(position: Int, tapListener: ItemTapListener, blankImgList: ArrayList<Int>) {
        binding.ivInput.setImageResource(blankImgList[position])
        binding.ivInput.setOnClickListener {
            tapListener.onTap(position, binding.ivInput)
        }
    }
}