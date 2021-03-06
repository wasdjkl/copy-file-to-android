package com.wasdjkl.pushfile.settings;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.io.File;

/**
 * @author wasdjkl
 */
public class AppSettingsPanel {
    private JButton choosePathButton;
    private JTextField localPath;
    private JTextField remotePath;
    private JPanel jPanel;

    public AppSettingsPanel() {
        choosePathButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fileChooser.showOpenDialog(choosePathButton);
            File file = fileChooser.getSelectedFile();
            localPath.setText(file.getPath());
        });
    }

    public JComponent getPanel() {
        return jPanel;
    }

    @NotNull
    public String getLocalPathText() {
        return localPath.getText();
    }

    @NotNull
    public String getResourcePathText() {
        return localPath.getText();
    }

    public void setResourcePathText(@NotNull String newText) {
        localPath.setText(newText);
    }

    @NotNull
    public String getRemotePathText() {
        return remotePath.getText();
    }

    public void setRemotePathText(@NotNull String newText) {
        remotePath.setText(newText);
    }

}
