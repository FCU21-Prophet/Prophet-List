package team.prophet.a3104.prophet_list;

import android.content.DialogInterface;
import android.content.Intent;
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
    private ArrayList<String> arrayItem;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_tag);

        Intent intent = getIntent();
        String tag = getString(intent.getIntExtra(MainActivity.TAG_REQUEST,0));
        setTitle(tag);
        tagList = (ListView)findViewById(R.id.lv_tagList);
        /*
        * get the tag that want to show
        * */
        arrayItem = new ArrayList<String>();
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayItem);
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
                        arrayItem.remove(position);
                        /*
                        * sql delete
                        * */
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
}
