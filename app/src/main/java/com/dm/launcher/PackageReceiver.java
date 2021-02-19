package com.dm.launcher;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class PackageReceiver extends BroadcastReceiver {
    Thread loader;

    public PackageReceiver(Context context, Thread loader) {
        this.loader = loader;
        
        IntentFilter filter = new IntentFilter(Intent.ACTION_PACKAGE_ADDED);
        filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        filter.addAction(Intent.ACTION_PACKAGE_CHANGED);
        filter.addDataScheme("package");
        context.registerReceiver(this, filter);
        
        IntentFilter eFilter = new IntentFilter();
        eFilter.addAction(Intent.ACTION_EXTERNAL_APPLICATIONS_AVAILABLE);
        eFilter.addAction(Intent.ACTION_EXTERNAL_APPLICATIONS_UNAVAILABLE);
        context.registerReceiver(this, eFilter);
    }

    @Override public void onReceive(Context context, Intent intent) {
        loader.run();
    }
}
