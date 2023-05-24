package com.example.captaincat.Utils.cache;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.captaincat.information.Config.Config;

public class SharePreferenceUtil {
    static SharedPreferences sp;

    public static void init(Context context, String name) {
        sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    private static final String USER_Id = "user_Id";
    public static void setUserId(String userId) {
        if (null != sp) {
            sp.edit().putString(USER_Id, userId).apply();
        }
    }
    public static String getUserId() {
        if (null != sp) {
            return sp.getString(USER_Id,Config.NO_USER);
        }
        return Config.NO_USER;
    }
    private static final String IS_AUTO_LOGIN = "is_auto_login";
    public static void setAutoLogin(boolean isAuto){
        if(null!=sp)
            sp.edit().putBoolean(IS_AUTO_LOGIN,isAuto).apply();
    }
    public static Boolean getIsAutoLogin() {
        if (null != sp) {
            return sp.getBoolean(IS_AUTO_LOGIN, false);
        }
        return false;
    }

    private static final String UserId_For_AutoLogin = "password_for_auto_login";
    public static String getRememberUserId(){
        if (null != sp) {
            return sp.getString(UserId_For_AutoLogin, null);
        }
        return null;
    }
    public static void setRememberUserId(String UserId) {
        if (null != sp) {
            sp.edit().putString(UserId_For_AutoLogin, UserId).apply();
        }
    }

    private static final String USER_PASSWORD = "user_psw";

    public static void setCachedPsw(String psw) {
        if (null != sp) {
            sp.edit().putString(USER_PASSWORD, psw).apply();
        }
    }

    public static String getCachedPsw() {
        if (null != sp) {
            return sp.getString(USER_PASSWORD, null);
        }
        return null;
    }


    private static final String ADD_FRIEND_ITEM = "item";

    public static void setItem(Long pass) {
        if (null != sp) {
            sp.edit().putLong(ADD_FRIEND_ITEM, pass).apply();
        }
    }

    public static Long getItem() {
        if (null != sp) {
            return sp.getLong(ADD_FRIEND_ITEM, 0);
        }
        return null;
    }

    private static final String KEY_CACHED_AVATAR_PATH = "jchat_cached_avatar_path";

    public static void setCachedAvatarPath(String path) {
        if (null != sp) {
            sp.edit().putString(KEY_CACHED_AVATAR_PATH, path).apply();
        }
    }

    public static String getCachedAvatarPath() {
        if (null != sp) {
            return sp.getString(KEY_CACHED_AVATAR_PATH, null);
        }
        return null;
    }

    private static final String CONVERSATION_TOP = "conversation_top";

    public static void setTopSize(int num) {
        if (null != sp) {
            sp.edit().putInt(CONVERSATION_TOP, num).apply();
        }
    }

    public static int getTopSize() {
        if (null != sp) {
            return sp.getInt(CONVERSATION_TOP, 0);
        }
        return 0;
    }

    private static final String CONVERSATION_TOP_CANCEL = "conversation_top_cancel";

    public static void setCancelTopSize(int num) {
        if (null != sp) {
            sp.edit().putInt(CONVERSATION_TOP_CANCEL, num).apply();
        }
    }

    public static int getCancelTopSize() {
        if (null != sp) {
            return sp.getInt(CONVERSATION_TOP_CANCEL, 0);
        }
        return 0;
    }


    private static final String KEY_REGISTER_AVATAR_PATH = "jchat_register_avatar_path";

    public static void setRegisterAvatarPath(String path) {
        if (null != sp) {
            sp.edit().putString(KEY_REGISTER_AVATAR_PATH, path).apply();
        }
    }

    public static String getRegisterAvatarPath() {
        if (null != sp) {
            return sp.getString(KEY_REGISTER_AVATAR_PATH, null);
        }
        return null;
    }

    private static final String KEY_CACHED_FIX_PROFILE_FLAG = "fixProfileFlag";

    public static void setCachedFixProfileFlag(boolean value) {
        if(null != sp){
            sp.edit().putBoolean(KEY_CACHED_FIX_PROFILE_FLAG, value).apply();
        }
    }

    public static boolean getCachedFixProfileFlag(){
        return null != sp && sp.getBoolean(KEY_CACHED_FIX_PROFILE_FLAG, false);
    }

    private static final String NO_DISTURB = "no_disturb";

    public static void setNoDisturb(boolean value) {
        if(null != sp){
            sp.edit().putBoolean(NO_DISTURB, value).apply();
        }
    }

    public static boolean getNoDisturb(){
        return null != sp && sp.getBoolean(NO_DISTURB, false);
    }

    private static final String IS_SHOWCHECK = "isShowCheck";

    public static void setShowCheck(boolean value) {
        if(null != sp){
            sp.edit().putBoolean(NO_DISTURB, value).apply();
        }
    }

    public static boolean getShowCheck(){
        return null != sp && sp.getBoolean(NO_DISTURB, false);
    }

    private static final String ISOPEN = "isopen";

    public static void setIsOpen(boolean value) {
        if(null != sp){
            sp.edit().putBoolean(ISOPEN, value).apply();
        }
    }

    public static boolean getIsOpen(){
        return null != sp && sp.getBoolean(ISOPEN, false);
    }

    private static final String SOFT_KEYBOARD_HEIGHT = "SoftKeyboardHeight";

    public static void setCachedKeyboardHeight(int height){
        if(null != sp){
            sp.edit().putInt(SOFT_KEYBOARD_HEIGHT, height).apply();
        }
    }

    public static int getCachedKeyboardHeight(){
        if(null != sp){
            return sp.getInt(SOFT_KEYBOARD_HEIGHT, 0);
        }
        return 0;
    }

    private static final String WRITABLE_FLAG = "writable";
    public static void setCachedWritableFlag(boolean value){
        if(null != sp){
            sp.edit().putBoolean(WRITABLE_FLAG, value).apply();
        }
    }

    public static boolean getCachedWritableFlag(){
        return null == sp || sp.getBoolean(WRITABLE_FLAG, true);
    }

    private static final String CACHED_APP_KEY = "CachedAppKey";

    public static void setCachedAppKey(String appKey) {
        if (null != sp) {
            sp.edit().putString(CACHED_APP_KEY, appKey).apply();
        }
    }

    public static String getCachedAppKey() {
        if (null != sp) {
            return sp.getString(CACHED_APP_KEY, "default");
        }
        return "default";
    }

    private static final String CACHED_NEW_FRIEND = "CachedNewFriend";

    public static void setCachedNewFriendNum(int num) {
        if (null != sp) {
            sp.edit().putInt(CACHED_NEW_FRIEND, num).apply();
        }
    }

    public static int getCachedNewFriendNum() {
        if (null != sp) {
            return sp.getInt(CACHED_NEW_FRIEND, 0);
        }
        return 0;
    }


    /*
    存经度和拿
     */
    public static final String Long_itude = "Longitude";
    public static void setLongitude(String Longitude) {
        if (null != sp) {
            sp.edit().putString(Long_itude, Longitude).apply();
        }
    }
    public static String getLongitude(){
        if (null != sp) {
            return sp.getString(Long_itude,"default");
        }
        return "default";
    }

    /*
    存纬度和拿
     */
    public static final String Lat_itude = "Latitude";
    public static void setLatitude(String Latitude) {
        if (null != sp) {
            sp.edit().putString(Lat_itude, Latitude).apply();
        }
    }
    public static String getLatitude(){
        if (null != sp) {
            return sp.getString(Lat_itude,"default");
        }
        return "default";
    }

    /**
     * 存地址和拿
     */

    public static final String present_loa = "presentloa";
    public static void setPresentloa(String locaName) {
        if (null != sp) {
            sp.edit().putString(present_loa, locaName).apply();
        }
    }
    public static String getPresentloa(){
        if (null != sp) {
            return sp.getString(present_loa,"default");
        }
        return "default";
    }

    public static final String loacal_name = "loacalname";
    public static void setPresentloacalname(String locaName) {
        if (null != sp) {
            sp.edit().putString(loacal_name, locaName).apply();
        }
    }
    public static String getPresentloacalname(){
        if (null != sp) {
            return sp.getString(loacal_name,"default");
        }
        return "default";
    }
}
