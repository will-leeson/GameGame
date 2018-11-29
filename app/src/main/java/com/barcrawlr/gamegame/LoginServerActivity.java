package com.barcrawlr.gamegame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import io.realm.ObjectServerError;
import io.realm.Realm;
import io.realm.SyncConfiguration;
import io.realm.SyncCredentials;
import io.realm.SyncUser;

public class LoginServerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_server);

        login("gamegame", "gamegame");
    }

    private void login(final String email, final String password)
    {
        if(SyncUser.currentUser() != null) {
            SyncUser.currentUser().logout();

            Realm realm = Realm.getDefaultInstance();
            if(realm != null) {
                realm.close();
                Realm.deleteRealm(realm.getConfiguration());
            }
        }

        SyncCredentials myCredentials = SyncCredentials.usernamePassword(email, password, false);
        SyncUser.loginAsync(myCredentials, "https://devface.us1.cloud.realm.io", new SyncUser.Callback() {
            @Override
            public void onSuccess(SyncUser user) {
                SyncConfiguration configuration = new SyncConfiguration.Builder(user, "realms://devface.us1.cloud.realm.io/~/gamegame").disableSSLVerification().waitForInitialRemoteData().schemaVersion((long) 1.0).build();
                Realm.setDefaultConfiguration(configuration);

                Realm.getInstanceAsync(configuration, new Realm.Callback() {
                    @Override
                    public void onSuccess(Realm realm) {

                        Log.v("success", "success");
                        Intent intent = new Intent(getBaseContext(), FirstRoundActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(Throwable exception) {
                        super.onError(exception);
                        Log.v("errorred again", "error");
                    }
                });
            }

            @Override
            public void onError(ObjectServerError error) {
                Log.v("errored", "error");
//                Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
