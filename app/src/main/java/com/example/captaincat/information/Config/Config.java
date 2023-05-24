package com.example.captaincat.information.Config;

public class Config {

  public static final String bgMusicPlay  = "BG_MUSIC_SHOULD_PLAY";
  public static final String shootMusicPlay  = "SHOOT_MUSIC_SHOULD_PLAY";
  public static final String tomcatUrl = "http://43.139.19.8:8080/";
  public static final String baseUrl = "http://43.139.19.8:8091/";
  public static final String musicSourceUrl = tomcatUrl + "uploads/cato352423/captain/music/";
  public static final String objSourceUrl = tomcatUrl + "uploads/cato352423/captain/object/";

  final public static  int TO_LOGIN = 0;
  final public static  int TO_MAIN = 1;
    public static final int MAX_LIFE = 17;
    public static final int MAX_LEVEL = 18;
    public static final int MAX_SPEED = 6;
    public static final int MAX_FIRE = 6;
    //单位:应该是px
   public static int SCREENWID ;
   public static int SCREENHEI ;
   public static int MAX_HEIGHT ;
   public static int MAX_WIDTH ;
   public static int HIGHEST_PLACE;
   public static int LOWEST_PLACE;

   final public static String NO_USER= "no_user_json";


   static public final String KEY_LIFE = "lifeLevel";
   static public final String KEY_SPEED = "speedLevel";

   static public final int LIFE_ID = 101;
   static public final int SPEED_ID = 102;


   final public static int TOUCH_LEFT = 0;
   final public static int TOUCH_RIGHT = 1;
   final public static int TOUCH_TOP = 2;
   final public static int TOUCH_BOTTOM = 3;
   final public static int NOT_TOUCHED = 100;
   final public static int GO_LEFT = -1;
   final public static int GO_RIGHT = 1;

   final public static int DAY_TIME = 0;
   final public static int DUSK_TIME = 1;
   final public static int NIGHT_TIME = 2;

   final public static String CHOSEN_FIRES = "CHOSEN_FIRES";
   final public static String CHOSEN_LEVEL = "CHOSEN_LEVEL";
   final public static String MAIN_OR_CHALLENGE = "MAIN_OR_CHALLENGE";
   final public static int MAIN = 1;
   final public static int CHALLENGE = 2;

   final public  static  int REWARD = 1;
   final public  static  int MENU = 2;
   final public  static  int TAKES = 3;

   final public  static double maxFireNum = 300.00;
   public static final boolean QUICK_SHOOT = false;
}
