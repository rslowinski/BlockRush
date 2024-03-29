package radoslaw.slowinski.ares.controls;

import com.badlogic.gdx.graphics.Color;
import radoslaw.slowinski.ares.RushGame;
import radoslaw.slowinski.ares.handlers.UserDataHandler;
import radoslaw.slowinski.ares.utils.IOnclickCallback;
import radoslaw.slowinski.ares.utils.SkinTypes;

/**
 * Created by ares on 21/08/17.
 */
public class ChoosePlayerButton {
    SkinTypes playerSkin;
    private MyButton button;
    private RushGame myGame;

    public ChoosePlayerButton(SkinTypes playerSkin, RushGame myGame) {
        this.playerSkin = playerSkin;
        this.myGame = myGame;
        init();
    }

    private void init() {
        button = new MyButton();
        button.setString("choose", Color.GOLDENROD);
        button.setListener(getListener());
        button.setDrawableUp(button.menuUI.getDrawable("button_05"));
        button.setDrawableDown(button.menuUI.getDrawable("button_03"));
    }

    private IOnclickCallback getListener() {
        return new IOnclickCallback() {
            @Override
            public void onClick() {
                UserDataHandler.instance.setPlayerSkin(playerSkin);
                myGame.setMenuScreen();
            }
        };
    }

    public MyButton getButton() {
        return button;
    }
}
