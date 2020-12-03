package com.example.ussdwithjava;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.ActionMode;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    final int RequestCode = 1001;
    final int SimRequestCode = 1002;
    String wantPermission = Manifest.permission.READ_PHONE_STATE;
    Activity activity = MainActivity.this;
    ArrayList<String> _mst=new ArrayList<>();
    TelephonyManager telephonyManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            Log.w("MainActivity", "it is denied");
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, RequestCode);
            //ActivityCompat.per

            //return;
        }

    }

    public void dialCode(View view) {

        Intent callIntent = new Intent(Intent.ACTION_CALL);
        String ussdCode = "*" + 556 + Uri.encode("#");
        Log.w("MainActivity", "here i am " + ussdCode);
        callIntent.setData(Uri.parse("tel:" + ussdCode));
        startActivityForResult(callIntent, RequestCode);

        //startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + ussdCode)));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.w("MainActivity", "request code is " + requestCode);
        Log.w("MainActivity", "result code is " + resultCode);
        Log.w("MainActivity", "permission is " + PackageManager.PERMISSION_GRANTED);



        if (requestCode == RequestCode) {
//            Log.w("MainActivity", "here on result 1 " + data.getData());
//            Log.w("MainActivity", "here on result 2 " + data.toString());
//            Bundle bundle = data.getExtras();
//            Object dd = (Object) bundle.get("data");
            Log.w("MainActivity", "here on result " + data);
        }

        if (requestCode == RequestCode) {

            if (resultCode == RESULT_OK) {
                // String result=data.getStringExtra("result");
                String dd = data.toString();
                Log.w("MainActivity", " the data is " + dd);
                //Toast.makeText(getApplicationContext(), dd, 1).show();
            }

        }


//        switch (requestCode) {
//            case SimRequestCode:
//                if (requestCode == PackageManager.PERMISSION_GRANTED) {
//                    Log.w("MainActivity", "request code is " + requestCode);
//                    Log.w("MainActivity", "result code is " + resultCode);
//                    Bundle bundle = data.getExtras();
//                    Object dd = (Object) bundle.get("data");
//                    Log.w("MainActivity", "here on result "+ dd);
//                } else {
//                    Toast.makeText(activity,"Permission Denied. We can't get phone number.", Toast.LENGTH_LONG).show();
//                }
//                break;
//        }

//        try {
//            Bundle bundle = data.getExtras();
//            Object dd = (Object) bundle.get("data");
//            Log.w("MainActivity", "here on result "+ dd);
//        }
//        catch (Exception e) {
//            Log.w("MainActivity", e.getMessage());
//        }
    }

    public void getSimNumbers(View view) {
//        telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
//        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, SimRequestCode);
//        } else {
//            Log.w("MainActivity", "sims are 1 " + telephonyManager.getSimSerialNumber());
//            Log.w("MainActivity", "sims are  1 " + telephonyManager.getNetworkOperatorName());
//            Log.w("MainActivity", "sims are 2 " + telephonyManager.getLine1Number());
//            Log.w("MainActivity", "sims are  3 " + telephonyManager.getNetworkOperator());
//            //Log.w("MainActivity", "sims are 4 " + telephonyManager.getSimCarrierId());
//        }

        if (!checkPermission(wantPermission)) {
            requestPermission(wantPermission);
        } else {

            Log.w("MainActivity", "Phone number: " + getPhone());
            _mst = getPhone();

            for (String op : _mst) {
                Log.i("Device Information", String.valueOf(op));
            }

        }
    }

    private void requestPermission(String permission){
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)){
            Toast.makeText(activity, "Phone state permission allows us to get phone number. Please allow it for additional functionality.", Toast.LENGTH_LONG).show();
        }
        ActivityCompat.requestPermissions(activity, new String[]{permission},SimRequestCode);
    }

    private boolean checkPermission(String permission){
        if (Build.VERSION.SDK_INT >= 23) {
            int result = ContextCompat.checkSelfPermission(activity, permission);
            if (result == PackageManager.PERMISSION_GRANTED){
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private ArrayList<String> getPhone() {
        TelephonyManager phoneMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(activity, wantPermission) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        ArrayList<String> _lst =new ArrayList<>();
        _lst.add(String.valueOf(phoneMgr.getCallState()));
        _lst.add("IMEI NUMBER :-"+phoneMgr.getImei());
        _lst.add("MOBILE NUMBER :-"+phoneMgr.getLine1Number());
        _lst.add("SERIAL NUMBER :-"+phoneMgr.getSimSerialNumber());
        _lst.add("SIM OPERATOR NAME :-"+phoneMgr.getSimOperatorName());
        //_lst.add("MEI NUMBER :-"+phoneMgr.getMeid());
        _lst.add("SIM STATE :-"+String.valueOf(phoneMgr.getSimState()));
        _lst.add("COUNTRY ISO :-"+phoneMgr.getSimCountryIso());
        return _lst;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);

//        try {
//            switch (requestCode) {
//                case SimRequestCode:
//                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) !=
//                            PackageManager.PERMISSION_GRANTED) {
//                        return;
//                    } else{
//                        Log.w("MainActivity", "sims are " + telephonyManager.getSimSerialNumber());
//                        Log.w("MainActivity", "sims are " + telephonyManager.getNetworkOperatorName());
//                    }
//            }
//        }
//        catch (Exception e) {
//            Log.w("MainActivity", "error is " + e.getMessage());
//        }

        switch (requestCode) {
            case SimRequestCode:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.w("MainActivity", "Phone number: " + getPhone());
                } else {
                    Toast.makeText(activity,"Permission Denied. We can't get phone number.", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    //    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//
//
//        switch (requestCode) {
//            case RequestCode:
//                if (grantResults.length > 0 &&
//                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    // Permission is granted. Continue the action or workflow
//                    // in your app.
//                    Log.w("MainActivity", "here on result "+ grantResults.length);
//                }  else {
//                    // Explain to the user that the feature is unavailable because
//                    // the features requires a permission that the user has denied.
//                    // At the same time, respect the user's decision. Don't link to
//                    // system settings in an effort to convince the user to change
//                    // their decision.
//                }
//                return;
//        }
//    }
}
