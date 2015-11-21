package tabinsights.database;

/**
 * Created by pushkar on 10/3/15.
 */
public class AppUseInfo {
    public String app_name;
    public String access_time;
    public long use_time;

    @Override
    public String toString(){
        return new String(access_time + "   " + "Dummy-device-id" +"   " +
                app_name + "    " + Long.toString(use_time));
    }
}
