package tabinsights.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pushkar on 10/3/15.
 */
public class AppsInfoDatasource {
    private SQLiteDatabase sqLiteDatabase;
    private DatabaseHelper dbHelper;
    private String[] allColums = {
            DatabaseHelper.app_col_name,
            DatabaseHelper.access_time_col_name,
            DatabaseHelper.use_time_col_name
    };

    public void close(){
        dbHelper.close();
    }

    public AppsInfoDatasource(Context context){
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException{
        sqLiteDatabase = dbHelper.getWritableDatabase();
    }

    public void createEntry(String appName, String accessTime, long useTime){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.app_col_name, appName);
        values.put(DatabaseHelper.access_time_col_name, accessTime);
        values.put(DatabaseHelper.use_time_col_name, useTime);
        sqLiteDatabase.insert(DatabaseHelper.table_name, null, values);
    }

    public void truncateTable(){
        sqLiteDatabase.execSQL("DELETE FROM " + DatabaseHelper.table_name + ";");
        sqLiteDatabase.execSQL("VACUUM;");
    }

    public List<AppUseInfo> getAllRecords(){
        ArrayList<AppUseInfo> result = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(DatabaseHelper.table_name, allColums, null, null,
                null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            AppUseInfo appUseInfo = cursorToComment(cursor);
            result.add(appUseInfo);
            cursor.moveToNext();
        }
        cursor.close();
        return result;
    }

    private AppUseInfo cursorToComment(Cursor cursor){
        AppUseInfo result = new AppUseInfo();
        result.app_name = cursor.getString(0);
        result.access_time = cursor.getString(1);
        result.use_time = cursor.getLong(2);
        return result;
    }
}
