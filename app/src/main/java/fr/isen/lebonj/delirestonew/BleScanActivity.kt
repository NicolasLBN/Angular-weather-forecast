package fr.isen.lebonj.delirestonew

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.lebonj.delirestonew.databinding.ActivityBleScanBinding


class BleScanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBleScanBinding
    private var isScanning = false
    private var bluetoothAdapter: BluetoothAdapter? = null

   private val SCAN_PERIOD: Long = 10000
   private var bluetoothLeScanner: BluetoothLeScanner? = null

   private val handler = Handler()

   private var leDeviceListAdapter: BleScanAdapter? = null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBleScanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Initialisation du Bluetooth adapter.
        bluetoothAdapter = getSystemService(BluetoothManager::class.java)?.adapter
        startBLEIfPossible()
        isDeviceHasBLESupport()
        binding.bleScanPlayPauseAction.setOnClickListener {
            togglePlayPauseAction()
            isDeviceHasBLESupport()
     }
        binding.bleScanTitle.setOnClickListener() {
            togglePlayPauseAction()
        }
    }
    private fun startBLEIfPossible() {
        when {
            !isDeviceHasBLESupport() || bluetoothAdapter == null -> {
                Toast.makeText(this, "pas dispo", Toast.LENGTH_SHORT)
                    .show()
            }
            !(bluetoothAdapter?.isEnabled ?: false) -> {
                //je dois activer le bluethooth
                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
            }
            ActivityCompat.checkSelfPermission
                (
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED -> {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_PERMISSION_LOCATION
                )
            }
            else -> {
                bluetoothLeScanner = bluetoothAdapter?.bluetoothLeScanner
                initRecyclerDevice()
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_ENABLE_BT && resultCode == RESULT_OK) {
            startBLEIfPossible()
        }
    }
    private fun isDeviceHasBLESupport(): Boolean {
        // Use this check to determine whether BLE is supported on the device. Then
        // you can selectively disable BLE-related features.
        if (!packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "Cet appareil n'est pas compatible, sorry", Toast.LENGTH_SHORT)
                .show()
            //finish()
        }
        return packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)
    }
    private fun togglePlayPauseAction() {
        if (!isScanning) {
            binding.bleScanTitle.text = getString(R.string.BleScanPlay)
            binding.loadingProgress.isVisible = true
            binding.divider.isVisible = false
            scanLeDevice()
        } else {
            binding.bleScanTitle.text = getString(R.string.BleScanPause)
            binding.loadingProgress.isVisible = false
            binding.divider.isVisible = true
        }
    }
    companion object {
        const val REQUEST_ENABLE_BT = 22
        const val REQUEST_PERMISSION_LOCATION = 22
    }
    // Stops scanning after 10 seconds.

    private fun scanLeDevice() {
        bluetoothLeScanner?.let { scanner ->
            Log.d("scanActivity","je passe dans le $isScanning")
            if (!isScanning) { // Stops scanning after a pre-defined scan period.

                handler.postDelayed({
                    isScanning = false
                    scanner.stopScan(leScanCallback)
                }, SCAN_PERIOD)
                isScanning = true
                Log.d("scanActivity","je passe dans le scandevice")
                scanner.startScan(leScanCallback)
            } else {
                isScanning = false
                scanner.stopScan(leScanCallback)
            }
        }
    }

    private fun initRecyclerDevice() {
        binding.deviceList.layoutManager = LinearLayoutManager(this)
        leDeviceListAdapter = BleScanAdapter(mutableListOf()) {
            val intent = Intent(this, DetailBleActivity::class.java)
            intent.putExtra(BluetoothDevice.EXTRA_DEVICE, it.device)
            startActivity(intent)
        }
        binding.deviceList.adapter = leDeviceListAdapter
    }

    private val leScanCallback: ScanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            super.onScanResult(callbackType, result)
            leDeviceListAdapter?.addDevice(result)
            Log.d("scanActivity","je passe par ici")
            leDeviceListAdapter?.notifyDataSetChanged()
        }
    }
}