package com.barcrawlr.gamegame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;


public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.etRegisterFullname)EditText fullname;
    @BindView(R.id.etRegisterUsername)EditText username;
    @BindView(R.id.etRegisterPassword)EditText password;
    @BindView(R.id.btnRegister)Button register;

    @BindView(R.id.tvList)TextView listLog;

    private Random rand = new Random();

    private Realm realm;
    private int idUser;
    private int idGenerator;

    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        realm = Realm.getDefaultInstance();

        listLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RealmQuery<User> listUser = realm.where(User.class);
                RealmResults<User> resultUser = realm.where(User.class).findAll();
                Log.d("userlist",resultUser.toString());
                Toast.makeText(RegisterActivity.this, resultUser.get(0).getFullName(), Toast.LENGTH_SHORT).show();
            }
        });
        idGenerator = rand.nextInt(99999) + 1;

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fullname.getText().length()==0 ||
                        username.getText().length()==0 ||
                        password.getText().length()==0){
                    Toast.makeText(RegisterActivity.this,"fill all fields!",Toast.LENGTH_SHORT).show();
                }else{
                    User user = new User();
                    User IDCheck = realm.where(User.class).equalTo("id", idGenerator).findFirst();
                    RealmResults<User> resultUser = realm.where(User.class).findAll();

                    //check if any user already registered
                    if(resultUser.size()>0){
                        //found users, get last userID
                        if(IDCheck!=null) {
                            while (IDCheck!=null) {
                                idGenerator = rand.nextInt(99999) + 1;
                                if (IDCheck==null) {
                                    user.setId(idGenerator);
                                    break;
                                }
                            }
                        }
                        else{
                            user.setId(idGenerator);
                        }
                        registerProccess(user.getId());
                    }else{
                        //empty users
                        idUser = 1;
                        Log.d("userID",String.valueOf(idUser));
                        registerProccess(idUser);
                    }
                }
            }
        });
    }

    private void registerProccess(int id){
        realm.beginTransaction();
        User user = realm.createObject(User.class);
        user.setId(id);
        user.setFullName(fullname.getText().toString());
        user.setUserName(username.getText().toString());
        user.setPassword(password.getText().toString());
        realm.commitTransaction();
        Toast.makeText(getApplicationContext(), "register success", Toast.LENGTH_SHORT).show();
        RegisterActivity.this.finish();
        Intent intent = new Intent(getBaseContext(), LoginUserActivity.class);
        //intent.putExtra("Pic", );
        startActivity(intent);
    }
}
