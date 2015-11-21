package com.tabinsights.constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by biphilip on 10/3/15.
 */
public class WhitelisteedApps {
    public List<String> getAppNames(){
        AppNames[] appNames = AppNames.class.getEnumConstants();
        List<String> appNamesStr = new ArrayList<String>();
        for(int i=0; i<appNames.length;i++){
            appNamesStr.add(appNames[i].name());
        }

        return appNamesStr;
    }

    public List<String> getPackageNames(){
        AppNames[] appNames = AppNames.class.getEnumConstants();
        List<String> appNamesStr = new ArrayList<String>();
        for(int i=0; i<appNames.length;i++){
            appNamesStr.add(appNames[i].toString());
        }

        return appNamesStr;
    }


}
