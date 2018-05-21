package io.algostack.risk.engine;

import java.io.File;

public class AppProperties {

    public static int getPort() {
        return Integer.getInteger("app.port", 50051);
    }

    public static String getMarketDataPath() {
        return System.getProperty("marketdata.path");
    }

    public static String getESScaledPath() {
        return getMarketDataPath() + File.separator + "scaled";
    }

    public static String getESUnscaledPath() {
        return getMarketDataPath() + File.separator + "unscaled";
    }
}
