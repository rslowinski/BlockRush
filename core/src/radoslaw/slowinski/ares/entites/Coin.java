package radoslaw.slowinski.ares.entites;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import radoslaw.slowinski.ares.handlers.ScoreHandler;
import radoslaw.slowinski.ares.utils.Assets;
import radoslaw.slowinski.ares.utils.Constant;

import static radoslaw.slowinski.ares.utils.Constant.PPM;

/**
 * Created by ares on 15/08/17.
 */
public class Coin extends AbstractGameObject {

    public Coin(World world, Vector2 pos) {
        super(getBody(world, pos), Assets.instance.coins.coins);
        body.setUserData(this);
    }

    private static Body getBody(World world, Vector2 pos) {
        BodyDef cdef = new BodyDef();
        cdef.type = BodyDef.BodyType.StaticBody;
        cdef.position.set(pos);
        Body body = world.createBody(cdef);
        FixtureDef cfdef = new FixtureDef();
        CircleShape cshape = new CircleShape();
        cshape.setRadius(8 / PPM);
        cfdef.shape = cshape;
        cfdef.isSensor = true;
        cfdef.filter.categoryBits = Constant.BIT_COIN;
        cfdef.filter.maskBits = Constant.BIT_PLAYER;
        body.createFixture(cfdef).setUserData(Constant.DATA_COIN);
        cshape.dispose();
        return body;
    }

   public void onContact(){
       ScoreHandler.instance.addToCurrentLevelCoins(Constant.REWARD_FOR_COIN);
       System.out.println(ScoreHandler.instance.getCurrentLevelCoins());
       toDelete = true;
       // TODO perform coin sound
   }


}
