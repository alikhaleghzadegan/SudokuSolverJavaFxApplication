package com.khaleghzadegan.logic.utility;

import java.io.File;
import java.util.Map;

public class SystemInfo {

    private static final long KILOBYTES = 1024;
    private static final long MEGABYTES = KILOBYTES * 1024;

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
        return Runtime.getRuntime().freeMemory() / (float) MEGABYTES;
    }

    public static float maxMemoryInMegabytes() {
        return Runtime.getRuntime().maxMemory() / (float) MEGABYTES;
    }

    public static float totalMemoryInMegabytes() {
        return Runtime.getRuntime().totalMemory() / (float) MEGABYTES;
    }

    public static File[] filesystemRoots() {
        return File.listRoots();
    }
}
