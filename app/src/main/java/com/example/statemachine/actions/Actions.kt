package com.example.statemachine.actions

import com.example.statemachine.model.Burger
import com.example.statemachine.model.SandwichType

sealed class Actions {
    class AddSandwichClicked: Actions()
    class SandwichTypeSelected(val type: SandwichType): Actions()
    class PredefinedSandwichSelected(val sandwich: Burger): Actions()
    class SubmitSandwichClicked(val sandwichName: String): Actions()
}