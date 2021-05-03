package fr.isen.lebonj.delirestonew

import android.bluetooth.BluetoothDevice
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.lebonj.delirestonew.databinding.ActivityDetailBinding
import fr.isen.lebonj.delirestonew.databinding.ActivityDetailBleBinding

class DetailBleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBleBinding
    private lateinit var listDevice: BluetoothDevice


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityDetailBleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)?: error("error")
        binding.titleDevice.text = listDevice?.name
    }
}