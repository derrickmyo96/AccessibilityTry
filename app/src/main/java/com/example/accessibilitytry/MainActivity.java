package com.example.accessibilitytry;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_SYSTEM_ALERT_WINDOW = 1;
    private PackageManager packageManager;
    private List<ApplicationInfo> installedApps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Request SYSTEM_ALERT_WINDOWS permission at runtime
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, REQUEST_CODE_SYSTEM_ALERT_WINDOW);
        }

        // Get package manager and installed apps
        packageManager = getPackageManager();
        installedApps = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);

        Button showAppsButton = findViewById(R.id.showAppsButton);
        showAppsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayInstalledAppsDropdown();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_SYSTEM_ALERT_WINDOW) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(this)) {
                    // Permission not granted, inform the user about the consequences
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void displayInstalledAppsDropdown() {
        // Prepare the list of app names
        List<String> appNames = new ArrayList<>();
        for (ApplicationInfo appInfo : installedApps) {
            appNames.add(appInfo.loadLabel(packageManager).toString());
        }

        // Create a spinner (drop-down) with the list of installed apps
        Spinner spinner = new Spinner(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, appNames);
        spinner.setAdapter(adapter);

        // Add an item selected listener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String appName = (String) parent.getItemAtPosition(position);
                Toast.makeText(MainActivity.this, "Selected: " + appName, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        // Show the spinner (drop-down) as a dialog
        spinner.performClick();
    }
}