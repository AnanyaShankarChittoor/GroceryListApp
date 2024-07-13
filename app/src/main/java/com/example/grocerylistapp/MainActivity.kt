package com.example.grocerylistapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var itemEditText: EditText
    private lateinit var quantitySpinner: Spinner
    private lateinit var addItemButton: Button
    private lateinit var groceryListView: ListView
    private lateinit var totalItemsTextView: TextView

    private val groceryItems = mutableListOf<Pair<String, String>>()
    private lateinit var adapter: GroceryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        itemEditText = findViewById(R.id.itemEditText)
        quantitySpinner = findViewById(R.id.quantitySpinner)
        addItemButton = findViewById(R.id.addItemButton)
        groceryListView = findViewById(R.id.groceryListView)
        totalItemsTextView = findViewById(R.id.totalItemsTextView)

        val quantities = arrayOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
        val quantityAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, quantities)
        quantityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        quantitySpinner.adapter = quantityAdapter

        adapter = GroceryAdapter(this, groceryItems)
        groceryListView.adapter = adapter

        quantitySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedQuantity = parent.getItemAtPosition(position).toString().toInt()
                addItemButton.isEnabled = selectedQuantity != 0
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        addItemButton.setOnClickListener {
            val item = itemEditText.text.toString()
            val quantity = quantitySpinner.selectedItem.toString()

            if (item.isNotEmpty()) {
                groceryItems.add(Pair(item, quantity))
                adapter.notifyDataSetChanged()
                itemEditText.text.clear()
                quantitySpinner.setSelection(0)
                updateTotalItems()
            }
        }

        updateTotalItems()
    }

    private fun updateTotalItems() {
        totalItemsTextView.text = getString(R.string.total_items, groceryItems.size)
    }

    inner class GroceryAdapter(context: Context, private val items: MutableList<Pair<String, String>>) :
        ArrayAdapter<Pair<String, String>>(context, 0, items) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val (item, quantity) = items[position]
            val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)

            val itemTextView = view.findViewById<TextView>(R.id.itemTextView)
            val quantityTextView = view.findViewById<TextView>(R.id.quantityTextView)
            val deleteButton = view.findViewById<Button>(R.id.deleteButton)

            itemTextView.text = item
            quantityTextView.text = quantity

            deleteButton.setOnClickListener {
                items.removeAt(position)
                notifyDataSetChanged()
                updateTotalItems()
            }

            return view
        }
    }
}
