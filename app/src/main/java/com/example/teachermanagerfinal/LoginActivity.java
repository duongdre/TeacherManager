package com.example.teachermanagerfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    Button signIn, login;
    EditText userName, passWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.btn_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = findViewById(R.id.text_tk);
                passWord = findViewById(R.id.text_mk);
                
                if(userName.getText().toString().equals("duongdre") && passWord.getText().toString().equals("123456")){
                    Toast.makeText(LoginActivity.this, "Đăng Nhập", Toast.LENGTH_SHORT).show();
                    openChoosingActivity();
                }else{
                    Toast.makeText(LoginActivity.this, "Tài khoản không tồn tại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void openChoosingActivity(){
        Intent intent = new Intent(this, ChoosingActivity.class);
        startActivity(intent);
    }
}
