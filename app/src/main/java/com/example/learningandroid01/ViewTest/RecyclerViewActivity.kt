package com.example.learningandroid01.ViewTest

import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.learningandroid01.R
import com.google.android.material.snackbar.Snackbar

class RecyclerViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        val layoutmanager = LinearLayoutManager(this)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val myAdapter = MyAdapter(mutableListOf("شماره یک", "شماره دو", "شماره سه"), this)
        recyclerView.apply {
            setHasFixedSize(false)
            layoutManager = layoutmanager
            adapter = myAdapter
        }

        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(myAdapter))
        itemTouchHelper.attachToRecyclerView(recyclerView)


    }
}

class MyAdapter(private val myDataset: MutableList<String>, private val context: Context) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    private var deletedItemPosition: Int = -1
    private var deletedItem: String = ""

    class MyViewHolder(val linearLayout: LinearLayout) : RecyclerView.ViewHolder(linearLayout)


    override fun getItemCount() = myDataset.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.linearLayout.findViewById<TextView>(R.id.textView).text = (myDataset[position])
        holder.linearLayout.findViewById<Button>(R.id.button).text = (myDataset[position])

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val tv =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_view, parent, false)
                    as LinearLayout

        return MyViewHolder(tv)
    }

    fun delete(position: Int) {
        deletedItemPosition = position
        deletedItem = myDataset[position]

        myDataset.removeAt(position)

        notifyItemRemoved(position)
        showUndoSnackbar()
    }

    private fun showUndoSnackbar() {
        val activity = this.context as Activity
        val layoutview = activity.findViewById<LinearLayout>(R.id.linearLayout)
        Snackbar.make(layoutview, deletedItem, Snackbar.LENGTH_LONG).apply {
            setAction("بازگشت undo", View.OnClickListener { v: View ->
                Toast.makeText(this.context, deletedItem, Toast.LENGTH_LONG).show()
                if (deletedItemPosition > -1) {
                    myDataset.add(deletedItemPosition, deletedItem)
                    notifyItemInserted(deletedItemPosition)
                    deletedItemPosition = -1
                }
            })
        }.show()
    }

    fun getValueAt(position: Int): String {
        return myDataset[position]
    }

    fun changePositions(start: Int, end: Int) {
        val itemStart = myDataset[start]

        myDataset.removeAt(start)
        myDataset.add(end, itemStart)
        notifyItemMoved(start,end)
    }
}


class SwipeToDeleteCallback() :
    ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.UP or ItemTouchHelper.DOWN,
        ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
    ) {

    lateinit var adapter: MyAdapter

    constructor(adapter: MyAdapter) : this() {
        this.adapter = adapter
    }


    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        val startPosition = viewHolder.adapterPosition
        val endPosition = target.adapterPosition

        if (startPosition > -1 && endPosition > -1) {
            adapter.changePositions(startPosition, endPosition)
            return true
        }

        Log.e("Move", "moved from: $startPosition TO :$endPosition")


        return true
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)

    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition;
        adapter.delete(position)
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

        val itemView: View = viewHolder.itemView
        val backgroundCornerOffset = 20
    }
}

