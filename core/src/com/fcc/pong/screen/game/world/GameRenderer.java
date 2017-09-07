package com.fcc.pong.screen.game.world;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.fcc.pong.assets.AssetDescriptors;
import com.fcc.pong.assets.RegionNames;
import com.fcc.pong.config.GameConfig;
import com.fcc.pong.entity.Ball;
import com.fcc.pong.entity.Paddle;
import com.fcc.util.GdxUtils;
import com.fcc.util.Validate;
import com.fcc.util.debug.DebugCameraController;
import com.fcc.util.debug.ShapeRendererUtils;
import com.fcc.util.entity.EntityBase;
import com.fcc.util.viewport.ViewportManager;
import com.fcc.util.viewport.ViewportUtils;

/**
 * Project: Pong_V4
 * Created by fromCharCode on 25.08.2017.
 */
public class GameRenderer implements Disposable {

    // == constants ==
    private static final float PADDING = 80f;

    // == attributes ==
    private final GameWorld gameWorld;
    private final SpriteBatch batch;
    private final AssetManager assetManager;
    private final ViewportManager viewportManager;
    private final GlyphLayout layout = new GlyphLayout();

    private OrthographicCamera camera;
    private Viewport worldViewport;
    private Viewport hudViewport;
    private ShapeRenderer shapeRenderer;
    private DebugCameraController debugCameraController;

    private BitmapFont scoreFont;

    private TextureRegion backgroundRegion;
    private TextureRegion ballRegion;
    private TextureRegion leftPaddleRegion;
    private TextureRegion rightPaddleRegion;

    // == constructors ==
    public GameRenderer(GameWorld gameWorld, SpriteBatch batch, AssetManager assetManager, ViewportManager viewportManager) {
        this.gameWorld = gameWorld;
        this.batch = batch;
        this.assetManager = assetManager;
        this.viewportManager = viewportManager;
        init();
    }

    // == init ==
    private void init(){
        camera = viewportManager.getWorldCamera();
        worldViewport = viewportManager.getWorldViewport();
        hudViewport = viewportManager.getHudViewport();
        shapeRenderer = new ShapeRenderer();

        scoreFont = assetManager.get(AssetDescriptors.FONT);

        TextureAtlas gamePlayAtlas = assetManager.get(AssetDescriptors.GAME_PLAY);
        backgroundRegion = gamePlayAtlas.findRegion(RegionNames.BACKGROUND);
        ballRegion = gamePlayAtlas.findRegion(RegionNames.BALL);
        leftPaddleRegion = gamePlayAtlas.findRegion(RegionNames.PADDLE_LEFT);
        rightPaddleRegion = gamePlayAtlas.findRegion(RegionNames.PADDLE_RIGHT);

        debugCameraController = new DebugCameraController();
        debugCameraController.setStartPosition(GameConfig.WORLD_CENTER_X, GameConfig.WORLD_CENTER_Y);
    }

    // == public methods ==
    public void render(float delta){
        // handle debug camera input
        //debugCameraController.handleDebugInput(delta);    // NOTE: keep in mind that debugCameraControls are WASD. leftPaddle is WS
        debugCameraController.applyTo(camera);

        // clear screen
        GdxUtils.clearScreen();

        // render game play
        renderGamePlay();

        // render debug
        //renderDebug();

        // render hud
        renderHud();
    }

    public void resize(int width, int height){
        viewportManager.resize(width, height);
        viewportManager.debugPixelsPerUnit();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }

    // private methods ==
    private void renderGamePlay(){
        worldViewport.apply();

        batch.setProjectionMatrix(viewportManager.getWorldCombined());
        batch.begin();

        drawGamePlay();

        batch.end();
    }

    private void drawGamePlay(){
        // background
        batch.draw(backgroundRegion, 0, 0, GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT);

        // ball
        Ball ball = gameWorld.getBall();
        drawEntity(batch, ballRegion, ball);

        // left paddle
        Paddle leftPaddle = gameWorld.getLeftPaddle();
        drawEntity(batch, leftPaddleRegion, leftPaddle);

        // right paddle
        Paddle rightPaddle = gameWorld.getRightPaddle();
        drawEntity(batch, rightPaddleRegion, rightPaddle);
    }

