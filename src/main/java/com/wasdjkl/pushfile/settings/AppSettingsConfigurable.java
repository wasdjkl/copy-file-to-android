package com.wasdjkl.pushfile.settings;

import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * @author wasdjkl
 */
public class AppSettingsConfigurable implements SearchableConfigurable {
    private final AppSettingsState appSettingsState;
    private AppSettingsPanel mySettingsComponent;

    public AppSettingsConfigurable(Project project) {
        this.appSettingsState = AppSettingsState.getSafeInstance(project);
    }

    @Override
    public @NotNull @NonNls String getId() {
        return "preferences.CopyFileToAndroid";
    }

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "Copy File To Android Settings";
    }

    @Override
    public @Nullable JComponent createComponent() {
        mySettingsComponent = new AppSettingsPanel();
        return mySettingsComponent.getPanel();
    }

    @Override
    public boolean isModified() {
        boolean modified = !mySettingsComponent.getLocalPathText().equals(appSettingsState
                .localPath);
        modified |= !mySettingsComponent.getRemotePathText().equals(appSettingsState
                .remotePath);
        return modified;
    }

    @Override
    public void apply() {
        appSettingsState.localPath = mySettingsComponent.getResourcePathText();
        appSettingsState.remotePath = mySettingsComponent.getRemotePathText();
    }

    @Override
    public void reset() {
        mySettingsComponent.setResourcePathText(appSettingsState.localPath);
        mySettingsComponent.setRemotePathText(appSettingsState.remotePath);
    }

}
