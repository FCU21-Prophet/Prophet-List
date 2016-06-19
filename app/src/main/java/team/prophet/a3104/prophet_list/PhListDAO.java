package team.prophet.a3104.prophet_list;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class PhListDAO
{
    //表格名稱
    public static final String TABLE_NAME = "phlist";

    //表格欄位名稱
    public static final String KEY_ID = "_id";

    public static final String TITLE = "title";
    public static final String CONTENT = "content";
    public static final String TAG ="tag";
    public static final String DATE = "phDate";
    public static final String TIME = "time";

    public static final String[] COLUMNS = { KEY_ID, TAG, TITLE, CONTENT, DATE, TIME};
    public static final String[] SHOW_COLUMNS = { TITLE, TAG, DATE, TIME, CONTENT, KEY_ID};

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
            "( " + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TAG + " TEXT NOT NULL, " +
            TITLE + " TEXT NOT NULL, " +
            CONTENT + " TEXT NOT NULL, " +
            DATE + " TEXT NOT NULL, " +
            TIME + " TEXT NOT NULL )";


    private SQLiteDatabase db;

    public PhListDAO(Context context)
    {
        db = PhListDBHelper.getDatabase(context);
    }

    public void close()
    {//關閉資料庫
        db.close();
    }

    public PhList insert(PhList phList)
    {//新增一筆資料
        ContentValues cv = new ContentValues();

        cv.put(TITLE, phList.getListTitle().toString());
        cv.put(CONTENT, phList.getListContent().toString());
        cv.put(TAG, phList.getTag().toString());
        cv.put(DATE, phList.getDate().toString());
        cv.put(TIME, phList.getTime().toString());

        long id = db.insert(TABLE_NAME, null, cv);

        phList.setId(id);//存入_id的值

        return phList;
    }

    public boolean update(PhList phList)
    {//修改內容
        ContentValues cv = new ContentValues();

        cv.put(TITLE, phList.getListTitle());
        cv.put(CONTENT, phList.getListContent());
        cv.put(TAG, phList.getTag());
        cv.put(DATE, phList.getDate());
        cv.put(TIME, phList.getTime());

        String where = KEY_ID + "=" + phList.getId();

        return db.update(TABLE_NAME, cv, where, null) > 0;
    }

    public boolean delete(long id)
    {//刪除一筆資料
        String where = KEY_ID + "=" + id;
        return db.delete(TABLE_NAME, where, null) > 0;
    }

    public Cursor getAllCursor()
    {//取得所有資料
        return db.query(TABLE_NAME, SHOW_COLUMNS, null, null, null, null, DATE + " ASC");

        //String select = "SELECT * FROM " + TABLE_NAME;
        //return db.rawQuery(select, null);
    }

    public Cursor getTagCursor(String tag)
    {
        String where = TAG + "=" + "";
        return db.query(TABLE_NAME, SHOW_COLUMNS, where, null, null, null, DATE + " ASC");
    }


    public PhList get(long id)
    {//取得某一筆資料(指定id), 回傳PhList物件
        PhList phList = null;

        String where = KEY_ID + "=" + id;//搜尋條件
        Cursor result = db.query(TABLE_NAME, COLUMNS, where, null, null, null, null);

        if(result.moveToFirst())
        {
            phList = getRecord(result);
        }

        return phList;
    }

    public PhList getRecord(Cursor cursor)
    {//處理get()傳進來的資料
        PhList result = new PhList();

        result.setId(cursor.getLong(0));
        result.setTag(cursor.getString(1));
        result.setListTitle(cursor.getString(2));
        result.setListContent(cursor.getString(3));
        result.setDate(cursor.getString(4));
        result.setTime(cursor.getString(5));

        return result;
    }

    public Cursor searchTag(String tag)
    {//待改, 指定tag搜尋
        //PhList phList = null;
        String where =
                "SELECT * FROM "+TABLE_NAME+" WHERE "+ TAG +" LIKE \'%" +tag+"%\'";//搜尋條件
        Cursor result = db.rawQuery(where,null);

        return result;
    }

}

