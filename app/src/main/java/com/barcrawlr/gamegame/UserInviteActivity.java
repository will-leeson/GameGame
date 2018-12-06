package com.barcrawlr.gamegame;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;


public class UserInviteActivity extends AppCompatActivity {
    private Realm realm;
    private List<User> userList;
    private ListView listView;
    private Toolbar toolbar;
    private MaterialSearchView searchView;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main_invite_page);

            listView = (ListView) findViewById(R.id.listView);
            toolbar = (Toolbar) findViewById(R.id.toolbarMain);
            searchView = (MaterialSearchView) findViewById(R.id.search_view);

           // setSupportActionBar(toolbar);

            realm = Realm.getDefaultInstance();
            setUpSearch();

            SessionManager sessionManager = new SessionManager(UserInviteActivity.this);
            if(!sessionManager.isLoggedIn()){
                Intent login = new Intent(UserInviteActivity.this, LoginUserActivity.class);
                login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(login);
                UserInviteActivity.this.finish();
            }
        }

        @Override
        protected void onResume(){
            super.onResume();
            getData();
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu, menu);
            MenuItem item = menu.findItem(R.id.action_search);
            searchView.setMenuItem(item);

            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == R.id.menu_logout) {
                SessionManager sessionManager = new SessionManager(UserInviteActivity.this);
                sessionManager.logout();
                Intent login = new Intent(UserInviteActivity.this, LoginUserActivity.class);
                login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                UserInviteActivity.this.finish();
                startActivity(login);
            }else if(id == R.id.action_search){
                //setUpSearch();
            }
            return super.onOptionsItemSelected(item);
        }

        private void getData(){
            userList = new ArrayList<>();
            userList.clear();

            final RealmResults<User> resultUser = realm.where(User.class).findAll();
            for(int i=0;i<resultUser.size();i++){
                userList.add(resultUser.get(i));
            }

            ListUserAdapter listUserAdapter = new ListUserAdapter(UserInviteActivity.this,userList);
            listView.setAdapter(listUserAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    updateDialog(userList.get(position));
                }
            });

            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    SendInviteToGame(position, resultUser);
                    return true;
                }
            });
        }

        private void getSearchResult(final RealmResults<User> list){
            userList = new ArrayList<>();
            userList.clear();

            for(int i=0;i<list.size();i++){
                userList.add(list.get(i));
            }

            ListUserAdapter listUserAdapter = new ListUserAdapter(UserInviteActivity.this,userList);
            listView.setAdapter(listUserAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    updateDialog(userList.get(position));
                }
            });

            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    SendInviteToGame(position, list);
                    return true;
                }
            });
        }

        private void SendInviteToGame(final int pos, final RealmResults<User> result){
            AlertDialog alertDialog = new AlertDialog.Builder(UserInviteActivity.this)
                    .setTitle("Game Invite")
                    .setMessage("Do you wish to start a Gogh Guess It game with this person?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            realm.beginTransaction();
                            //result.deleteFromRealm(pos);
                            realm.commitTransaction();
                            onResume();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setCancelable(true)
                    .show();
        }

        private void updateDialog(final User user){
            LayoutInflater li = LayoutInflater.from(UserInviteActivity.this);
            View promptsView = li.inflate(R.layout.dialog_update, null, false);
            AlertDialog.Builder updateBuilder = new AlertDialog.Builder(UserInviteActivity.this);
            updateBuilder.setView(promptsView);

            final EditText fullname = (EditText) promptsView.findViewById(R.id.tvUpdateFullName);
            fullname.setText(user.getFullName());
            final EditText username = (EditText) promptsView.findViewById(R.id.tvUpdateUsername);
            username.setText(user.getUserName());

            updateBuilder.setCancelable(true)
                    .setTitle("Update User Data")
                    .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(fullname.getText().length()==0 ||
                                    username.getText().length()==0){
                                Toast.makeText(UserInviteActivity.this, "please fill all fields!", Toast.LENGTH_SHORT).show();
                            }else{
                                if(isUsernameExist(username.getText().toString())){
                                    Toast.makeText(UserInviteActivity.this, "User Name alredy Used!", Toast.LENGTH_SHORT).show();
                                }else{
                                    updateData(fullname.getText().toString(),username.getText().toString(),user.getId());
                                }
                            }
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            AlertDialog updateDialog = updateBuilder.create();
            updateDialog.show();
        }

        private boolean isUsernameExist(String username){
            RealmResults listUsername = realm
                    .where(User.class)
                    .equalTo("userName",username).findAll();
            if(listUsername.size()>1){
                return true;
            }else{
                return false;
            }
        }

        private void updateData(String fullname, String username, int id){
            User user = realm.where(User.class)
                    .equalTo("id", id)
                    .findFirst();
            realm.beginTransaction();
            user.setUserName(username);
            user.setFullName(fullname);
            realm.commitTransaction();
            onResume();
        }

        private void setUpSearch(){
            searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Log.d("onQueryTextSubmit",query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    Log.d("onQueryTextChange",newText);
                    //try to search in username
                    RealmResults searchUsername = realm
                            .where(User.class)
                            .contains("userName",newText)
                            .findAll();
                    Log.d("searchResult",searchUsername.toString());
                    if(searchUsername.size()>0){
                        getSearchResult(searchUsername);
                    }else{
                        RealmResults searchFullName = realm
                                .where(User.class)
                                .contains("fullName",newText)
                                .findAll();
                        getSearchResult(searchFullName);
                    }

                    return false;
                }
            });

            searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
                @Override
                public void onSearchViewShown() {
                    //do something
                }

                @Override
                public void onSearchViewClosed() {
                    //do something
                    onResume();
                }
            });
        }
}
