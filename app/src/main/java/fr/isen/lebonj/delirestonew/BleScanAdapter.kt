package fr.isen.lebonj.delirestonew

import android.bluetooth.BluetoothDevice
import android.bluetooth.le.ScanResult
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import fr.isen.lebonj.delirestonew.databinding.ActivityBleScanBinding

class BleScanAdapter(private val listBLE: MutableList<ScanResult>,
                     private val clickListener: (ScanResult) -> Unit) : RecyclerView.Adapter<BleScanAdapter.DeviceViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DeviceViewHolder {
        val binding = ActivityBleScanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DeviceViewHolder(binding.root)
    }

    class DeviceViewHolder(binding: ConstraintLayout) : RecyclerView.ViewHolder(binding) {
        //val titleDevice: TextView = binding.titleDevice

        val layout = binding.findViewById<View>(R.id.cellDeviceLayout)

    }


    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        //holder.titleDevice.text = listBLE[position].device.toString()
        holder.layout.setOnClickListener {
            clickListener.invoke(listBLE[position])
        }

    }

    fun addDevice(AppareilData: ScanResult) {
        if (!listBLE.contains(AppareilData)) {
            listBLE.add(AppareilData)
        }
    }

    override fun getItemCount(): Int = listBLE.size
}