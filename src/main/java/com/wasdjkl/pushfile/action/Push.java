package com.wasdjkl.pushfile.action;

import com.android.ddmlib.AndroidDebugBridge;
import com.intellij.notification.*;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.vfs.VirtualFile;
import com.wasdjkl.pushfile.android.DeviceChangeListener;
import com.wasdjkl.pushfile.settings.AppSettingsState;

import java.io.File;

/**
 * @author wasdjkl
 */
public class Push extends AnAction {
    private final NotificationGroup notificationGroup = NotificationGroupManager.getInstance().getNotificationGroup("Copy File to Android");
    private final DeviceChangeListener deviceChangeListener;

    public Push() {
        AndroidDebugBridge.init(false);
        AndroidDebugBridge.createBridge();
        deviceChangeListener = new DeviceChangeListener();
        AndroidDebugBridge.addDeviceChangeListener(deviceChangeListener);
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        AppSettingsState settings = AppSettingsState.getInstance();
        VirtualFile virtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE);
        if (virtualFile == null) {
            return;
        }

        File srcFile = new File(virtualFile.getPath());
        String srcFilePath = srcFile.getPath();
        String resourcePath = settings.resourcePath;
        if (!srcFilePath.contains(resourcePath)) {
            showNotification("Please check the resource path in the settings", NotificationType.ERROR);
            return;
        }

        String destFilePath = settings.remotePath + srcFilePath.replace(resourcePath, "").replace("\\", "/");

        if (!deviceChangeListener.isConnected) {
            showNotification("No devices/emulators found", NotificationType.ERROR);
            return;
        }
        try {
            pushFile(srcFilePath, destFilePath);
            showNotification("1 file pushed", NotificationType.INFORMATION);
        } catch (Exception ex) {
            showNotification(ex.getLocalizedMessage(), NotificationType.ERROR);
            ex.printStackTrace();
        }
    }

    private void pushFile(String srcFilePath, String destFilePath) throws Exception {
        deviceChangeListener.defaultDevice.pushFile(srcFilePath, destFilePath);
    }

    private void showNotification(String content, NotificationType notificationType) {
        String title = "Copy File to Android device";
        Notification notification = notificationGroup.createNotification(title, content, notificationType, null);
        Notifications.Bus.notify(notification);
    }
}