package din19sp.group10.shoppingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity implements View.OnClickListener {

    //private TextView register;
    private TextView register;
    private EditText Email, Password;
    private Button logBtn;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        register = findViewById(R.id.register);
        register.setOnClickListener(this);

        logBtn = findViewById(R.id.loginBtn);
        logBtn.setOnClickListener(this);
        Email = findViewById(R.id.Email);
        Password = findViewById(R.id.password);

        mAuth = FirebaseAuth.getInstance();
    }

    //////REDIRECT BETWEEN REGISTER AND LOGIN
    @Override
    public void onClick(View view) {
//        startActivity(new Intent(this, Register.class));
        switch (view.getId()) {
            case R.id.register:
                startActivity(new Intent(this, Register.class));
                break;
            case R.id.loginBtn:
                LoginUser();
                break;
        }
    }

    private void LoginUser() {
        // Toast.makeText(Login.this, "Hi DuPham", Toast.LENGTH_LONG).show();
        String email = Email.getText().toString().trim();
        String password = Password.getText().toString().trim();
        if (email.isEmpty()) {
            Email.setError("Email is Empty");
            Email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Email.setError("Please enter your valid email");
            Email.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            Email.setError("Password is Empty");
            Email.requestFocus();
            return;
        }
        if (password.length() < 6) {
            Email.setError("Password is more than 6 characters");
            Email.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    //Redirect to LoginActivity Page
                    startActivity(new Intent(Login.this, LoginActivity.class));
                }
                else  {
                    Toast.makeText(Login.this, "Login Fail", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}