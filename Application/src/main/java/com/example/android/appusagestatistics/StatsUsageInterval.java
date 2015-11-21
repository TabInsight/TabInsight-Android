package com.example.android.appusagestatistics;

import android.app.usage.UsageStatsManager;

/**
 * Created by biphilip on 10/3/15.
 */
public enum StatsUsageInterval {
    DAILY("Daily", UsageStatsManager.INTERVAL_DAILY),
    WEEKLY("Weekly", UsageStatsManager.INTERVAL_WEEKLY),
    MONTHLY("Monthly", UsageStatsManager.INTERVAL_MONTHLY),
    YEARLY("Yearly", UsageStatsManager.INTERVAL_YEARLY);

    protected int mInterval;
    protected String mStringRepresentation;

    StatsUsageInterval(String stringRepresentation, int interval) {
        mStringRepresentation = stringRepresentation;
        mInterval = interval;
    }

    static StatsUsageInterval getValue(String stringRepresentation) {
        for (StatsUsageInterval statsUsageInterval : values()) {
            if (statsUsageInterval.mStringRepresentation.equals(stringRepresentation)) {
                return statsUsageInterval;
            }
        }
        return null;
    }
}
