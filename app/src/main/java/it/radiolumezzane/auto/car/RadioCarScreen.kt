package it.radiolumezzane.auto.car

import android.content.Intent
import android.net.Uri
import androidx.car.app.CarContext
import androidx.car.app.Screen
import androidx.car.app.model.Action
import androidx.car.app.model.ActionStrip
import androidx.car.app.model.MessageTemplate
import androidx.car.app.model.ParkedOnlyOnClickListener
import androidx.car.app.model.Template

private const val STREAM_URL = "https://custom-azura1.asurahosting.com/listen/radio_lumezzane/radio.mp3"
private const val SITE_URL = "https://stefanovillani.it/radio-lumezzane/"

class RadioCarScreen(carContext: CarContext) : Screen(carContext) {

    override fun onGetTemplate(): Template {
        val playAction = Action.Builder()
            .setTitle("Play")
            .setOnClickListener(
                ParkedOnlyOnClickListener.create {
                    // Su Android Auto apriamo lo stream sul telefono
                    // (comportamento semplice/sicuro lato distrazione)
                    val i = Intent(Intent.ACTION_VIEW, Uri.parse(STREAM_URL))
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    carContext.startActivity(i)
                }
            )
            .build()

        val siteAction = Action.Builder()
            .setTitle("Sito")
            .setOnClickListener(
                ParkedOnlyOnClickListener.create {
                    val i = Intent(Intent.ACTION_VIEW, Uri.parse(SITE_URL))
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    carContext.startActivity(i)
                }
            )
            .build()

        return MessageTemplate.Builder("Radio Lumezzane pronta su Android Auto.")
            .setTitle("Radio Lumezzane")
            .addAction(playAction)
            .setActionStrip(
                ActionStrip.Builder()
                    .addAction(siteAction)
                    .build()
            )
            .build()
    }
}
