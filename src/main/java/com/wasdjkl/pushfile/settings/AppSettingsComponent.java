// Copyright 2000-2022 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.

package com.wasdjkl.pushfile.settings;

import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.FormBuilder;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Supports creating and managing a {@link JPanel} for the Settings Dialog.
 * @author wasdjkl
 */
public class AppSettingsComponent {

  private final JPanel mainPanel;
  private final JBTextField resourcePathText = new JBTextField();
  private final JBTextField remotePathText = new JBTextField();
//  private final JBCheckBox myIdeaUserStatus = new JBCheckBox("Do you use IntelliJ IDEA? ");

  public AppSettingsComponent() {
    FormBuilder mainFormBuilder = FormBuilder.createFormBuilder();
    // choose resource path
    JButton choosePathButton  = new JButton("...");
    choosePathButton.addActionListener(new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fileChooser.showOpenDialog(mainPanel);
                File file = fileChooser.getSelectedFile();
                resourcePathText.setText(file.getPath());
              }
            });
    // input resource path
    mainFormBuilder.addLabeledComponent(new JBLabel("Resource path:"), resourcePathText, 1, false);
    mainFormBuilder.addComponent(choosePathButton,1);
    mainFormBuilder.addSeparator();
    mainFormBuilder.addLabeledComponent(new JBLabel("Remote path:"), remotePathText, 1, false);
//    mainFormBuilder.addComponent(myIdeaUserStatus, 1);
    mainFormBuilder.addComponentFillVertically(new JPanel(), 0);
    mainPanel = mainFormBuilder.getPanel();
  }

  public JPanel getPanel() {
    return mainPanel;
  }

  public JComponent getPreferredFocusedComponent() {
    return resourcePathText;
  }

  @NotNull
  public String getResourcePathText() {
    return resourcePathText.getText();
  }

  public void setResourcePathText(@NotNull String newText) {
    resourcePathText.setText(newText);
  }

  @NotNull
  public String getRemotePathText() {
    return remotePathText.getText();
  }

  public void setRemotePathText(@NotNull String newText) {
    remotePathText.setText(newText);
  }


//  public boolean getIdeaUserStatus() {
//    return myIdeaUserStatus.isSelected();
//  }

//  public void setIdeaUserStatus(boolean newStatus) {
//    myIdeaUserStatus.setSelected(newStatus);
//  }

}
