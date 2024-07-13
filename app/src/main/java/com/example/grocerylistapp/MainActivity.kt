package com.example.grocerylistapp

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // We are initializing all the needed vars to be used in onCreate to prevent adding nullchecks later
    private lateinit var itemEditText: EditText
    private lateinit var quantitySpinner: Spinner
    private lateinit var addItemButton: Button
    private lateinit var groceryListView: ListView
    private lateinit var totalItemsTextView: TextView

    private val groceryItems = mutableListOf<Pair<String, String>>()
    private lateinit var adapter: GroceryAdapter

    private var isItemValid = false
    private var isQuantityValid = false

    /*
     * The onCreate method is called on creation of.
     * Here we do the starting setup
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        itemEditText = findViewById(R.id.itemEditText)
        quantitySpinner = findViewById(R.id.quantitySpinner)
        addItemButton = findViewById(R.id.addItemButton)
        groceryListView = findViewById(R.id.groceryListView)
        totalItemsTextView = findViewById(R.id.totalItemsTextView)

        // Values to be displayed in spinner
        val quantities = arrayOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10")

        // quantityAdapter is used to provide data to the Spinner.
        val quantityAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, quantities)
        quantityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        quantitySpinner.adapter = quantityAdapter

        adapter = GroceryAdapter(this, groceryItems)
        groceryListView.adapter = adapter

        quantitySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedQuantity = parent.getItemAtPosition(position).toString().toInt()
                isQuantityValid = selectedQuantity != 0
                updateAddButtonState()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        // TextWatcher to monitor text changes in itemEditText
        itemEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                isItemValid = !s.isNullOrEmpty()
                updateAddButtonState()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        // Listener for add button
        addItemButton.setOnClickListener {
            val item = itemEditText.text.toString()
            val quantity = quantitySpinner.selectedItem.toString()

            // Once we have added the item, the spinner and edit text is
            // reset and button is disabled
            if (item.isNotEmpty()) {
                groceryItems.add(Pair(item, quantity))
                adapter.notifyDataSetChanged()
                itemEditText.text.clear()
                quantitySpinner.setSelection(0)
                isItemValid = false
                isQuantityValid = false
                updateAddButtonState()
                updateTotalItems()
            }
        }
    }

    // Update the state of the add button based on item and quantity validity
    private fun updateAddButtonState() {
        addItemButton.isEnabled = isItemValid && isQuantityValid
    }

    // We have a variable in the app which counts the number of items added. This updates it.
    private fun updateTotalItems() {
        totalItemsTextView.text = getString(R.string.total_items, groceryItems.size)
    }

    /**
     * Custom adapter to manage the display of grocery items in the ListView.
     * We are trying to display and icon, item name and item quantity.
     * This adapter helps in controlling all of these better.
     */
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

