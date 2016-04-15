package com.marianilga.probasocial;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiUser;

import org.json.JSONArray;
import com.facebook.FacebookSdk;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{


   // SHA1: 98:DD:56:91:4C:F0:AB:F9:DB:C0:EE:FB:2B:AF:4E:E1:5C:1E:7B:39
    // 98DD56914CF0ABF9DBC0EEFB2BAF4EE15C1E7B39
    // accessToken = 129f92fad87ee7ccde5bb8e55e3af047096566908d9f25daacb33213fd63e5a9486faa6df84eb838cbb1b
    // userId = 63340213

   // CallbackManager callbackManager;


    private static final String[] sMyScope = new String[]{
            /*VKScope.FRIENDS,
            VKScope.WALL,
            VKScope.PHOTOS,
            VKScope.NOHTTPS,
            VKScope.MESSAGES,
            VKScope.DOCS*/
            VKScope.GROUPS
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FacebookSdk.sdkInitialize(getApplicationContext());


        Button buttonVK = (Button)findViewById(R.id.buttonVK);
        buttonVK.setOnClickListener(this);

        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_friends");
        // If using in a fragment
       // loginButton.setFragment(this);
        // Other app specific specialization

        // Callback registration
        /*loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });*/




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        VKSdk.login(this, sMyScope);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
// Пользователь успешно авторизовался
                Log.d("vk", "accessToken = " + res.accessToken);
                Log.d("vk", "userId = " + res.userId);

                getGroups();


            }
            @Override
            public void onError(VKError error) {
// Произошла ошибка авторизации (например, пользователь запретил авторизацию)
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }




    private void getGroups() {
        //VKRequest request = new VKRequest("apps.getFriendsList", VKParameters.from("extended", 1, "type", "request"));

        //VKRequest request = VKApi.groups().get(VKParameters.from(VKApiConst.USER_IDS, "63340213"));
        VKRequest request =  new VKRequest("groups.get", VKParameters.from("extended", 0));


        request.executeWithListener(new VKRequest.VKRequestListener() {

            @Override
            public void onError(VKError error) {

                Log.d("vk", "onError " + error);
                super.onError(error);
            }

            @Override
            public void onComplete(VKResponse response) {

                Log.d("vk", "onComplete response = " + response.json.toString());


               /* Context context = getApplicationContext();
                if (context == null || !isAdded()) {
                    return;
                }
                try {
                    JSONArray jsonArray = response.json.getJSONObject("response").getJSONArray("items");
                    int length = jsonArray.length();
                    final VKApiUser[] vkApiUsers = new VKApiUser[length];
                    CharSequence[] vkApiUsersNames = new CharSequence[length];
                    for (int i = 0; i < length; i++) {
                        VKApiUser user = new VKApiUser(jsonArray.getJSONObject(i));
                        vkApiUsers[i] = user;
                        vkApiUsersNames[i] = user.first_name + " " + user.last_name;
                    }
                    new AlertDialog.Builder(context)
                            .setTitle(R.string.send_request_title)
                            .setItems(vkApiUsersNames, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startApiCall(new VKRequest("apps.sendRequest",
                                            VKParameters.from("user_id", vkApiUsers[which].id, "type", "request")));
                                }
                            }).create().show();
                } catch (Exception e) {
                    e.printStackTrace();
                }*/
            }
        });
    }

}
