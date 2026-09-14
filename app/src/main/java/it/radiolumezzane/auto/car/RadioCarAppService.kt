package it.radiolumezzane.auto.car

import androidx.car.app.CarAppService
import androidx.car.app.Session
import androidx.car.app.validation.HostValidator

class RadioCarAppService : CarAppService() {
    override fun onCreateSession(): Session = RadioCarSession()

    override fun createHostValidator(): HostValidator {
        // Per sviluppo: consenti tutti gli host.
        // In produzione sostituire con HostValidator.Builder(this)
        //    .addAllowedHosts(R.array.hosts_allowlist)
        //    .build()
        return HostValidator.ALLOW_ALL_HOSTS_VALIDATOR
    }
}
