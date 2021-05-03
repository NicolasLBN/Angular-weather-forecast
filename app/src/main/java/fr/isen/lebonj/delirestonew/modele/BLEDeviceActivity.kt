package fr.isen.lebonj.delirestonew.modele

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattCharacteristic
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.lebonj.delirestonew.R
import fr.isen.lebonj.delirestonew.databinding.ActivityBLEDeviceBinding
import fr.isen.lebonj.delirestonew.databinding.ActivityBleScanBinding

class BLEDeviceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ActivityBLEDeviceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val device = intent.getParcelableExtra<BluetoothDevice>("ble_device")
        binding.deviceName.text = device?.name?:"Appareil inconnu"
        binding.deviceStatus.text = getString(R.string.ble_device_status, getString(R.string.ble_device_status_connecting))

        connectToDevice(device)
    }

    private fun connectToDevice(device: BluetoothDevice?)
    {
        var bluetoothGatt = device?.connectGatt(this, false, object : BluetoothGattCallback() {
            fun OnConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {

                super.onConnectionStateChange(gatt, status, newState)
                onConnectionStateChange(newState, gatt)

            }

            override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
                super.onServicesDiscovered(gatt, status)

            }

            override fun onCharacteristicRead(
                gatt: BluetoothGatt?,
                characteristic: BluetoothGattCharacteristic?,
                status: Int
            ) {
                super.onCharacteristicRead(gatt, characteristic, status)
            }


        })

    }

    private fun onConnectionStateChange(newState: Int, gatt: BluetoothGatt?)
    {
        BLEConnexionState.getBLEConnexionStateFromState(newState)?.let {
            runOnUiThread{

            }
                //binding.deviceStatus.text = getString(R.string.ble_device_status, getString(it.text))           }
        }

    }
}