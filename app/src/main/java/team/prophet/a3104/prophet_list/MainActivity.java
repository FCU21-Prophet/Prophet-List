package team.prophet.a3104.prophet_list;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{

    public static final int ACTIVITY_NEW_TASK = 1;//request code from MainActivity to activity_new_task
    public static final int ACTIVITY_UPDATE = 2;

    public static final String TAG_REQUEST = "TAG_REQUEST";
    public static final int[] IDS = {R.id.show_title, R.id.show_tag, R.id.show_date, R.id.show_time, R.id.show_content};

    private String tag;
    private String title;
    private String content;
    private String date;
    private String time;
    private Long id;

    public static ListView toDoList;
    private ImageView userImage;
    private ActionBarDrawerToggle toggle;
    private PhList phList;

    private PhListDAO db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = new PhListDAO(this);

        toDoList = (ListView)findViewById(R.id.lv_toDoList);

        refresh();

        toDoList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View view, final int position, long id)
            {
                phList = db.get(id);
                AlertDialog.Builder change = new AlertDialog.Builder(MainActivity.this);
                change.setTitle(R.string.delete);
                change.setMessage(R.string.delete_ask_message);
                change.setPositiveButton(R.string.sure_btn, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        db.delete(phList.getId());
                        refresh();
                    }

                });
                change.setNegativeButton(R.string.edit_btn, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Intent intent = new Intent();
                        intent.putExtra("ID", phList.getId());
                        intent.setClass(MainActivity.this, newTask.class);
                        startActivityForResult(intent, ACTIVITY_UPDATE);
                    }
                });

                change.show();
                return false;

            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/

                //Intent for call newTask
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, newTask.class);
                startActivityForResult(intent, ACTIVITY_NEW_TASK);

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        userImage = (ImageView)header.findViewById(R.id.user_image);
      /*  userImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent userIntent = new Intent();
                userIntent.setClass(MainActivity.this,UserData.class);
                startActivity(userIntent);
            }
        });*/
    }

    @Override
    //get return data
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        //EXCEPTION!! when user use BACK to end activity
        if(data == null)
        {
            return;
        }

        if(requestCode == ACTIVITY_NEW_TASK)
        {
            tag = data.getStringExtra(newTask.TAG_RESULT);
            title = data.getStringExtra(newTask.TITLE_RESULT);
            content = data.getStringExtra(newTask.CONTENT_RESULT);
            if(data.getStringExtra(newTask.DATE_RESULT) != null)
            {
                date = data.getStringExtra(newTask.DATE_RESULT);
            }
            else
            {
                date = "";
            }
            if(data.getStringExtra(newTask.TIME_RESULT) != null)
            {
                time = data.getStringExtra(newTask.TIME_RESULT);
            }
            else
            {
                time = "";
            }


            if(!content.equals(""))
            {
                PhList item = new PhList();
                item.setTag(tag);
                item.setListTitle(title);
                item.setListContent(content);
                item.setDate(date);
                item.setTime(time);

                item = db.insert(item);

                if(item.getId() == -1)
                {
                    Toast.makeText(MainActivity.this,"error",Toast.LENGTH_SHORT).show();
                }
                refresh();


            }
            else
            {
                Toast.makeText(MainActivity.this,R.string.empty_content,Toast.LENGTH_SHORT).show();
            }


        }
        else if(requestCode == ACTIVITY_UPDATE)
        {
            tag = data.getStringExtra(newTask.TAG_RESULT);
            title = data.getStringExtra(newTask.TITLE_RESULT);
            content = data.getStringExtra(newTask.CONTENT_RESULT);
            id = data.getLongExtra(newTask.UPDATE_ID, -1);

            if(data.getStringExtra(newTask.DATE_RESULT) != null)
            {
                date = data.getStringExtra(newTask.DATE_RESULT);
            }
            else
            {
                date = "";
            }
            if(data.getStringExtra(newTask.TIME_RESULT) != null)
            {
                time = data.getStringExtra(newTask.TIME_RESULT);
            }
            else
            {
                time = "";
            }


            if(!(content.equals("")) && id != -1)
            {
                PhList item = new PhList();
                item.setTag(tag);
                item.setListTitle(title);
                item.setListContent(content);
                item.setDate(date);
                item.setTime(time);
                item.setId(id);

                if(!db.update(item))
                {
                    Toast.makeText(MainActivity.this,"error",Toast.LENGTH_SHORT).show();
                }
                refresh();


            }
            else
            {
                Toast.makeText(MainActivity.this,R.string.empty_content,Toast.LENGTH_SHORT).show();
            }
        }
        else//show error information
        {
            Toast.makeText(MainActivity.this,R.string.requestCode_err,Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.action_about:
                AlertDialog.Builder ad = new AlertDialog.Builder(this);
                ad.setTitle(R.string.menu_about);
                ad.setMessage(R.string.about_author);

                DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface di, int i)
                    {
                    }
                };

                ad.setPositiveButton(R.string.about_ok, listener);
                ad.show();
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        Intent intent = new Intent();
        intent.setClass(MainActivity.this,DrawerTag.class);

        if(toggle.onOptionsItemSelected(item))
        {
            return true;
        }

        switch (item.getItemId())
        {
            case R.id.menu_company:
                intent.putExtra(TAG_REQUEST,R.string.menu_company);
                startActivity(intent);
                break;

            case R.id.menu_school:
                intent.putExtra(TAG_REQUEST,R.string.menu_school);
                startActivity(intent);
                break;

            case R.id.menu_personal:
                intent.putExtra(TAG_REQUEST,R.string.menu_personal);
                startActivity(intent);
                break;


            case R.id.menu_done:
                intent.putExtra(TAG_REQUEST,R.string.menu_done);
                startActivity(intent);
                break;

            case R.id.menu_setting:
            /*    intent.putExtra()
                startActivity(intent);*/
                break;

            default:

                break;
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

  /*  private ArrayList<PhList> returnData(Cursor cursor)
    {
        ArrayList<PhList> arrayList = new ArrayList<PhList>();
        //TITLE, TAG, CONTENT, DATE, TIME, KEY_ID
        PhList result = new PhList();
        cursor.moveToFirst();

        while (!cursor.isAfterLast())
        {
        /*result.setListTitle(cursor.getString(0));
        result.setTag(cursor.getString(1));
        result.setListContent(cursor.getString(2));
        result.setDate(cursor.getString(3));
        result.setTime(cursor.getString(4));
        result.setId(cursor.getLong(5));

            result.setId(cursor.getLong(0));
            result.setTag(cursor.getString(1));
            result.setListTitle(cursor.getString(2));
            result.setListContent(cursor.getString(3));
            result.setDate(cursor.getString(4));
            result.setTime(cursor.getString(5));

            arrayList.add(result);
            cursor.moveToNext();
        }


        cursor.close();
        return arrayList;
    }        */

    private void refresh()
    {// refresh listview
        Cursor cursor = db.getAllCursor();

        SimpleCursorAdapter sca = new SimpleCursorAdapter(
                this, R.layout.list_view,
                cursor, db.SHOW_COLUMNS, IDS,
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        toDoList.setAdapter(sca);
    }




}
