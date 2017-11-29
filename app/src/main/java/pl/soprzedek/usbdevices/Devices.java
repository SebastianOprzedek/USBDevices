package pl.soprzedek.usbdevices;

import android.content.Context;
import android.graphics.Color;
import android.hardware.usb.UsbAccessory;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Devices extends AppCompatActivity {
    StartGlasses startGlasses;
    int i=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devices);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        ImageView imageView2 = (ImageView) findViewById(R.id.imageView2);
        startGlasses = new StartGlasses(imageView, imageView2);
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
        List<UsbDevice> deviceNames = getConnectedDevices();
        i++;
        startGlasses.refersh(i);
        if(deviceNames.isEmpty())
            content.setText("no devices " + new Integer(i).toString() + accessory());
        else {
            content.setText(new Integer(i).toString() + accessory());
            for (UsbDevice device : deviceNames) {
                content.append(device.getDeviceName() + "\r\n");
                content.append("\t\t\t" + "product id: " + device.getProductId() + "\r\n");
                content.append("\t\t\t" + "vendor id: " + device.getVendorId() + "\r\n");
                content.append("\t\t\t" + "device id: " + device.getDeviceId() + "\r\n");
                content.append("\t\t\t" + "DeviceName id: " + device.getDeviceName() + "\r\n");
            }
        }
    }

    public List<UsbDevice> getConnectedDevices() {
        UsbManager manager = (UsbManager) getSystemService(Context.USB_SERVICE);
        HashMap<String, UsbDevice> deviceList = manager.getDeviceList();
        Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();
        List<UsbDevice> devices = new ArrayList<>();
        while (deviceIterator.hasNext()) {
            UsbDevice device = deviceIterator.next();
            devices.add(device);
        }
        return devices;
    }

    public String accessory() {
        UsbManager um = (UsbManager) getSystemService(USB_SERVICE);
        UsbAccessory[] accessoryList = um.getAccessoryList();
        if (accessoryList != null) {
            return "=====accessoryList======" + accessoryList.length;
        }
        else{
            return " no acc ";
        }
    }
}
