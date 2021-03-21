package din19sp.group10.shoppingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;

    //private TextView login;
    private TextView login;
    private EditText regFullName, regEmail, regPassword, regPhone;
    private Button regBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        login = findViewById(R.id.login);
        login.setOnClickListener(this);

        regFullName = findViewById(R.id.fullName);
        regEmail = findViewById(R.id.Email);
        regPassword = findViewById(R.id.password);
        regPhone = findViewById(R.id.phone);

        regBtn = findViewById(R.id.registerBtn);
        regBtn.setOnClickListener(this);
    }

    //////CHANGE PAGE. MOVE TO LOGIN PAGE
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                startActivity(new Intent(this, Login.class));
                break;
            case R.id.registerBtn:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        String fullName = regFullName.getText().toString().trim();
        String email = regEmail.getText().toString().trim();
        String password = regPassword.getText().toString().trim();
        String phone = regPhone.getText().toString().trim();

        if (fullName.isEmpty()) {
            regFullName.setError("Enter your full name");
            regFullName.requestFocus(); // show error
            return;
        }

        if (email.isEmpty()) {
            regEmail.setError("Enter your email");
            regEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            regEmail.setError("Please provide valid email");
            regEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            regPassword.setError("Enter your password");
            regPassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            regPassword.setError("Password must be more than 6 letters");
            regPassword.requestFocus();
            return;
        }

        if (phone.isEmpty()) {
            regPhone.setError("Enter your phone");
            regPhone.requestFocus();
            return;
        }
        if (!Patterns.PHONE.matcher(phone).matches() || phone.length() < 10) {
            regPhone.setError("It is not a phone number");
            regPhone.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(fullName, email, phone);
                            FirebaseDatabase.getInstance().getReference("users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Register.this, "Registration successful!", Toast.LENGTH_LONG).show();
                                    }
                                    else {
                                        Toast.makeText(Register.this, "Registration Fail", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                        else {
                            Toast.makeText(Register.this, "Registration Fail", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


}