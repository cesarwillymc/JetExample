package com.summit.jetexample.material.drawerlayout

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch

/**
 * [EN]
 * Navigation drawers provide access to destinations in your app.
 *
 * Modal navigation drawers block interaction with the rest of an app’s content with a scrim.
 * They are elevated above most of the app’s UI and don’t affect the screen’s layout grid.
 *
 */

/**
 * [ES]
 * Navigation drawers proveen acceso a distintos destinos en tu app.
 *
 * Un modal navigation drawer bloquea la interaccion con el resto del contenido de la app con un scrim.
 * Son elevados arriba de la UI principal y no afecta a la layout en pantalla
 */

@Composable
fun ModalDrawerDemo() {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    ModalDrawer(
            drawerState = drawerState,
            drawerContent = {
                Column {
                    Button(onClick = { coroutineScope.launch { drawerState.close() } }) {
                        Text("Close drawer")
                    }
                }
            },
            content = {
                Column {
                    Button(onClick = { coroutineScope.launch { drawerState.open() } }) {
                        Text("Open drawer")
                    }
                }
            }
    )
}

@Preview(showBackground = true)
@Composable
private fun ModalDrawerDemoPreview(){
    ModalDrawerDemo()
}