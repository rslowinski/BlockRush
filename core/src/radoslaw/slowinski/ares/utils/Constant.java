package radoslaw.slowinski.ares.utils;

/**
 * Created by ares on 12.08.17.
 */
public class Constant {
    // general settings
    public static final boolean DEBUG_BOX2D = false;
    public static final int GAME_WIDTH = 600;
    public static final int GAME_HEIGHT = 300;
    public static final int SCALE = 2;
    public static final float PPM = 100.0f; // pixel per meter

    // gameplay settings
    public static final int REWARD_FOR_COIN = 25;
    public static final float INIT_MOVEMENT_SPEED = 1.5f;

    // map bit filters
    public static final short BIT_PLAYER = 1;
    public static final short BIT_RED_BLOCK = 2;
    public static final short BIT_BLUE_BLOCK = 4;
    public static final short BIT_GREEN_BLOCK = 8;
    public static final short BIT_COIN = 16;

    // data for box2d
    public static final String DATA_RED_BLOCK = "red block";
    public static final String DATA_BLUE_BLOCK = "blue block";
    public static final String DATA_GREEN_BLOCK = "green block";
    public static final String DATA_PLAYER_SENSOR = "player sensor";
    public static final String DATA_COIN = "coin";

    // paths to assets
    public static final String ARIAL_FONT = "images/uiSkins/arial-15.fnt";
    public static final String MENU_UI_SKIN = "images/uiSkins/menuUI.pack.atlas";
    public static final String LIBGDX_SKIN = "images/uiSkins/uiskin.atlas";
    public static final String LIBGDX_SKIN_JSON = "images/uiSkins/uiskin.json";
    public static final String TEXTURE_ATLAS_PLAYERS = "images/players/playerSkins.pack.atlas";
    public static final String TEXTURE_ATLAS_ITEMS = "images/items/items.pack.atlas";

    public static final String SOUND_JUMP = "audio/jumpSound.mp3";
    public static final String SOUND_COIN = "audio/coinSound.wav";
    public static final String MUSIC_BACKGROUND = "audio/background.ogg";
    public static final String PREFERENCES = "radoslaw.slowinski.ares.prefs";

}
