package com.wasdjkl.pushfile.action;

import com.android.ddmlib.AndroidDebugBridge;
import com.intellij.notification.*;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.wasdjkl.pushfile.android.DeviceChangeListener;

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
        if (project == null) {
            return;
        }
        String projectPath = project.getBasePath();
        if (projectPath == null) {
            return;
        }
        VirtualFile file = e.getData(CommonDataKeys.VIRTUAL_FILE);
        if (file == null) {
            return;
        }
        String srcFile = file.getCanonicalPath();
        if (srcFile == null) {
            return;
        }

        // resourcePath diff  setting  browser
        String destFile = srcFile.replace(projectPath, "");

        if (!deviceChangeListener.isConnected) {
            showNotification("No devices/emulators found", NotificationType.ERROR);
            return;
        }
        try {
            pushFile(srcFile, destFile);
            showNotification("1 file pushed", NotificationType.INFORMATION);
        } catch (Exception ex) {
            showNotification(ex.getLocalizedMessage(), NotificationType.ERROR);
            ex.printStackTrace();
        }
    }

    private void pushFile(String srcFile, String destFile) throws Exception {
        deviceChangeListener.defaultDevice.pushFile(srcFile, "/sdcard/lncmop" + destFile);
    }

    private void showNotification(String content, NotificationType notificationType) {
        String title = "Copy File to Android device";
        Notification notification = notificationGroup.createNotification(title, content, notificationType, null);
        Notifications.Bus.notify(notification);
    }
}