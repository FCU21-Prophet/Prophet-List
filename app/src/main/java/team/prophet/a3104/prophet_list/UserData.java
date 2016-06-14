package team.prophet.a3104.prophet_list;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UserData extends AppCompatActivity
{

    Button ok ;
    EditText name;
    EditText email;
    EditText phone;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);

        ok = (Button)findViewById(R.id.user_ok);
        name = (EditText)findViewById(R.id.user_name);
        email = (EditText)findViewById(R.id.user_email);
        phone = (EditText)findViewById(R.id.user_phone);

        ok.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                // set data
                /*
                if(!name.getText().toString().equals(getString(R.string.user_name)))
                {

                }

                if(!email.getText().toString().equals(getString(R.string.user_email)))
                {

                }

                if(!phone.getText().toString().equals(getString(R.string.user_phone)))
                {

                }
                */

                finish();
            }
        });

    }
}
