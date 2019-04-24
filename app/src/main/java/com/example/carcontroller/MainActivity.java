package com.example.carcontroller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.util.List;

import io.palaima.smoothbluetooth.Device;
import io.palaima.smoothbluetooth.SmoothBluetooth;

public class MainActivity extends AppCompatActivity {

    private SmoothBluetooth mSmoothBluetooth;
    private BtnMoveType[] btnsMoveType = new BtnMoveType[4];
    private class BtnMoveType {
        public Button btn;
        public String type;
        public BtnMoveType(int btn, String type) {
            this.btn = findViewById(btn);
            this.type = type;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnsMoveType[0] = new BtnMoveType(R.id.up, "u");
        btnsMoveType[1] = new BtnMoveType(R.id.down, "d");
        btnsMoveType[2] = new BtnMoveType(R.id.right, "r");
        btnsMoveType[3] = new BtnMoveType(R.id.left, "l");

        for (int i = 0; i < 4; i++) {
            addListenerToButtons(btnsMoveType[i]);
        }
        mSmoothBluetooth = new SmoothBluetooth(MainActivity.this);
        /*SmoothBluetooth.Listener mListener = */new SmoothBluetooth.Listener() {
            @Override
            public void onBluetoothNotSupported() {
                //device does not support bluetooth
                Log.d("onBluetoothNotSupported: ", "bluetooth not supported yet!");
            }

            @Override
            public void onBluetoothNotEnabled() {
                //bluetooth is disabled, probably call Intent request to enable bluetooth
                Log.d("onBluetoothNotEnabled: ", "bluetooth not enabled yet!");
            }

            @Override
            public void onConnecting(Device device) {
                //called when connecting to particular device
                Log.d("onConnecting: ", "bluetooth is connecting!");
            }

            @Override
            public void onConnected(Device device) {
                //called when connected to particular device
                Log.d("onConnected: ", "connected and ready to use");
                // here start listen to buttons clicks
            }

            @Override
            public void onDisconnected() {
                //called when disconnected from device
                Log.d("onDisconnected: ", "bluetooth disconnected for some reason!");
            }

            @Override
            public void onConnectionFailed(Device device) {
                //called when connection failed to particular device
                Log.d("onConnectionFailed: ", "bluetooth failed to connect for some reason!");
            }

            @Override
            public void onDiscoveryStarted() {
                //called when discovery is started
                Log.d("onDiscoveryStarted: ", "starting to discover devices!");
            }

            @Override
            public void onDiscoveryFinished() {
                //called when discovery is finished
                Log.d("onDiscoveryFinished: ", "discovering finished!");
            }

            @Override
            public void onNoDevicesFound() {
                //called when no devices found
                Log.d("onNoDevicesFound: ", "no device was found");
            }

            @Override
            public void onDevicesFound(final List<Device> deviceList,
                                       final SmoothBluetooth.ConnectionCallback connectionCallback) {
                //receives discovered devices list and connection callback
                //you can filter devices list and connect to specific one
                //connectionCallback.connectTo(deviceList.get(position));
                Log.d("Devices number: ", "" + deviceList.size());
            }

            @Override
            public void onDataReceived(int data ) {
                //receives all bytes
                // useless
                Log.d("onDataReceived: ", "" + data);
            }
        };

        mSmoothBluetooth.doDiscovery();

    }


    private void addListenerToButtons(final BtnMoveType clickedButton) {
        clickedButton.btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent e) {
                int action = e.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        // bluetooth sends the char which indicates the action to the car
                        Log.d("ACTION Type: ", clickedButton.type);
                        mSmoothBluetooth.send(clickedButton.type);
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.d("ACTION Type: ", "s");
                        mSmoothBluetooth.send("s");
                        break;
                }
                return false;
            }
        });
    }

}
