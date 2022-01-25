package com.wasdjkl.pushfile.settings;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author wasdjkl
 */
@State(
        name = "CopyFileToAndroidSettings",
//        storages = {@Storage(StoragePathMacros.WORKSPACE_FILE)}
        storages = @Storage("CopyFileToAndroidSettings.xml")
)
public class AppSettingsState implements PersistentStateComponent<AppSettingsState> {

    public String localPath = "D:\\";
    public String remotePath = "/sdcard/";

    public static AppSettingsState getSafeInstance(Project project) {
        AppSettingsState settings = project.getService(AppSettingsState.class);
        return settings != null ? settings : new AppSettingsState();
    }

    @Nullable
    @Override
    public AppSettingsState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull AppSettingsState state) {
        XmlSerializerUtil.copyBean(state, this);
    }

}
