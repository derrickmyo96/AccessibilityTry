package com.example.accessibilitytry;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;

public class MyAccessibilityService extends AccessibilityService {
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        // Handle accessibility events here
    }

    @Override
    public void onInterrupt() {
        // Handle any required actions when the service is interrupted
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        // Initialize your service and perform any required setup
    }
}
