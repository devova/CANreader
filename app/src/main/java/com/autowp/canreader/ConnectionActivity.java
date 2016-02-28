package com.autowp.canreader;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.autowp.can.CanClient;
import com.autowp.can.adapter.android.CanHackerFelhr;
import com.autowp.can.adapter.android.CanHackerFelhrException;

import java.util.ArrayList;
import java.util.List;

public class ConnectionActivity extends Activity {

    private static final String ACTION_USB_PERMISSION =
            "com.android.example.USB_PERMISSION";
    private static final String PREFENCES_BAUDRATE = "baudrate";
    private static final String PREFENCES_VID = "vid";
    private static final String PREFENCES_PID = "pid";

    private UsbBroadcastReceiver mUsbReceiver;
    private UsbDeviceSpinnerAdapter mSpinnerAdapter;
    private ArrayList<UsbDevice> mUsbDeviceList = new ArrayList<>();
    private Spinner mSpinnerDevice;
    private UsbManager mUsbManager;
    private Spinner mSpinnerBaudrate;

    private class Baudrate {
        private int value;
        private String name;

        public Baudrate(int value, String name) {
            this.value = value;
            this.name = name;
        }

        public int getValue() {
            return value;
        }

        public String getName()
        {
            return name;
        }

        public String toString()
        {
            return name;
        }
    }

