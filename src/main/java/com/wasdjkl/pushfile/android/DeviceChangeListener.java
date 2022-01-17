package com.wasdjkl.pushfile.android;

import com.android.ddmlib.AndroidDebugBridge;
import com.android.ddmlib.IDevice;

/**
 * @author wasdjkl
 */
public class DeviceChangeListener implements AndroidDebugBridge.IDeviceChangeListener {

    public boolean isConnected = false;
    public IDevice defaultDevice = null;

    @Override
    public void deviceConnected(IDevice device) {
        isConnected = true;
        defaultDevice = device;
    }

    @Override
    public void deviceDisconnected(IDevice device) {
        isConnected = false;
    }

    @Override
    public void deviceChanged(IDevice device, int changeMask) {

    }
}