    private void renderDebug(){
        worldViewport.apply();
        if(gameWorld.isDrawGrid()){
            ViewportUtils.drawGrid(worldViewport, shapeRenderer);
        }

        if(!gameWorld.isDrawDebug()){
            return;
        }

        Color oldColor = shapeRenderer.getColor().cpy();

        shapeRenderer.setProjectionMatrix(viewportManager.getWorldCombined());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        drawDebug();

        shapeRenderer.end();
        shapeRenderer.setColor(oldColor);
    }

    private void drawDebug(){
        // left paddle
        shapeRenderer.setColor(Color.GREEN);
        Polygon leftPaddleBounds = gameWorld.getLeftPaddle().getBounds();
        ShapeRendererUtils.polygon(shapeRenderer, leftPaddleBounds);

        // right paddle
        Polygon rightPaddleBounds = gameWorld.getRightPaddle().getBounds();
        ShapeRendererUtils.polygon(shapeRenderer, rightPaddleBounds);

        // ball
        shapeRenderer.setColor(Color.RED);
        Polygon ballBounds = gameWorld.getBall().getBounds();
        ShapeRendererUtils.polygon(shapeRenderer, ballBounds);
    }

    private void renderHud(){
        hudViewport.apply();
        Color oldColor = batch.getColor().cpy();

        batch.setProjectionMatrix(viewportManager.getHudCombined());
        batch.begin();

        drawHud();

        batch.end();
        batch.setColor(oldColor);
    }

    private void drawHud(){
        String rightScoreString = "" + gameWorld.getRightScore();
        String leftScoreString = "" + gameWorld.getLeftScore();

        // right score
        layout.setText(scoreFont, rightScoreString);
        float scoreY = GameConfig.HUD_HEIGHT - layout.height;
        float rightScoreX = (GameConfig.HUD_WIDTH/2f) + (PADDING);
        scoreFont.draw(batch, layout, rightScoreX , scoreY);

        // left score
        layout.setText(scoreFont, leftScoreString);
        float leftScoreX = (GameConfig.HUD_WIDTH/2f) - (layout.width + PADDING);
        scoreFont.draw(batch, layout, leftScoreX, scoreY);

        // controls
        if (gameWorld.getBall().isNotActive() && (gameWorld.getRound() == 0)){
            String leftUp = "W";
            String leftDown = "S";

            String rightUp = "UP";
            String rightDown = "DOWN";

            String space = "PRESS SPACE FOR START/PAUSE";

            float leftX = 64f;
            float rightX = GameConfig.HUD_WIDTH - 100f;
            float yPad = 150f;
            float topY = GameConfig.HUD_HEIGHT/2f + yPad;
            float bottomY = GameConfig.HUD_HEIGHT/2f - yPad;

            // top left
            layout.setText(scoreFont, leftUp);
            scoreFont.draw(batch, layout, leftX, topY);

            // bottom left
            layout.setText(scoreFont, leftDown);
            scoreFont.draw(batch, layout, leftX, bottomY);

            // top right
            layout.setText(scoreFont, rightUp);
            scoreFont.draw(batch, layout, rightX, topY);

            // bottom right
            layout.setText(scoreFont, rightDown);
            scoreFont.draw(batch, layout, rightX, bottomY);

            // space
            layout.setText(scoreFont, space);
            float spaceX = (GameConfig.HUD_WIDTH - layout.width)/2f;
            float spaceY = layout.height + 15f;
            scoreFont.draw(batch, layout, spaceX, spaceY);

        }

        if(gameWorld.isPaused()){
            layout.setText(scoreFont, "PAUSED");
            float x = (GameConfig.HUD_WIDTH - layout.width)/2f;
            float y = (GameConfig.HUD_HEIGHT - layout.height)/2f;
            scoreFont.draw(batch, layout, x, y);
        }
    }

    // == static methods ==
    private static void drawEntity(SpriteBatch batch, TextureRegion region, EntityBase entity){
        Validate.notNull(batch);
        Validate.notNull(region);
        Validate.notNull(entity);

        batch.draw(region, entity.getX(), entity.getY(), entity.getWidth(), entity.getHeight());
    }
}
