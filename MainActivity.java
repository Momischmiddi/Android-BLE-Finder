package com.example.morit.bluetoothlowenergy;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.Set;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Debug","Suche nach Geräten");

        this.findBluetoothDevice();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void findBluetoothDevice() {
        Toast toast;
        String toastMessage = "Das Gerät supported kein Low-Energy.";

        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(this.validateBlueToothConneciton(bluetoothAdapter))
        {
            BluetoothDevice bluetoothDevice = this.getBlueToothDevice(bluetoothAdapter);

            if(bluetoothDevice != null) {
                if(bluetoothDevice.getType() == bluetoothDevice.DEVICE_TYPE_LE) {
                    toastMessage = "Gerät Supported Low-Energy.";
                    this.getDeviceDistance();
                }
            } else {
                Log.d("Nonefound", "Nonefound");
            }
        }

        toast = Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_LONG);
        toast.show();
    }

    private void getDeviceDistance() {
        Log.d("Distance", "Getting Distance");

        /*
            Hab kein BLE um das zu testen.
            https://stackoverflow.com/questions/36399927/distance-calculation-from-rssi-ble-android
         */
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private BluetoothDevice getBlueToothDevice(BluetoothAdapter bluetoothAdapter) {
        Toast toast;
        String blueToothDeviceName = "JBL E50BT";
        String toastMessage = "Gerät '" + blueToothDeviceName + "' konnte nicht gefunden werden.";
        BluetoothDevice result = null;

        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

        if(pairedDevices.size() == 0) {
            toastMessage = "Es konnte kein Bluetooth-Gerät " +
                    "mit dem Namen '" + blueToothDeviceName + "' gefunden werden.";
            Log.e("Deviceerror","Nicht gefunden");
        } else {
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                if(deviceName.equals(blueToothDeviceName)) {
                    result = device;
                    toastMessage = "Gerät wurde gefunden.";
                }
            }
        }

        toast = Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_LONG);
        toast.show();

        return result;
    }

    private boolean validateBlueToothConneciton(BluetoothAdapter bluetoothAdapter) {
        boolean result = false;
        Toast toast;
        String toastMessage = "Suche nach Geräten.";

        if(bluetoothAdapter == null) {
            toastMessage = "Bluetooth-Antenne konnte nicht angesprochen werden.";
        } else {
            if(bluetoothAdapter.isEnabled()) {
                result = true;
            } else {
                toastMessage = "Bluetooth ist nicht aktiviert.";
            }
        }

        toast = Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_LONG);
        toast.show();

        return result;
    }
}
