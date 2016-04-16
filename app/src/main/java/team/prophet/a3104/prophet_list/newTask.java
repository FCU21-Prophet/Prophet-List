package team.prophet.a3104.prophet_list;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class newTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        Button btn_create = (Button)findViewById(R.id.btn_create);
        btn_create.setOnClickListener(create);
    }

    private View.OnClickListener create = new View.OnClickListener()
    {
        public void onClick(View v)
        {
            finish();
        }
    };
}
