package com.proyectoxml.job.infrastructure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "etl.python")
public class ETLProperties {

    private String executable;
    private String scriptPath;
    private String workingDirectory;
    private String databaseConnection;
    private int cancelGraceSeconds = 30;

    public String getExecutable() {
        return executable;
    }

    public void setExecutable(String executable) {
        this.executable = executable;
    }

    public String getScriptPath() {
        return scriptPath;
    }

    public void setScriptPath(String scriptPath) {
        this.scriptPath = scriptPath;
    }

    public String getWorkingDirectory() {
        return workingDirectory;
    }

    public void setWorkingDirectory(String workingDirectory) {
        this.workingDirectory = workingDirectory;
    }

    public String getDatabaseConnection() {
        return databaseConnection;
    }

    public void setDatabaseConnection(String databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public int getCancelGraceSeconds() {
        return cancelGraceSeconds;
    }

    public void setCancelGraceSeconds(int cancelGraceSeconds) {
        this.cancelGraceSeconds = cancelGraceSeconds;
    }
}
