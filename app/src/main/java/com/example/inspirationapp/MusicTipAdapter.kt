package com.example.inspirationapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.inspirationapp.databinding.ActivityMusicTipDetailBinding
import kotlinx.parcelize.Parcelize
import android.graphics.drawable.ColorDrawable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.widget.Toast
import androidx.core.content.ContextCompat
import android.view.inputmethod.InputMethodManager
import android.view.View
import android.content.res.Configuration
import android.text.TextPaint
import android.widget.ScrollView

@Parcelize
data class MusicTip(
    val id: Int,
    val song: String,
    val artist: String,
    val description: String,
    val imageResourceId: Int
) : Parcelable



class MusicTipDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMusicTipDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_music_tip_detail_landscape)
        } else {
            binding = ActivityMusicTipDetailBinding.inflate(layoutInflater)
            setContentView(binding.root)
        }

        supportActionBar?.apply {
            setBackgroundDrawable(
                ColorDrawable(
                    ContextCompat.getColor(
                        this@MusicTipDetailActivity,
                        R.color.primaryDarkColor
                    )
                )
            )

        }

        val musicTip = intent.getParcelableExtra<MusicTip>("MUSIC_TIP")

        musicTip?.let {
            with(binding) {
                titleTextView.text = it.song
                artistTextView.text = it.artist
                descriptionTextView.text = it.description
                imageView.setImageResource(it.imageResourceId)
            }
        }

        displayComments(musicTip?.id ?: -1)


        val scrollView = findViewById<ScrollView>(R.id.scrollView)
        binding.scrollView.setOnTouchListener { v, event ->
            hideKeyboard(v)
            return@setOnTouchListener false
        }


        binding.submitCommentButton.setOnClickListener {
            val commentText = binding.commentEditText.text.toString()
            val musicTipId =
                musicTip?.id ?: -1
            if (musicTipId != -1) {
                submitComment(musicTipId, commentText)
                binding.commentEditText.text.clear()
            } else {

                Toast.makeText(this, "Error: Music tip not found.", Toast.LENGTH_SHORT).show()
            }
        }



    }

    private fun displayComments(musicTipId: Int) {
        val sharedPref = this.getSharedPreferences("MusicTipComments", Context.MODE_PRIVATE)
        val comments = sharedPref.getString("comment_$musicTipId", "")

        val commentSpannable = SpannableStringBuilder()

        val commentLines = comments?.split("\n") ?: emptyList()

        val deletableCommentColor = ContextCompat.getColor(this, R.color.primaryLightColor)

        for ((index, commentLine) in commentLines.withIndex()) {
            val commentSpan = SpannableString(commentLine)

            val deleteAction = object : ClickableSpan() {
                override fun onClick(widget: View) {
                    deleteComment(musicTipId, index)
                }

                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.color = deletableCommentColor
                    ds.isUnderlineText = false
                }
            }

            commentSpan.setSpan(
                deleteAction,
                0,
                commentSpan.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            commentSpannable.append(commentSpan)

            if (index < commentLines.size - 1) {
                commentSpannable.append("\n")
            }
        }

        binding.commentTextView.text = commentSpannable

        binding.commentTextView.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun deleteComment(musicTipId: Int, commentIndex: Int) {
        val sharedPref = this.getSharedPreferences("MusicTipComments", Context.MODE_PRIVATE)
        val existingComments = sharedPref.getString("comment_$musicTipId", "")

        val commentLines = existingComments?.split("\n")?.toMutableList() ?: mutableListOf()

        if (commentIndex in 0 until commentLines.size) {
            commentLines.removeAt(commentIndex)
        }

        val updatedComments = commentLines.joinToString("\n")

        with(sharedPref.edit()) {
            putString("comment_$musicTipId", updatedComments)
            apply()
        }

        displayComments(musicTipId)
    }

    private fun hideKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_music_tip_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
            R.id.action_share -> shareMusicTip()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun shareMusicTip() {
        val musicTip = intent.getParcelableExtra<MusicTip>("MUSIC_TIP")
        musicTip?.let {
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "${it.song} by ${it.artist}\n${it.description}")
                type = "text/plain"
            }
            startActivity(Intent.createChooser(shareIntent, getString(R.string.share_music_tip)))
        }
    }
    private fun submitComment(musicTipId: Int, comment: String) {
        if (comment.isNotEmpty()) {
            val sharedPref = this.getSharedPreferences("MusicTipComments", Context.MODE_PRIVATE)
            val existingComments = sharedPref.getString("comment_$musicTipId", "")

            val combinedComments = if (existingComments.isNullOrEmpty()) {
                comment
            } else {
                "$existingComments\n$comment"
            }

            with(sharedPref.edit()) {
                putString("comment_$musicTipId", combinedComments)
                apply()
            }

            displayComments(musicTipId)
            binding.commentEditText.text.clear()
            hideKeyboard(binding.commentEditText)
            Toast.makeText(this, "Comment saved", Toast.LENGTH_SHORT).show()
        }
    }
}