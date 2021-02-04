package com.cake.mcakeapp.error;

import com.cake.mcakeapp.MyApplication;
import com.cake.mcakeapp.R;

public class ErrorData {

    private static final String ACCOUNT_DOES_NOT_EXIST = "The password is invalid or the user does not have a password.";

    private static final String EMAIL_NOT_RIGHT = "The email address is badly formatted";

    private static final String USER_MAY_DELETE = "There is no user record corresponding to this identifier. The user may have been deleted.";

    public static String getErrorMessage(String message){
        if (message.contains(ACCOUNT_DOES_NOT_EXIST) || message.contains(USER_MAY_DELETE)){
            return MyApplication.getInstance().getApplicationContext().getString(R.string.account_does_not_exist);
        }else if (message.contains(EMAIL_NOT_RIGHT)){
            return MyApplication.getInstance().getApplicationContext().getString(R.string.email_not_right);
        }
        return "";
    }

}
