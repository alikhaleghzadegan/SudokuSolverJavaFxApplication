package com.khaleghzadegan.logic.utility;

import java.io.File;
import java.util.Map;

public class SystemInfo {

    private static final long kilobytes = 1024;
    private static final long megabytes = kilobytes * 1024;
    private static final long gigabytes = megabytes * 1024;

    private SystemInfo() {
    }

    public static String javaVersion() {
        return System.getProperty("java.version");
    }

    public static String javafxVersion() {
        return System.getProperty("javafx.version");
    }

    public static String osName() {
        return System.getProperty(System.getProperty("os.name"));
    }

    public static String osVersion() {
        return System.getProperty(System.getProperty("os.version"));
    }

    public static String osArchitecture() {
        return System.getProperty(System.getProperty("os.arch"));
    }

    public static Map<String, String> environmentValues() {
        return System.getenv();
    }

    public static int availableProcessors() {
        return Runtime.getRuntime().availableProcessors();
    }

    public static float freeMemoryInMegabytes() {
        return Runtime.getRuntime().freeMemory() / (float) megabytes;
    }

    public static float maxMemoryInMegabytes() {
        return Runtime.getRuntime().maxMemory() / (float) megabytes;
    }

    public static float totalMemoryInMegabytes() {
        return Runtime.getRuntime().totalMemory() / (float) megabytes;
    }

    public static File[] filesystemRoots() {
        return File.listRoots();
    }
}
