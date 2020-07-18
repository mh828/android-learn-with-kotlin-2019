package com.example.learningandroid01

import android.content.Intent
import android.gesture.Gesture
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.learningandroid01.ViewTest.RecyclerViewActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_recycler_view.*
import java.util.zip.Inflater

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        registerForContextMenu(findViewById(R.id.TextView))
        registerForContextMenu(findViewById(R.id.button))
        registerForContextMenu(findViewById(R.id.button2))

        LinearLayout.setOnCreateContextMenuListener { menu, v, menuInfo ->
            this.menuInflater.inflate(R.menu.context_menu_02, menu)
        }

        findViewById<Button>(R.id.button).setOnClickListener { v: View? ->
            startActivity(Intent(this, RecyclerViewActivity::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        return super.onCreateOptionsMenu(menu)

        this.menuInflater.inflate(R.menu.context_menu_01, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)

        this.menuInflater.inflate(R.menu.context_menu_01, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return super.onContextItemSelected(item)
    }
}
