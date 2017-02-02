package za.co.seanix.androidstudiosandbox;


/**
 * Created by Sean on 2/2/2017.
 */

public class PasswordAuthentication {


    public static String GenerateNewSalt(int num)
    {
        return BCrypt.gensalt(num);
    }

    public static String Hash(String password, String salt)
    {
        return BCrypt.hashpw(password, salt);
    }

}
