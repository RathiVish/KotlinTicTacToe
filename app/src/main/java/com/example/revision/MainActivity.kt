package com.example.revision

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.example.revision.databinding.ActivityMainBinding
import com.example.revision.recycler.ItemTapListener
import com.example.revision.recycler.TicTacToeAdapter

class MainActivity : AppCompatActivity(), ItemTapListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var ticTacToeAdapter: TicTacToeAdapter
    private lateinit var winningPosition: Array<IntArray>
    private lateinit var gameState: ArrayList<Int>
    private lateinit var blankImgList: ArrayList<Int>

    //Player Representation
    private val o = 1
    private val x = 2
    private val empty = 0
    private var activePlayer = o
    private var isGameActive = true

    //Image Representation
    private val imgO = R.drawable.o
    private val imgX = R.drawable.x
    private val blankImg = R.drawable.blank_img

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initItems()
    }

    private fun initItems() {
        initGameState()
        initWinningPosition()
        initImgList()
        initRecyclerView()
        initReset()
    }

    private fun initGameState() {
        gameState = ArrayList()
        for (index in 0..8) {
            gameState.add(empty)
        }
    }

    private fun initWinningPosition() {
        winningPosition = arrayOf(
            intArrayOf(0, 1, 2), intArrayOf(3, 4, 5), intArrayOf(6, 7, 8), intArrayOf(0, 3, 6),
            intArrayOf(1, 4, 7), intArrayOf(2, 5, 8), intArrayOf(0, 4, 8), intArrayOf(2, 4, 6)
        )
    }

    private fun initImgList() {
        blankImgList = ArrayList(9)
        for (img in 0..8) {
            blankImgList.add(blankImg)
        }
    }

    private fun initRecyclerView() {
        binding.rcView.layoutManager = GridLayoutManager(this, 3)
        ticTacToeAdapter = TicTacToeAdapter(blankImgList)
        ticTacToeAdapter.setOnItemClickListener(this)
        binding.rcView.adapter = ticTacToeAdapter
    }

    private fun initReset() {
        binding.btnReset.setOnClickListener {
            resetGame()
        }
    }

    private fun resetGame() {
        for (img in blankImgList.indices) blankImgList[img] = blankImg
        for (state in gameState.indices) gameState[state] = empty

        binding.rcView.adapter!!.notifyDataSetChanged()
        binding.btnReset.visibility = View.INVISIBLE
        binding.tvStatus.text = getString(R.string.initialStatus)
        isGameActive = true
    }

    override fun onTap(position: Int, img: ImageView) {
        if (isGameActive) {
            img.translationY = -1000f
            if (gameState[position] == empty) {
                gameState[position] = activePlayer
                setInputInImageResource(img)
                checkIfSomeOneHasWon()
            }
        }
    }

    private fun setInputInImageResource(imgView: ImageView) {
        if (activePlayer == o) managePlayerInput(imgView, x, R.string.xTurn, imgO)
        else managePlayerInput(imgView, o, R.string.oTurn, imgX)
        imgView.animate().translationYBy(1000f).duration = 300
    }

    private fun managePlayerInput(
        imgView: ImageView,
        playerToActivate: Int,
        playerTurn: Int,
        imgSrc: Int
    ) {
        imgView.setImageResource(imgSrc)
        binding.tvStatus.text = getString(playerTurn)
        activePlayer = playerToActivate
    }

    private fun checkIfSomeOneHasWon() {
        if (gameState.contains(empty)) {
            for (winPosition in winningPosition) {
                if (gameState[winPosition[0]] == gameState[winPosition[1]] &&
                    gameState[winPosition[1]] == gameState[winPosition[2]] &&
                    gameState[winPosition[0]] != empty
                ) {
                    // Somebody has won! - Find out who!
                    announceWinner(winPosition)
                }
            }
        } else {
            binding.tvStatus.text = getString(R.string.matchDrawn)
            binding.btnReset.visibility = View.VISIBLE
            isGameActive = false
        }
    }

    private fun announceWinner(winPosition: IntArray) {
        val winnerStr =
            if (gameState[winPosition[0]] == o) getString(R.string.winnerO)
            else getString(R.string.winnerX)

        // Update the status bar for winner announcement
        binding.tvStatus.text = winnerStr
        binding.btnReset.visibility = View.VISIBLE
        isGameActive = false
    }
}