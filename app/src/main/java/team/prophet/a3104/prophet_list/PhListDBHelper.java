package team.prophet.a3104.prophet_list;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class PhListDBHelper extends SQLiteOpenHelper
{
    //資料庫名稱
    public static final String DATABASE_NAME = "phlist-data.db";
    //版本
    public static final int VERSION = 1;
    //資料庫物件?
    private static SQLiteDatabase database;


    //建構子
    public PhListDBHelper(Context context, String name,
                        SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
    }

    public static SQLiteDatabase getDatabase(Context context)
    {
        if(database == null || !database.isOpen())
        {
            database = new PhListDBHelper(context, DATABASE_NAME, null, VERSION).getWritableDatabase();
        }
        return database;
    }

    @Override
    public SQLiteDatabase getWritableDatabase() {
        return super.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(PhListDAO.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXIST" + PhListDAO.TABLE_NAME);
        onCreate(db);
    }
}
