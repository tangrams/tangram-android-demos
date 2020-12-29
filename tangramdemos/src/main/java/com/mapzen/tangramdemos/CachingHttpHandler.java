package com.mapzen.tangramdemos;

import com.mapzen.tangram.networking.DefaultHttpHandler;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class CachingHttpHandler extends DefaultHttpHandler {

    private static final int maxCacheSize = 32 * 1024 * 1024; // 32 MB
    private static final int maxDaysStale = 7;
    private static final String cachedHostName = "tile.nextzen.com";

    private final CacheControl tileCacheControl = new CacheControl.Builder().maxStale(maxDaysStale, TimeUnit.DAYS).build();

    private static OkHttpClient.Builder configureBuilder(File cacheDirectory) {
        OkHttpClient.Builder builder = getClientBuilder();
        if (cacheDirectory != null && cacheDirectory.exists()) {
            builder.cache(new Cache(cacheDirectory, maxCacheSize));
        }
        return builder;
    }

    public CachingHttpHandler(File cacheDirectory) {
        super(configureBuilder(cacheDirectory));
    }

    @Override
    protected void configureRequest(HttpUrl url, Request.Builder builder) {
        if (cachedHostName.equals(url.host())) {
            builder.cacheControl(tileCacheControl);
        }
    }
}
