package radoslaw.slowinski.ares.entites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import radoslaw.slowinski.ares.handlers.GameContactListener;
import radoslaw.slowinski.ares.utils.Assets;
import radoslaw.slowinski.ares.utils.Constant;
import radoslaw.slowinski.ares.utils.SkinTypes;

import static radoslaw.slowinski.ares.utils.Constant.INIT_MOVEMENT_SPEED;
import static radoslaw.slowinski.ares.utils.Constant.PPM;

/**
 * Created by ares on 12.08.17.
 */
public class Player {
    public static final float spriteScaleX = 0.40f;
    public static final float spriteScaleY = 0.45f;
    private final float animationDelay = 1 / 14f;
    private TextureRegion currentPlayerTexture;
    private Vector2 startingPos;
    private Vector2 size;
    private Body body;
    private FixtureDef fixtureDef;
    private World world;
    private TextureRegion[] walkTextures;
    private TextureRegion jumpTexture;
    private TextureRegion standTexture;
    private float time;
    private int currentFrame;
    private Vector2 linearVelocity;

    public Player(World world, Vector2 startingPos, SkinTypes type) {
        this.world = world;
        this.startingPos = startingPos;

        linearVelocity = new Vector2(INIT_MOVEMENT_SPEED, 0);
        fixtureDef = new FixtureDef();
        size = new Vector2();

        handlePlayerSkin(type);
        handlePlayerWithBox2D();
    }

    private void handlePlayerSkin(SkinTypes types) {
        applyPlayerSkin(Assets.instance.playerSkin.get(types));
        size.set(currentPlayerTexture.getRegionWidth(),
                currentPlayerTexture.getRegionHeight());
    }

    private void applyPlayerSkin(Assets.AssetPlayerSkin skin) {
        walkTextures = skin.walk;
        jumpTexture = skin.jump;
        standTexture = skin.stand;

        currentPlayerTexture = standTexture;
    }

    public void jump() {
        if (!isPlayerJumping()) {
            body.setLinearVelocity(linearVelocity);
            body.applyForceToCenter(0, 2000, true);
        }
        handlePlayerBeingStuck();
    }

    private boolean isPlayerJumping() {
        return body.getLinearVelocity().y != 0;
    }

    private void updatePlayerTexture(float deltaTime) {
        if (!GameContactListener.instance.isPlayerOnGround()) {
            currentPlayerTexture = jumpTexture;
        } else {
            handlePlayerWalkAnimation(deltaTime);
        }
    }

    public void update(float deltaTime){
        updatePlayerTexture(deltaTime);
        handlePlayerBeingStuck();
        handleDead();
    }

    private void handlePlayerWalkAnimation(float deltaTime) {
        time += deltaTime;
        while (time >= animationDelay) {
            stepAnimation();
        }
    }

    private void stepAnimation() {
        time -= animationDelay;
        if (body.getLinearVelocity().x >= linearVelocity.x * 0.5f)
            currentPlayerTexture = walkTextures[(currentFrame++) % walkTextures.length];
        else
            currentPlayerTexture = standTexture;
    }

    private void handlePlayerWithBox2D() {
        createPlayerBodyDef();
        createPlayerFixtureDef();
        createSensorFixtureDef();
    }

    private void createSensorFixtureDef() {
        PolygonShape shape = new PolygonShape();

        shape.setAsBox(size.x * 0.25f / PPM / 2, size.y * 0.05f / PPM, new Vector2(size.x * 0.08f / 2 / PPM, -size.y * 0.38f / 2 / PPM), 0);
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        fixtureDef.filter.categoryBits = Constant.BIT_PLAYER;
        fixtureDef.filter.maskBits = Constant.BIT_BLUE_BLOCK |
                Constant.BIT_GREEN_BLOCK |
                Constant.BIT_RED_BLOCK |
                Constant.BIT_COIN;

        body.createFixture(fixtureDef).setUserData(Constant.DATA_PLAYER_SENSOR);
        shape.dispose();
    }

    private void createPlayerFixtureDef() {
        PolygonShape shape = new PolygonShape();

        shape.setAsBox(size.x * 0.25f / PPM / 2, size.y * 0.38f / PPM / 2, new Vector2(size.x * 0.08f / 2 / PPM, 0f), 0);
        fixtureDef.shape = shape;
        fixtureDef.density = 60;
        fixtureDef.isSensor = false;
        fixtureDef.friction = 0.4f;
        fixtureDef.filter.categoryBits = Constant.BIT_PLAYER;
        fixtureDef.filter.maskBits = Constant.BIT_BLUE_BLOCK | Constant.BIT_GREEN_BLOCK | Constant.BIT_RED_BLOCK;

        body.createFixture(fixtureDef).setUserData(Constant.DATA_PLAYER);
        shape.dispose();
    }

    private void createPlayerBodyDef() {
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.position.set(startingPos);
        bdef.fixedRotation = true;
        bdef.linearVelocity.set(linearVelocity);
        body = world.createBody(bdef);
    }

    public TextureRegion getTexture() {
        return currentPlayerTexture;
    }

    public float getTextureX() {
        return body.getPosition().x * PPM - size.x * spriteScaleX / 2 + 2;
    }

    public float getTextureY() {
        return body.getPosition().y * PPM - size.x * spriteScaleY / 2 - 5;
    }

    public float getTextureWidth() {
        return size.x * spriteScaleX;
    }

    public float getTextureHeight() {
        return size.y * spriteScaleY;
    }

    public Body getBody() {
        return body;
    }

    private void handleDead() {
        if (body.getPosition().y <= 0) {
            // TODO handle player death
            Gdx.app.exit();
        }
    }

    private void handlePlayerBeingStuck() {
        if (body.getLinearVelocity().x < linearVelocity.x)
            body.setLinearVelocity(linearVelocity.x, body.getLinearVelocity().y);
    }

    public void setLinearVelocity(float x, float y) {
        linearVelocity.set(x, y);
    }
}
