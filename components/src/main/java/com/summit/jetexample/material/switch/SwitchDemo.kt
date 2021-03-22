package com.summit.jetexample.material.switch

import androidx.compose.material.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.summit.jetexample.utils.showMessage

/**
 * [EN]
 * A Switch is a two state toggleable component that provides on/off like options
 */

/**
 * [ES]
 * Un switch es un accionable de dos estados, provee una funcionalidad on/off
 */

@Composable
fun SwitchDemo() {
        val context = LocalContext.current
        val checkedState = remember { mutableStateOf(false) }
        Switch(
            checked = checkedState.value,
            onCheckedChange = {
                    checkedState.value = it
                    if(checkedState.value) showMessage(context,"Checked") else showMessage(context,"Unchecked")
            }
        )
}

@Preview(showBackground = true)
@Composable
private fun SwitchPreview(){
        SwitchDemo()
}