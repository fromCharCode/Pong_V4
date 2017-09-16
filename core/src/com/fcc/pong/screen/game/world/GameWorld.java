package com.fcc.pong.screen.game.world;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.fcc.pong.PongGame;
import com.fcc.pong.common.GameType;
import com.fcc.pong.common.SoundController;
import com.fcc.pong.config.GameConfig;
import com.fcc.pong.entity.Ball;
import com.fcc.pong.entity.EntityFactory;
import com.fcc.pong.entity.Paddle;
import com.fcc.pong.screen.menu.GameOverScreen;
import com.fcc.pong.script.AiPlayerScript;
import com.fcc.pong.script.LeftPlayerInputScript;
import com.fcc.pong.script.RightPlayerInputScript;
import com.fcc.util.game.GameBase;

/**
 * Project: Pong_V4
 * Created by fromCharCode on 25.08.2017.
 */
public class GameWorld {

    // == attributes ==
    private final SoundController soundController;
    private final GameBase game;
    private final EntityFactory factory;
    private final int maxRounds;

    private Paddle leftPaddle;
    private Paddle rightPaddle;
    private Ball ball;

    private boolean drawDebug = true;
    private boolean drawGrid = true;
    private boolean paused;

    private GameType gameType;

    private int leftScore;
    private int rightScore;
    private int round;

    private boolean isRightStart;

    // == constructors ==
    public GameWorld(GameBase game, EntityFactory factory, int maxRounds, GameType gameType) {
        soundController = (((PongGame) game).getSoundController());
        this.game = game;
        this.factory = factory;
        this.maxRounds = maxRounds;

        this.gameType = gameType;

        init();
    }

    // == init ==
    private void init(){
        ball = factory.createBall();
        leftPaddle = factory.createLeftPaddle();
        rightPaddle = factory.createRightPaddle();
        leftPaddle.addScript(new LeftPlayerInputScript());
        if(gameType.isSinglePlayer()){
            rightPaddle.setBall(ball);
            rightPaddle.addScript(new AiPlayerScript());
        } else if (gameType.isMultiPlayer()){
            rightPaddle.addScript(new RightPlayerInputScript());
        }
    }

    // == package private methods ==
    void update(float delta){

        if(round >= maxRounds){
            getWinner();
        }

        if(ball.isNotActive() || paused){
            return;
        }

        updatePaddles(delta);

        blockPaddleFromLeavingTheWorld(leftPaddle);
        blockPaddleFromLeavingTheWorld(rightPaddle);

        ball.update(delta);
        blockBallFromLeavingTheWorld();
        checkBallWithPaddleCollision(leftPaddle);
        checkBallWithPaddleCollision(rightPaddle);
    }

    void activateBall() {
        float startAngle = MathUtils.random(120, 240);
        if(isRightStart){
            startAngle = 180 -startAngle;
        }
        ball.setVelocity(startAngle, GameConfig.BALL_START_SPEED);
        paused = false;
    }

    Ball getBall() {
        return ball;
    }

    Paddle getLeftPaddle() {
        return leftPaddle;
    }

    Paddle getRightPaddle() {
        return rightPaddle;
    }

    boolean isDrawDebug() {
        return drawDebug;
    }

    boolean isDrawGrid() {
        return drawGrid;
    }

    boolean isPaused() {
        return paused;
    }

    void toggleDrawGrid(){
        drawGrid = !drawGrid;
    }

    void toggleDrawDebug(){
        drawDebug = !drawDebug;
    }

    void togglePaused(){
        paused = !paused;
    }

    int getRightScore() {
        return rightScore;
    }

    int getLeftScore() {
        return leftScore;
    }

    int getRound() {
        return round;
    }

    // == private methods ==
    private void updatePaddles(float delta) {
        leftPaddle.update(delta);
        rightPaddle.update(delta);
    }

    private void blockPaddleFromLeavingTheWorld(Paddle paddle){
        if(paddle.getY() <= 0){
            paddle.setY(0);
        } else if (paddle.getY() + paddle.getHeight() >= GameConfig.WORLD_HEIGHT){
            paddle.setY(GameConfig.WORLD_HEIGHT - paddle.getHeight());
        }
    }

    private void blockBallFromLeavingTheWorld(){
        // top
        float maxY = ball.getY() + ball.getHeight();
        if(maxY >= GameConfig.WORLD_HEIGHT){
            ball.setY(GameConfig.WORLD_HEIGHT - ball.getHeight());
            ball.multiplyVelocityY(-1);
        }

        // bottom
        if(ball.getY() <= 0){
            ball.setY(0);
            ball.multiplyVelocityY(-1);
        }

        //left
        if(ball.getX() <= 0){
            rightScore++;
            isRightStart = true;
            soundController.lose();
            round++;
            restart();
        }

        // right
        float maxX = ball.getX() + ball.getWidth();
        if(maxX >= GameConfig.WORLD_WIDTH){
            leftScore++;
            isRightStart = false;
            soundController.lose();
            round++;
            restart();
        }
    }

    private void checkBallWithPaddleCollision(Paddle paddle){
        Polygon ballBounds = ball.getBounds();
        Polygon paddleBounds = paddle.getBounds();

        if(Intersector.overlapConvexPolygons(ballBounds, paddleBounds)){
            float ballCenterY = ball.getY() + ball.getHeight()/2f;
            float percent = (ballCenterY - paddle.getY()) / paddle.getHeight(); // 0f - 1f
            // interpolate angle between 120 and 240
            float bounceAngle = 240 - percent * 120;
            ball.setVelocity(bounceAngle, ball.getSpeed() + GameConfig.BALL_SPEED_FACTOR);

            if(ball.getSpeed() >= GameConfig.BALL_MAX_SPEED){
                ball.setVelocity(bounceAngle, GameConfig.BALL_MAX_SPEED);
            }

            // TODO: when using pickups clamp minimum ball speed

            if(paddle.equals(leftPaddle)){
                soundController.hitLeft();
                ball.multiplyVelocityX(-1);
            } else {
                soundController.hitRight();
            }
        }
    }

    private void restart(){
        leftPaddle.setPosition(GameConfig.LEFT_PADDLE_START_X, GameConfig.PADDLE_START_Y);
        rightPaddle.setPosition(GameConfig.RIGHT_PADDLE_START_X, GameConfig.PADDLE_START_Y);
        ball.setPosition(GameConfig.BALL_START_X, GameConfig.BALL_START_Y);
        ball.stop();
    }

    private void getWinner(){
        String labelText;
        if(leftScore > rightScore){
            labelText = "left player wins!";
        } else if(rightScore > leftScore){
            labelText = "right player wins!";
        } else {
            labelText = "draw!";
        }

        game.setScreen(new GameOverScreen(game, labelText));
    }
}
