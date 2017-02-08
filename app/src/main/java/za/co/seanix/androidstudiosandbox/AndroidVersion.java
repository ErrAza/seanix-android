package za.co.seanix.androidstudiosandbox;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Sean on 2/3/2017.
 */

public class AndroidVersion {

    public String version_name;

    public String version_code;

    public AndroidVersion(String name, String code)
    {
        version_name = name;
        version_code = code;
    }

}
