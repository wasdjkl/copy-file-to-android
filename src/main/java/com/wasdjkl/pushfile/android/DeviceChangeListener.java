package com.wasdjkl.pushfile.android;

import com.android.ddmlib.AndroidDebugBridge;
import com.android.ddmlib.IDevice;

/**
 * @author wasdjkl
 */
public class DeviceChangeListener implements AndroidDebugBridge.IDeviceChangeListener {

    public boolean isConnected = false;
    public IDevice defaultDevice = null;

    /**
     * Sent when the a device is connected to the {@link AndroidDebugBridge}.
     * <p/>
     * This is sent from a non UI thread.
     *
     * @param device the new device.
     */
    @Override
    public void deviceConnected(IDevice device) {
        isConnected = true;
        defaultDevice = device;
    }

    /**
     * Sent when the a device is connected to the {@link AndroidDebugBridge}.
     * <p/>
     * This is sent from a non UI thread.
     *
     * @param device the new device.
     */
    @Override
    public void deviceDisconnected(IDevice device) {
        isConnected = false;
    }

    /**
     * Sent when a device data changed, or when clients are started/terminated on the device.
     * <p/>
     * This is sent from a non UI thread.
     *
     * @param device     the device that was updated.
     * @param changeMask the mask describing what changed. It can contain any of the following
     *                   values: {@link IDevice#CHANGE_BUILD_INFO}, {@link IDevice#CHANGE_STATE},
     *                   {@link IDevice#CHANGE_CLIENT_LIST}
     */
    @Override
    public void deviceChanged(IDevice device, int changeMask) {
        // defaultDevice = device;
    }
}
