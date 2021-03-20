package din19sp.group10.shoppingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Login extends AppCompatActivity implements View.OnClickListener {

    //private TextView register;
    TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        register = findViewById(R.id.register);
        register.setOnClickListener(this);
    }

    //////CHANGE PAGE. MOVE TO REGISTER PAGE
    @Override
    public void onClick(View view) {
        startActivity(new Intent(this, Register.class));
    }
}