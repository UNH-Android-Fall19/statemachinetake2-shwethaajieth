package com.example.statemachine.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.statemachine.R
import com.example.statemachine.model.SandwichType
import com.example.statemachine.actions.Actions
import com.example.statemachine.model.Burger
import com.example.statemachine.states.*
import kotlinx.android.synthetic.main.activity_sandwich.*
import kotlin.properties.Delegates

class SandwichActivity : AppCompatActivity() {

    private val predefinedSandwiches: MutableList<Burger> = mutableListOf()

    var currentState by Delegates.observable<SandwichState>(
        SandwichList(
            emptyList()
        ), { _, old, new ->
        renderViewState(new, old)
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        buildPredefinedSandwiches()
        setContentView(R.layout.activity_sandwich)
        showSandwichList(emptyList())
    }

    private fun buildPredefinedSandwiches() {
        predefinedSandwiches.add(Burger("Meatball", SandwichType.SINGLE))
        predefinedSandwiches.add(Burger("Greek", SandwichType.DOUBLE))
        predefinedSandwiches.add(Burger("Italian", SandwichType.SINGLE))
        predefinedSandwiches.add(Burger("Caesar", SandwichType.DOUBLE))
        predefinedSandwiches.add(Burger("BLT", SandwichType.SINGLE))
        predefinedSandwiches.add(Burger("Chicken", SandwichType.SINGLE))
    }

    private fun renderViewState(newState: SandwichState, oldState: SandwichState) {
        when (newState) {
            is SandwichList -> showSandwichList(newState.sandwiches)
            is AddSandwich -> showAddSandwichView(predefinedSandwiches)
            is NameSandwich -> showSandwichInputFields()
        }

        when (oldState) {
            is SandwichList -> hideSandwichList()
            is AddSandwich -> hideAddSandwichView()
            is NameSandwich -> hideSandwichInputFields()
        }
    }

    private fun showSandwichList(sandwiches: List<Burger>) {
        sandwich_list_container.visibility = View.VISIBLE
        favorite_sandwiches_listview.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, sandwiches)
        add_sandwich_button.setOnClickListener {
            currentState = currentState.consumeAction(Actions.AddSandwichClicked())
        }
    }

    private fun hideSandwichList() {
        sandwich_list_container.visibility = View.GONE
    }

    private fun showAddSandwichView(predefinedSandwiches: List<Burger>) {
        add_sandwich_container.visibility = View.VISIBLE
        wrap_button.setOnClickListener {
            currentState = currentState.consumeAction(Actions.SandwichTypeSelected(SandwichType.DOUBLE))
        }
        grinder_button.setOnClickListener {
            currentState = currentState.consumeAction(Actions.SandwichTypeSelected(SandwichType.SINGLE))
        }
        predefined_sandwiches_listview.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, predefinedSandwiches)
        predefined_sandwiches_listview.setOnItemClickListener { _, _, position, _ ->
            val selectedSandwich = predefinedSandwiches[position]
            currentState = currentState.consumeAction(Actions.PredefinedSandwichSelected(selectedSandwich))
        }
    }

    private fun hideAddSandwichView() {
        add_sandwich_container.visibility = View.GONE
    }

    private fun showSandwichInputFields() {
        sandwich_inputs_container.visibility = View.VISIBLE
        submit_button.setOnClickListener {
            val sandwichName = sandwich_name.text.toString()
            sandwich_name.text.clear()
            currentState = currentState.consumeAction(Actions.SubmitSandwichClicked(sandwichName))
        }
    }

    private fun hideSandwichInputFields() {
        sandwich_inputs_container.visibility = View.GONE
    }

}