    private final class UsbBroadcastReceiver extends BroadcastReceiver {

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_USB_PERMISSION.equals(action)) {
                synchronized (this) {
                    UsbDevice device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);

                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        if (device != null) {
                            connectDevice(device);
                            refreshButtonsState();
                        }
                    }

                }
            }

            if (UsbManager.ACTION_USB_DEVICE_ATTACHED.equals(action)) {
                synchronized (this) {
                    //UsbDevice device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);

                    fillUsbDeviceList();
                    refreshButtonsState();
                }
            }

            if (UsbManager.ACTION_USB_DEVICE_DETACHED.equals(action)) {
                synchronized (this) {
                    //UsbDevice device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);

                    fillUsbDeviceList();
                    refreshButtonsState();
                }
            }
        }
    }

    private CanReaderService canReaderService;

    ServiceConnection serviceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName name, IBinder binder) {
            canReaderService = ((CanReaderService.TransferServiceBinder) binder).getService();
            bound = true;

            canReaderService.addListener(new CanReaderService.OnConnectedStateChangeListener() {
                @Override
                public void handleConnectedStateChanged(CanClient.ConnectionState connection) {
                    refreshButtonsState();
                }
            });
        }

        public void onServiceDisconnected(ComponentName name) {

            bound = false;
        }
    };
    boolean bound = false;

    private void connectDevice(UsbDevice device) {
        try {
            CanHackerFelhr canClient = new CanHackerFelhr(mUsbManager, device);
            Baudrate baudrate = (Baudrate)mSpinnerBaudrate.getSelectedItem();
            canReaderService.setSpeed(baudrate.getValue());
            canReaderService.setCanAdapter(canClient);
            refreshButtonsState();
        } catch (CanHackerFelhrException e) {
            e.printStackTrace();
        }

        /*try {
            Elm327Felhr canClient = new Elm327Felhr(mUsbManager, device);
            Baudrate baudrate = (Baudrate)mSpinnerBaudrate.getSelectedItem();
            canReaderService.setSpeed(baudrate.getValue());
            canReaderService.setCanAdapter(canClient);
            refreshButtonsState();
        } catch (Elm327FelhrException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);

        mUsbManager = (UsbManager) getSystemService(Context.USB_SERVICE);

        final SharedPreferences mPrefences = getPreferences(MODE_PRIVATE);

        mSpinnerDevice = (Spinner) findViewById(R.id.spinner);

        mSpinnerDevice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                refreshButtonsState();
                SharedPreferences.Editor ed = mPrefences.edit();
                UsbDevice device = (UsbDevice) mSpinnerDevice.getSelectedItem();
                ed.putInt(PREFENCES_VID, device.getVendorId());
                ed.putInt(PREFENCES_PID, device.getProductId());
                ed.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                refreshButtonsState();
            }
        });

        mSpinnerAdapter = new UsbDeviceSpinnerAdapter(this, R.layout.usbdevice_spinner_item, mUsbDeviceList);
        mSpinnerAdapter.setDropDownViewResource(R.layout.usbdevice_spinner_item);

        mSpinnerBaudrate = (Spinner) findViewById(R.id.spinnerBaudrate);

        List<Baudrate> baudrates = new ArrayList<>();
        baudrates.add(new Baudrate(10, "10 Kbit/s"));
        baudrates.add(new Baudrate(20, "20 Kbit/s"));
        baudrates.add(new Baudrate(50, "50 Kbit/s"));
        baudrates.add(new Baudrate(100, "100 Kbit/s"));
        baudrates.add(new Baudrate(125, "125 Kbit/s"));
        baudrates.add(new Baudrate(250, "250 Kbit/s"));
        baudrates.add(new Baudrate(500, "500 Kbit/s"));
        baudrates.add(new Baudrate(1000, "1 Mbit/s"));

        ArrayAdapter<Baudrate> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, baudrates);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerBaudrate.setAdapter(adapter);

        mSpinnerBaudrate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                SharedPreferences.Editor ed = mPrefences.edit();
                ed.putInt(PREFENCES_BAUDRATE, position);
                ed.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                refreshButtonsState();
            }
        });



        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_USB_PERMISSION);
        filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
        mUsbReceiver = new UsbBroadcastReceiver();
        registerReceiver(mUsbReceiver, filter);

        Button buttonDisconnect = (Button) findViewById(R.id.buttonDisconnect);
        buttonDisconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                canReaderService.setCanAdapter(null);
                refreshButtonsState();
            }
        });

        Button buttonConnect = (Button) findViewById(R.id.buttonConnect);
        buttonConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UsbDevice device = (UsbDevice) mSpinnerDevice.getSelectedItem();

                if (!mUsbManager.hasPermission(device)) {
                    Intent intent = new Intent(ACTION_USB_PERMISSION);
                    PendingIntent pendindIntent = PendingIntent.getBroadcast(ConnectionActivity.this, 0, intent, 0);
                    mUsbManager.requestPermission(device, pendindIntent);
                } else {
                    connectDevice(device);
                }


            }
        });
    }

    private void refreshButtonsState() {
        final Button btnConnect = (Button) findViewById(R.id.buttonConnect);
        final Button btnDisconnect = (Button) findViewById(R.id.buttonDisconnect);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.connection_progressbar);

        UsbDevice selectedDevice = (UsbDevice) mSpinnerDevice.getSelectedItem();

        CanClient.ConnectionState connection = CanClient.ConnectionState.DISCONNECTED;
        if (canReaderService != null) {
            connection = canReaderService.getConnectionState();
        }

        final boolean connectEnabled = bound && (connection == CanClient.ConnectionState.DISCONNECTED) && selectedDevice != null;
        final boolean disconnectEnabled = bound && (connection == CanClient.ConnectionState.CONNECTED);
        final boolean progressBarVisible = bound && (connection == CanClient.ConnectionState.CONNECTING || connection == CanClient.ConnectionState.DISCONNECTING);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                btnConnect.setEnabled(connectEnabled);
                btnDisconnect.setEnabled(disconnectEnabled);
                progressBar.setVisibility(progressBarVisible ? View.VISIBLE : View.GONE);
            }
        });
    }

    private void fillUsbDeviceList() {
        mUsbDeviceList.clear();
        for (final UsbDevice usbDevice : mUsbManager.getDeviceList().values()) {
            mUsbDeviceList.add(usbDevice);
        }

        mSpinnerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();

        Intent intent = new Intent(this, CanReaderService.class);
        bindService(intent, serviceConnection, AppCompatActivity.BIND_AUTO_CREATE);

        fillUsbDeviceList();

        mSpinnerDevice.setAdapter(mSpinnerAdapter);

        final SharedPreferences mPrefences = getPreferences(MODE_PRIVATE);

        int lastVid = mPrefences.getInt(PREFENCES_VID, 0);
        int lastPid = mPrefences.getInt(PREFENCES_PID, 0);

        for (UsbDevice device : mUsbDeviceList) {
            boolean match = device.getVendorId() == lastVid && device.getProductId() == lastPid;
            if (match) {
                int position = mSpinnerAdapter.getPosition(device);
                mSpinnerDevice.setSelection(position);
                break;
            }
        }

        int position = mPrefences.getInt(PREFENCES_BAUDRATE, 0);
        mSpinnerBaudrate.setSelection(position);

        refreshButtonsState();
    }

    @Override
    public void onPause() {
        super.onPause();

        if (bound) {
            unbindService(serviceConnection);
            bound = false;
        }
    }

    @Override
    protected void onStop() {
        if (mUsbReceiver != null) {
            unregisterReceiver(mUsbReceiver);
            mUsbReceiver = null;
        }
        super.onStop();
    }
}
