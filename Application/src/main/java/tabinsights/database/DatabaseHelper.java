package tabinsights.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by pushkar on 10/3/15.
 */
public class DatabaseHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "TabInsights";
    private static final int DATABASE_VERSION = 1;
    public static final String table_name = "App_use_info";
    public static final String app_col_name = "app_name";
    public static final String access_time_col_name = "access_time";
    public static final String use_time_col_name = "use_time";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + table_name + " (" + app_col_name + " text not null, " +
                access_time_col_name +" text not null, " +
                use_time_col_name + " float not null);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + table_name);
        onCreate(db);
    }
}
