package team.prophet.a3104.prophet_list;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class DrawerTag extends AppCompatActivity
{

    private ListView tagList;
    private ArrayList<PhList> arrayItem;
    private PhListAdapter adapter;
    private String title_tag;
    private PhListDAO db ;
    private String tag;
    private PhList phList;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        db = new PhListDAO(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_tag);
        title_tag = getText(R.string.title).toString() + "：";
        Intent intent = getIntent();
        tag = getString(intent.getIntExtra(MainActivity.TAG_REQUEST,0));
        setTitle(tag);
        tagList = (ListView)findViewById(R.id.lv_tagList);

        refresh();


        tagList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View view, final int position, long id)
            {
                phList = db.get(id);
                AlertDialog.Builder change = new AlertDialog.Builder(DrawerTag.this);
                change.setTitle(R.string.delete);
                change.setMessage(R.string.delete_ask_message);
                change.setPositiveButton(R.string.sure_btn, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
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
                        intent.setClass(DrawerTag.this, newTask.class);
                        startActivityForResult(intent, MainActivity.ACTIVITY_UPDATE);

                    }
                });

                change.show();

                return false;

            }
        });

    }


    private void refresh()
    {// refresh listview
        Cursor cursor = db.searchTag(tag);

        SimpleCursorAdapter sca = new SimpleCursorAdapter(
                this, R.layout.list_view,
                cursor, db.SHOW_COLUMNS, MainActivity.IDS,
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        tagList.setAdapter(sca);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if ((keyCode == KeyEvent.KEYCODE_BACK)) {   //確定按下退出鍵

            Cursor cursor = db.getAllCursor();

            SimpleCursorAdapter sca = new SimpleCursorAdapter(
                    this, R.layout.list_view,
                    cursor, db.SHOW_COLUMNS, MainActivity.IDS,
                    CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
            MainActivity.toDoList.setAdapter(sca);
            finish();
            return true;

        }

        return super.onKeyDown(keyCode, event);

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == MainActivity.ACTIVITY_UPDATE)
        {
            tag = data.getStringExtra(newTask.TAG_RESULT);
            String title = data.getStringExtra(newTask.TITLE_RESULT);
            String content = data.getStringExtra(newTask.CONTENT_RESULT);
            long id = data.getLongExtra(newTask.UPDATE_ID, -1);
            String date;
            String time;

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
                    Toast.makeText(DrawerTag.this,"error",Toast.LENGTH_SHORT).show();
                }
                refresh();


            }
            else
            {
                Toast.makeText(DrawerTag.this,R.string.empty_content,Toast.LENGTH_SHORT).show();
            }
        }
        else//show error information
        {
            Toast.makeText(DrawerTag.this,R.string.requestCode_err,Toast.LENGTH_SHORT).show();
        }
    }

}
