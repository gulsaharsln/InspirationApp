package com.example.inspirationapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import android.graphics.drawable.ColorDrawable
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.core.content.ContextCompat

class DayAdapter(private var musicTips: List<MusicTip>, private val onDayClicked: (MusicTip) -> Unit) : RecyclerView.Adapter<DayAdapter.DayViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.day_item, parent, false)
        return DayViewHolder(view, onDayClicked)
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        holder.bind(musicTips[position])
    }

    override fun getItemCount() = musicTips.size

    class DayViewHolder(itemView: View, private val onDayClicked: (MusicTip) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val dayNumberTextView: TextView = itemView.findViewById(R.id.dayNumberTextView)

        fun bind(musicTip: MusicTip) {
            dayNumberTextView.text = musicTip.id.toString()
            itemView.setOnClickListener { onDayClicked(musicTip) }
        }
    }

    fun updateData(newMusicTips: List<MusicTip>) {
        musicTips = newMusicTips
        notifyDataSetChanged()
    }
}

class MainActivity : AppCompatActivity() {
    private lateinit var dayAdapter: DayAdapter
    private val viewModel: MusicTipViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupActionBar()
        setupDayRecyclerView()

        viewModel.musicTips.observe(this) { musicTips ->
            updateRecyclerView(musicTips)
        }
    }

    private fun setupActionBar() {
        supportActionBar?.apply {
            setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this@MainActivity, R.color.primaryColor)))
            displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
            setCustomView(R.layout.abs_layout)
        }
    }



    private fun updateRecyclerView(musicTips: List<MusicTip>) {
        dayAdapter.updateData(musicTips)
    }


    private fun setupDayRecyclerView() {
        dayAdapter = DayAdapter(emptyList()) { musicTip ->
            val intent = Intent(this, MusicTipDetailActivity::class.java).apply {
                putExtra("MUSIC_TIP", musicTip)
            }
            startActivity(intent)
        }

        findViewById<RecyclerView>(R.id.monthDaysRecyclerView).apply {
            layoutManager = GridLayoutManager(this@MainActivity, 7)
            adapter = dayAdapter
        }
    }

}


