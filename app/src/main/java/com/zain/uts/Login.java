package com.zain.uts;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity {
    public List<User> user;
    public EditText username,password;
    public Button login;
    Database db;
    public static SharedPreferences spPengguna;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user = new ArrayList<>();
        user.add(new User("Zain", "123"));

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User pengguna = null;
                for(User u:user){
                    if(u.getUsername().equals(username.getText().toString()) && u.getPassword().equals(password.getText().toString())){
                        pengguna = u;
                    }
                }

                if(pengguna != null){
                    spPengguna = Login.this.getSharedPreferences("Userlogin", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = spPengguna.edit();
                    edit.putString("sedangLogin",pengguna.getUsername());
                    edit.apply();

                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(Login.this, "Username or Password is not valid", Toast.LENGTH_LONG).show();
                }
            }
        });

        db = new Database(this);
    }
}

