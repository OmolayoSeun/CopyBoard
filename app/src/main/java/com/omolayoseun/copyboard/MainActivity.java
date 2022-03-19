package com.omolayoseun.copyboard;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.io.File;

public class MainActivity extends AppCompatActivity implements Caller{

    private static final int PERMISSION_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Tools.checkForSavedText(getApplicationContext());

        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            if (checkPermission()) {
                File path = new File(Tools.dirPath);
                if (!path.exists()){
                    if(path.mkdirs())
                        ToastClass.makeText(MainActivity.this, "Created directory");
                }
                callView("onCreate");
                //Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
            } else {
                requestPermission(); // Code for permission
            }
        }

        Fragment fragment = new EditTextFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame,fragment).addToBackStack(null).commit();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1, menu);
        return true;
    }

    @SuppressLint({"CommitTransaction", "NonConstantResourceId"})
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.newFile:
                callView("");
                return true;
            case R.id.viewList:
                callList();
                return true;
            case R.id.exit:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Toast.makeText(MainActivity.this, "Write External Storage permission allows us to read files. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.e("value", "Permission Granted, Now you can use local drive .");
            } else {
                Log.e("value", "Permission Denied, You cannot use local drive .");
            }
        }
        if(checkPermission()){
            File path = new File(Tools.dirPath);
            if (!path.exists()){
                if(path.mkdirs())
                    ToastClass.makeText(MainActivity.this, "Created directory");
            }
        }
    }

    @Override
    public void callList() {
        Fragment fragment2 = new ViewListFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame,fragment2).addToBackStack(null).commit();
    }

    @Override
    public void callView(String call) {
        if (!call.equals("onCreate")) Tools.clear();

        Fragment fragment1 = new EditTextFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame,fragment1).addToBackStack(null).commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}