package com.botanipal.botanipal.ui.chat.forums

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import com.botanipal.botanipal.R
import com.botanipal.botanipal.adapter.FirebaseMessageAdapter
import com.botanipal.botanipal.data.Message
import com.botanipal.botanipal.databinding.ActivityForumsBinding
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.Firebase
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database
import java.util.Date

class ForumsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForumsBinding
    private lateinit var forumDB: FirebaseDatabase
    private lateinit var adapter: FirebaseMessageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForumsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        forumDB = Firebase.database
        val messageref = forumDB.reference.child(MESSAGES_CHILD)

        binding.sendButton.setOnClickListener {
            val friendlyMessage = Message(
                binding.etMessage.text.toString(),
                "BotaniPal",
                null,
                Date().time,
                false
            )

            messageref.push().setValue(friendlyMessage) { error, _ ->
                if (error != null) {
                    Toast.makeText(this, "Failed to send message" + error.message, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Message sent", Toast.LENGTH_SHORT).show()
                }

            }
            binding.etMessage.setText("")
        }

        val manager = LinearLayoutManager(this)
        manager.stackFromEnd = true
        binding.messageRecyclerView.layoutManager = manager

        val options = FirebaseRecyclerOptions.Builder<Message>()
            .setQuery(messageref, Message::class.java)
            .build()
        adapter = FirebaseMessageAdapter(options, "BotaniPal")
        binding.messageRecyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.forum_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.forumBookmark -> {
                // Handle bookmark
                true
            }
            else -> super.onOptionsItemSelected(menuItem)
        }

    }

    public override fun onResume() {
        super.onResume()
        adapter.startListening()
    }
    public override fun onPause() {
        adapter.stopListening()
        super.onPause()
    }

    companion object {
        const val MESSAGES_CHILD = "messages"
    }
}