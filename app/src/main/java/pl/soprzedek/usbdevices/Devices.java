package pl.soprzedek.usbdevices;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Devices extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devices);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refresh();
            }
        });
        refresh();
    }

    private void refresh() {
        TextView content = (TextView) findViewById(R.id.content);
        List<String> deviceNames = getConnectedDevicesNames();
        if(deviceNames.isEmpty())
            content.setText("no devices");
        else {
            content.setText("");
            for (String name : deviceNames)
                content.append(name + "\r\n");
        }
    }

    public List<String> getConnectedDevicesNames() {
//        Context context = getApplicationContext();
//        String actionString = context.getPackageName() + ".action.USB_PERMISSION";
//        PendingIntent mPermissionIntent = PendingIntent.getBroadcast(context, 0, new
//                Intent(actionString), 0);

        UsbManager manager = (UsbManager) getSystemService(Context.USB_SERVICE);
        HashMap<String, UsbDevice> deviceList = manager.getDeviceList();
        Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();
        List<String> deviceNames = new ArrayList<>();
        while (deviceIterator.hasNext()) {
            UsbDevice device = deviceIterator.next();
//            manager.requestPermission(device, mPermissionIntent);
            deviceNames.add(device.getDeviceName());
        }
        return deviceNames;
    }
}
