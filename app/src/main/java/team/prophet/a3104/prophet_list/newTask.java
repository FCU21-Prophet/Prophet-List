package team.prophet.a3104.prophet_list;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class newTask extends AppCompatActivity {

    public static final String TAG_RESULT="TAG_RESULT";
    public static final String TITLE_RESULT="TITLE_RESULT";
    public static final String CONTENT_RESULT="CONTENT_RESULT";
    public static final String DATE_RESULT="DATE_RESULT";
    public static final String TIME_RESULT="TIME_RESULT";
    public static final String UPDATE_ID = "ID";

    private Spinner tag;
    private EditText title;
    private EditText content;

    private String rt_tag = null;
    private String rt_title = null;
    private String rt_content = null;
    private String rt_date = null;
    private String rt_time = null;

    private int rt_year;
    private int rt_month;
    private int rt_day;
    private int rt_hour;
    private int rt_minute;

    AlarmManager am;
    PendingIntent pi;

    private long id;
    private PhListDAO phListDAO;
    private PhList phList;


    Intent intent = new Intent();
    Intent alarm_intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        Button btn_create = (Button)findViewById(R.id.btn_create);
        Button btn_settime = (Button) findViewById(R.id.btn_settime);

        tag = (Spinner)findViewById(R.id.sp_tag);
        title = (EditText)findViewById(R.id.et_title);
        content = (EditText) findViewById(R.id.et_content);


        btn_create.setOnClickListener(create);
        tag.setOnItemSelectedListener(tag_listener);
      //  project.setOnItemSelectedListener(project_listener);
        btn_settime.setOnClickListener(settime);

        alarm_intent = new Intent();
        alarm_intent.setClass(newTask.this, AlarmReceiver.class);

        phListDAO = new PhListDAO(this);
        Intent intentEdit = getIntent();
        id = intentEdit.getLongExtra(UPDATE_ID, -1);

        if(id != -1)
        {
            phList = phListDAO.get(id);
            title.setText(phList.getListTitle());

            if(phList.getTag().equals(getString(R.string.tag_none)))
            {
                tag.setSelection(0);
            }
            else if(phList.getTag().equals(getString(R.string.menu_company)))
            {
                tag.setSelection(1);
            }
            else if(phList.getTag().equals(getString(R.string.menu_school)))
            {
                tag.setSelection(2);
            }
            else if(phList.getTag().equals(getString(R.string.menu_personal)))
            {
                tag.setSelection(3);
            }

            content.setText(phList.getListContent());
        }



        pi = PendingIntent.getBroadcast(newTask.this, 1, alarm_intent, 0);
        am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
    }



    private View.OnClickListener create = new View.OnClickListener()
    {
        public void onClick(View v)
        {
            rt_title = title.getText().toString();
            rt_content = content.getText().toString();
            rt_tag = tag.getSelectedItem().toString();

            //Intent intent = new Intent();

            intent.putExtra(TAG_RESULT, rt_tag);
            intent.putExtra(TITLE_RESULT,rt_title);
            intent.putExtra(CONTENT_RESULT, rt_content);
            intent.putExtra(DATE_RESULT, rt_date);
            intent.putExtra(TIME_RESULT, rt_time);
            intent.putExtra(UPDATE_ID, id);

            if(rt_date != null && rt_time != null)
            {
                Calendar cal = Calendar.getInstance();
                cal.set(rt_year, rt_month, rt_day, rt_hour, rt_minute, 0);
                alarm_intent.putExtra(TITLE_RESULT,rt_title);
                alarm_intent.putExtra(CONTENT_RESULT, rt_content);

                am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pi);
            }

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

    private View.OnClickListener settime = new View.OnClickListener()
    {
        public void onClick(View v)
        {
            Calendar c = Calendar.getInstance();
            new TimePickerDialog(newTask.this, new TimePickerDialog.OnTimeSetListener()
            {
                public void onTimeSet(TimePicker view, int hour, int minute)
                {
                    TextView show = (TextView) findViewById(R.id.tv_time);
                    show.setText(hour + ":" + minute);
                    rt_time = hour + ":" + minute;
                    rt_hour = hour;
                    rt_minute = minute;
                }
            }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true).show();

            new DatePickerDialog(newTask.this, new DatePickerDialog.OnDateSetListener()
            {
                public void onDateSet(DatePicker view, int year, int month, int day) {
                    TextView show = (TextView) findViewById(R.id.tv_date);
                    int show_month = month + 1;
                    show.setText(year + "-" + show_month + "-" + day);
                    rt_date = year + "-" + show_month + "-" + day;
                    rt_year = year;
                    rt_month = month;
                    rt_day = day;
                }
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();

        }
    };

}
