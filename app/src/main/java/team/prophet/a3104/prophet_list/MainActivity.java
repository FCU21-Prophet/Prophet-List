package team.prophet.a3104.prophet_list;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
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

    private String tag ;
    private String content ;

    private String text_tag;
    private ListView toDoList;
    private ArrayList<String> arrayItem;
    private ArrayAdapter<String> adapter;
    private ImageView userImage;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        text_tag = getText(R.string.menu_tag).toString() + "：";

        toDoList = (ListView)findViewById(R.id.lv_toDoList);
        arrayItem = new ArrayList<String>();
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayItem);
        toDoList.setAdapter(adapter);
        toDoList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View view, final int position, long id)
            {
                AlertDialog.Builder change = new AlertDialog.Builder(MainActivity.this);
                change.setTitle(R.string.delete);
                change.setMessage(R.string.delete_ask_message);
                change.setPositiveButton(R.string.sure_btn, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        arrayItem.remove(position);
                        toDoList.setAdapter(adapter);
                    }

                });
                change.setNegativeButton(R.string.no_btn, new DialogInterface.OnClickListener()
                {

                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
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
                startActivityForResult(intent,ACTIVITY_NEW_TASK);

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        userImage = (ImageView)header.findViewById(R.id.user_image);
        userImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent userIntent = new Intent();
                userIntent.setClass(MainActivity.this,UserData.class);
                startActivity(userIntent);
            }
        });
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
            content = data.getStringExtra(newTask.CONTENT_RESULT);

            if(!content.equals("")) {
                arrayItem.add("\n"
                        + text_tag + tag + "\n\n"
                        + "\t" + content + "\n");
                toDoList.setAdapter(adapter);
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
        int id = item.getItemId();

        if (id == R.id.menu_user)
        {
            // Handle the camera action
        }
        else if (id == R.id.menu_company)
        {

        }
        else if (id == R.id.menu_school)
        {

        }
        else if (id == R.id.menu_personal)
        {

        }
        else if (id == R.id.menu_tag)
        {

        }
        else if (id == R.id.menu_done)
        {

        }
        else if(id == R.id.menu_setting)
        {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
