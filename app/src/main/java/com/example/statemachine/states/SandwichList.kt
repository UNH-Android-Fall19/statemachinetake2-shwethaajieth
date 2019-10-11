package com.example.statemachine.states

import com.example.statemachine.actions.Actions
import com.example.statemachine.model.Burger

class SandwichList(val sandwiches: List<Burger>):
    SandwichState {
    override fun consumeAction(action: Actions): SandwichState {
        return when(action) {
            is Actions.AddSandwichClicked -> AddSandwich(sandwiches)
            else -> throw IllegalStateException("Invalid action $action passed to state $this")
        }
    }
}