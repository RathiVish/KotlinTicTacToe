package com.example.revision.recycler

import android.widget.ImageView

interface ItemTapListener {
    fun onTap(position: Int, img: ImageView)
}