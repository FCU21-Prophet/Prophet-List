package team.prophet.a3104.prophet_list;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class DrawerTag extends AppCompatActivity
{

    private ListView tagList;
    private ArrayList<PhList> arrayItem;
    private PhListAdapter adapter;
    private String title_tag;
    private PhListDAO db ;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        db = new PhListDAO(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_tag);
        title_tag = getText(R.string.title).toString() + "：";
        Intent intent = getIntent();
        String tag = getString(intent.getIntExtra(MainActivity.TAG_REQUEST,0));
        setTitle(tag);
        tagList = (ListView)findViewById(R.id.lv_tagList);

        arrayItem = new ArrayList<PhList>();
        returnData(db.searchTag(tag));

        adapter = new PhListAdapter(this, arrayItem);
        tagList.setAdapter(adapter);

        tagList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View view, final int position, long id)
            {
                AlertDialog.Builder change = new AlertDialog.Builder(DrawerTag.this);
                change.setTitle(R.string.delete);
                change.setMessage(R.string.delete_ask_message);
                change.setPositiveButton(R.string.sure_btn, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.delete(arrayItem.get(position).getId());
                        arrayItem.remove(position);
                        tagList.setAdapter(adapter);
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

    }

    private void returnData(Cursor cursor)
    {
        PhList result = new PhList();

            while (cursor.moveToNext())
            {
                result.setId(cursor.getLong(0));
                result.setTag(cursor.getString(1));
                result.setListTitle(cursor.getString(2));
                result.setListContent(cursor.getString(3));
                result.setDate(cursor.getString(4));
                result.setTime(cursor.getString(5));

                arrayItem.add(result);
            }


        cursor.close();
    }

}
