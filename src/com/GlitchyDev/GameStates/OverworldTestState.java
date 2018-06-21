package com.GlitchyDev.GameStates;

import com.GlitchyDev.IO.AssetLoader;
import com.GlitchyDev.MMBN_Game;
import com.GlitchyDev.Utility.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;


public class OverworldTestState extends BasicControllerGameState {
    protected int x = 80;
    protected int y = 120;
    protected boolean isMoving = false;
    protected boolean isRunning = false;
    protected Direction previousDirection = Direction.SOUTH;
    protected Direction currentDirection = Direction.SOUTH;

    private final int playerHeightOffset = 8;


    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) {
        super.init(gameContainer, stateBasedGame);


    }

    @Override
    public void doRender(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {

        graphics.setColor(new Color(1.0f / 256 * 64, 1.0f / 256 * 16, 1.0f / 256 * 80, 1.0f));
        graphics.fillRect(0, 0, gameContainer.getWidth(), gameContainer.getHeight());


        SpriteUtil.drawSprite("Lans_Bedroom", -x + MMBN_Game.WIDTH / 2, -y + MMBN_Game.HEIGHT / 2 + playerHeightOffset, 1.0f);

        drawLan();

        SpriteUtil.drawSprite("Lans_Bedroom_Collision", -x + MMBN_Game.WIDTH / 2, -y + MMBN_Game.HEIGHT / 2 + playerHeightOffset, 1.0f);

        showCollisions();

        graphics.setColor(Color.green);
        graphics.drawString("X :" + x + " Y: " + y,300,0);


    }


    public void drawLan() {

        Direction direction = currentDirection;
        if(direction == Direction.NONE) {
            direction = previousDirection;
        }
        Direction displayDirection = direction;
        boolean flip = false;
        if(direction.requiresFlip()) {
            displayDirection = direction.mirror();
            flip = true;
        }
        previousDirection = direction;


        if(isMoving) {
             if(isRunning) {
                 SpriteUtil.drawBottemCenteredSprite("Shadow_" + direction.getDirectionValue(getCurrentInputMapping()) % 18/6, MMBN_Game.WIDTH / 2, MMBN_Game.HEIGHT / 2 + 7, 1.0f,flip);
                 SpriteUtil.drawBottemCenteredSprite("Lan_Running_" + displayDirection + "_" + direction.getDirectionValue(getCurrentInputMapping()) % 48/6, MMBN_Game.WIDTH / 2, MMBN_Game.HEIGHT / 2 + playerHeightOffset, 1.0f,flip);
             }
             else {
                 SpriteUtil.drawBottemCenteredSprite("Shadow_" + direction.getDirectionValue(getCurrentInputMapping()) % 18/6, MMBN_Game.WIDTH / 2, MMBN_Game.HEIGHT / 2 + 8, 1.0f,flip);
                 SpriteUtil.drawBottemCenteredSprite("Lan_Walking_" + displayDirection + "_" + direction.getDirectionValue(getCurrentInputMapping()) % 36/6, MMBN_Game.WIDTH / 2, MMBN_Game.HEIGHT / 2 + playerHeightOffset, 1.0f,flip);
             }
         }
         else {
             // 39 pixels tall
            SpriteUtil.drawBottemCenteredSprite("Shadow_2", MMBN_Game.WIDTH / 2, MMBN_Game.HEIGHT / 2 + 8, 1.0f,flip);
            SpriteUtil.drawBottemCenteredSprite("Lan_Standing_" + displayDirection, MMBN_Game.WIDTH / 2, MMBN_Game.HEIGHT / 2 + playerHeightOffset, 1.0f,flip);
         }

    }

    @Override
    public void doUpdate(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) {
        if(currentDirection != Direction.NONE) {
            previousDirection = currentDirection;
        }
        currentDirection = Direction.getDirection(getCurrentInputMapping());
        isMoving = currentDirection != Direction.NONE;
        isRunning = getCurrentInputMapping()[GButtons.B.ordinal()] >= 1;

        handleMovement();

    }

    public void handleMovement() {

        if (currentDirection.getDirectionValue(getCurrentInputMapping()) > 1) {
            switch (currentDirection) {
                case NORTH:
                case EAST:
                case SOUTH:
                case WEST:
                    x += currentDirection.getX() * (isRunning ? 2 : 1);
                    y += currentDirection.getY() * (isRunning ? 2 : 1);
                    correctExitBounds();
                    if(detectCollisions())
                    {
                        x -= currentDirection.getX() * (isRunning ? 2 : 1);
                        y -= currentDirection.getY() * (isRunning ? 2 : 1);
                    }
                    break;
                case NORTHEAST:
                case NORTHWEST:
                case SOUTHEAST:
                case SOUTHWEST:
                    if (isRunning || (currentDirection.getDirectionValue(getCurrentInputMapping()) - 1) % 2 == 0) {
                        x += currentDirection.getX();
                        y += currentDirection.getY();
                    }
                    correctExitBounds();
                    if(detectCollisions())
                    {
                        x -= currentDirection.getX();
                        y -= currentDirection.getY();
                    }
                    break;
                default:
            }
        }

    }


    public void correctExitBounds() {
        x = x < 0 ? 0 : x;
        y = y < 0 ? 0 : y;
        x = x > AssetLoader.getSprite("Lans_Bedroom").getWidth() ? AssetLoader.getSprite("Lans_Bedroom").getWidth() : x;
        y = y > AssetLoader.getSprite("Lans_Bedroom").getHeight() ? AssetLoader.getSprite("Lans_Bedroom").getHeight() : y;
    }

    public boolean detectCollisions()
    {
        if(getCurrentInputMapping()[GButtons.R.ordinal()] >= 1) {

        }
        else
        {
            switch(currentDirection)
            {
                case NORTH:
                case EAST:
                case SOUTH:
                case WEST:
                case NORTHEAST:
                case NORTHWEST:
                case SOUTHEAST:
                case SOUTHWEST:
                    break;
                default:
            }
        }

        return false;
        /*
        if(getCurrentInputMapping()[GButtons.R.ordinal()] >= 1) {
            return false;
        }
        return AssetLoader.getSprite("Lans_Bedroom_Collision").getColor(x,y).a != 0.0f;
        */
    }

    public float getGridSection(Direction direction, int x, int y)
    {
        switch(currentDirection)
        {
            case NORTH:
                return AssetLoader.getSprite("Lans_Bedroom_Collision").getColor(x,y-1).a;
            case EAST:
            case SOUTH:
            case WEST:
            case NORTHEAST:
            case NORTHWEST:
            case SOUTHEAST:
            case SOUTHWEST:
                break;
            default:
        }    }




    public void showCollisions()
    {

        if(AssetLoader.getSprite("Lans_Bedroom_Collision").getColor(x,y).a == 0.0f)
        {
            SpriteUtil.drawSprite("PET_1",0,0,1.0f);
        }
        else
        {
            SpriteUtil.drawSprite("PET_2",0,0,1.0f);
        }
    }


    @Override
    public int getID() {
        return GameStateType.OVERWORLD.ordinal();
    }
}
