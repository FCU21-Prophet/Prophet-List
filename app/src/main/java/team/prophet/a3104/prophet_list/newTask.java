package team.prophet.a3104.prophet_list;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;

public class newTask extends AppCompatActivity {

    public static final String TAG_RESULT="TAG_RESULT";
    public static final String PROJECT_RESULT="PROJECT_RESULT";
    public static final String CONTENT_RESULT="CONTENT_RESULT";


    private Spinner tag;
    private Spinner project;
    private EditText content;

    private String rt_project = null;
    private String rt_tag = null;
    private String rt_content = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        Button btn_create = (Button)findViewById(R.id.btn_create);

        tag = (Spinner)findViewById(R.id.sp_tag);
        project = (Spinner)findViewById(R.id.sp_project);
        content = (EditText) findViewById(R.id.et_content);


        btn_create.setOnClickListener(create);
        tag.setOnItemSelectedListener(tag_listener);
        project.setOnItemSelectedListener(project_listener);
    }



    private View.OnClickListener create = new View.OnClickListener()
    {
        public void onClick(View v)
        {
            rt_content = content.getText().toString();
            rt_project = project.getSelectedItem().toString();
            rt_tag = tag.getSelectedItem().toString();

            Intent intent = new Intent();

            intent.putExtra(TAG_RESULT, rt_tag);
            intent.putExtra(PROJECT_RESULT, rt_project);
            intent.putExtra(CONTENT_RESULT, rt_content);

            setResult(RESULT_OK, intent);
            finish();
        }
    };

    private AdapterView.OnItemSelectedListener tag_listener = new AdapterView.OnItemSelectedListener()
    {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
        {
            rt_tag = parent.getItemAtPosition(position).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent)
        {
            rt_tag = getResources().getString(R.string.tag_none);//if no choise then set none
        }
    };

    private AdapterView.OnItemSelectedListener project_listener = new AdapterView.OnItemSelectedListener()
    {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
        {
            rt_project = parent.getItemAtPosition(position).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent)
        {
            rt_project = getResources().getString(R.string.tag_none);
        }
    };


}
