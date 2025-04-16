package com.varun.sse.demo.model;


public class SystemMetric {
    private double cpuUsage; // Percentage
    private double memoryUsage; // Percentage
    private int activeUsers;

    public SystemMetric(double cpuUsage, double memoryUsage, int activeUsers) {
        this.cpuUsage = cpuUsage;
        this.memoryUsage = memoryUsage;
        this.activeUsers = activeUsers;
    }

    public double getCpuUsage() {
        return cpuUsage;
    }

    public void setCpuUsage(double cpuUsage) {
        this.cpuUsage = cpuUsage;
    }

    public double getMemoryUsage() {
        return memoryUsage;
    }

    public void setMemoryUsage(double memoryUsage) {
        this.memoryUsage = memoryUsage;
    }

    public int getActiveUsers() {
        return activeUsers;
    }

    public void setActiveUsers(int activeUsers) {
        this.activeUsers = activeUsers;
    }
}
