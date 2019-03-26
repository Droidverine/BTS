package com.droidverine.bts.bts;

import android.Manifest;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.IBinder;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TrackingService extends Service {


    private static final String TAG = TrackingService.class.getSimpleName();
    Location location;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
      //  loginToFirebase();
        requestLocationUpdates(getApplicationContext());
    }

//Create the persistent notification//


    protected BroadcastReceiver stopReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

//Unregister the BroadcastReceiver when the notification is tapped//

            unregisterReceiver(stopReceiver);

//Stop the Service//

            stopSelf();
        }
    };


//Initiate the request to track the device's location//

    public Location requestLocationUpdates(Context context) {
        LocationRequest request = new LocationRequest();

//Specify how often your app should request the device’s location//

        request.setInterval(10000);

//Get the most accurate location data available//

        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(context);
     //   final String path = getString(R.string.firebase_path);
        int permission = ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION);

//If the app currently has access to the location permission...//

        if (permission == PackageManager.PERMISSION_GRANTED) {

//...then request location updates//

            client.requestLocationUpdates(request, new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {

//Get a reference to the database, so your app can perform read and write operations//
                FirebaseDatabase  database = FirebaseDatabase.getInstance();

                    DatabaseReference myRef = database.getReference();

                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("location");
                     location = locationResult.getLastLocation();
                    if (location != null) {

//Save the location data to the database//
                        Log.d("alare",""+location);
                        if (FirebaseAuth.getInstance().getCurrentUser()!=null){
                        myRef.child("Driver").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                dataSnapshot.getRef().child("email").setValue(FirebaseAuth.getInstance().getCurrentUser().getEmail());
//                                dataSnapshot.getRef().child("username").setValue(FirebaseAuth.getInstance().getCurrentUser().getDisplayName().toString());
                                dataSnapshot.getRef().child("lat").setValue(location.getLatitude());
                                dataSnapshot.getRef().child("lon").setValue(location.getLongitude());




                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.d("User", databaseError.getMessage());
                            }
                        });
                      //  ref.setValue(location);
                    }}
                }
            }, null);

        }
    return location;
    }


}