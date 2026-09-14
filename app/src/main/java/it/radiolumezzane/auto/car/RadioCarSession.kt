package it.radiolumezzane.auto.car

import androidx.car.app.Screen
import androidx.car.app.Session
import androidx.car.app.CarContext

class RadioCarSession : Session() {
    override fun onCreateScreen(intent: android.content.Intent): Screen {
        return RadioCarScreen(carContext)
    }
}
