package com.example.statemachine.states

import com.example.statemachine.model.SandwichType
import com.example.statemachine.actions.Actions
import com.example.statemachine.model.Burger

class NameSandwich(private val sandwiches: List<Burger>, private val newSandwichType: SandwichType):
    SandwichState {
    override fun consumeAction(action: Actions): SandwichState {
        return when (action) {
            is Actions.SubmitSandwichClicked -> {
                val newSandwich = Burger(action.sandwichName, newSandwichType)
                SandwichList(sandwiches + newSandwich)
            }
            else -> throw IllegalStateException("Invalid action $action passed to state $this")
        }
    }
}