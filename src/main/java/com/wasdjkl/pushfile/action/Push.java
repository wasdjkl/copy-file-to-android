package com.wasdjkl.pushfile.action;

import com.android.ddmlib.AndroidDebugBridge;
import com.intellij.notification.*;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
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
        Project project = e.getProject();
        if(project==null){
            return;
        }
        AppSettingsState settings = AppSettingsState.getSafeInstance(project);
        VirtualFile virtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE);
        if (virtualFile == null) {
            return;
        }
        File srcFile = new File(virtualFile.getPath());
        String srcFilePath = srcFile.getPath();
        String resourcePath = settings.localPath;
        if (!srcFilePath.contains(resourcePath)) {
            showNotification("Please check the local path in the settings", NotificationType.ERROR);
            return;
        }

        String destFilePath = settings.remotePath + srcFilePath.replace(resourcePath, "").replace("\\", "/");

        if (!deviceChangeListener.isConnected) {
            showNotification("No devices/emulators found", NotificationType.ERROR);
            return;
        }
        try {
            pushFile(srcFilePath, destFilePath);
            showNotification(new StringBuilder().append("Copy ").append(srcFilePath).append(" to ").append(destFilePath).toString(), NotificationType.INFORMATION);
        } catch (Exception ex) {
            showNotification(ex.getLocalizedMessage(), NotificationType.ERROR);
            ex.printStackTrace();
        }
    }

    private void pushFile(String srcFilePath, String destFilePath) throws Exception {
        deviceChangeListener.defaultDevice.pushFile(srcFilePath, destFilePath);
    }

    private void showNotification(String content, NotificationType notificationType) {
        String title = "Copy File To Android";
        Notification notification = notificationGroup.createNotification(title, content, notificationType);
        Notifications.Bus.notify(notification);
    }
}