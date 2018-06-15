package com.GlitchyDev.GameStates;

import com.GlitchyDev.IO.AssetLoader;
import com.GlitchyDev.MMBN_Game;
import com.GlitchyDev.Utility.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;


public class OverworldTestState extends BasicControllerGameState {
    protected int x = 0;
    protected int y = 0;
    protected boolean isMoving = false;
    protected boolean isRunning = false;
    protected Direction previousDirection = Direction.SOUTH;
    protected Direction currentDirection = Direction.SOUTH;



    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) {
        super.init(gameContainer, stateBasedGame);


    }

    @Override
    public void doRender(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {

        graphics.setColor(new Color(1.0f / 256 * 64, 1.0f / 256 * 16, 1.0f / 256 * 80, 1.0f));
        graphics.fillRect(0, 0, gameContainer.getWidth(), gameContainer.getHeight());


        SpriteUtil.drawSprite("Lans_Bedroom", -x + MMBN_Game.WIDTH / 2, -y + MMBN_Game.HEIGHT / 2, 1.0f);

        drawLan();

        SpriteUtil.drawSprite("Lans_Bedroom_Collision", -x + MMBN_Game.WIDTH / 2, -y + MMBN_Game.HEIGHT / 2, 1.0f);

        showCollisions();




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
                 SpriteUtil.drawBottemCenteredSprite("Lan_Running_" + displayDirection + "_" + direction.getDirectionValue(getCurrentInputMapping()) % 48/6, MMBN_Game.WIDTH / 2, MMBN_Game.HEIGHT / 2 + 7, 1.0f,flip);
             }
             else {
                 SpriteUtil.drawBottemCenteredSprite("Shadow_" + direction.getDirectionValue(getCurrentInputMapping()) % 18/6, MMBN_Game.WIDTH / 2, MMBN_Game.HEIGHT / 2 + 8, 1.0f,flip);
                 SpriteUtil.drawBottemCenteredSprite("Lan_Walking_" + displayDirection + "_" + direction.getDirectionValue(getCurrentInputMapping()) % 36/6, MMBN_Game.WIDTH / 2, MMBN_Game.HEIGHT / 2 + 8, 1.0f,flip);
             }
         }
         else {
             // 39 pixels tall
            SpriteUtil.drawBottemCenteredSprite("Shadow_2", MMBN_Game.WIDTH / 2, MMBN_Game.HEIGHT / 2 + 8, 1.0f,flip);
            SpriteUtil.drawBottemCenteredSprite("Lan_Standing_" + displayDirection, MMBN_Game.WIDTH / 2, MMBN_Game.HEIGHT / 2 + 8, 1.0f,flip);
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

    public void handleMovement()
    {
        if(currentDirection.getDirectionValue(getCurrentInputMapping()) > 1) {
            switch (currentDirection) {
                case NORTH:
                    y -= isRunning ? 2 : 1;
                    if (y < 0) {
                        y = 0;
                    }
                    break;
                case EAST:
                    x += isRunning ? 2 : 1;
                    if (x > AssetLoader.getSprite("Lans_Bedroom").getWidth()) {
                        x = AssetLoader.getSprite("Lans_Bedroom").getWidth();
                    }
                    break;
                case SOUTH:
                    y += isRunning ? 2 : 1;
                    if (y > AssetLoader.getSprite("Lans_Bedroom").getHeight()) {
                        y = AssetLoader.getSprite("Lans_Bedroom").getHeight();
                    }
                    break;
                case WEST:
                    x -= isRunning ? 2 : 1;
                    if (x < 0) {
                        x = 0;
                    }
                    break;
                case NORTHEAST:
                    if (isRunning) {
                        x += 2;
                        if (x > AssetLoader.getSprite("Lans_Bedroom").getWidth()) {
                            x = AssetLoader.getSprite("Lans_Bedroom").getWidth();
                        }
                        y -= 1;
                        if (y < 0) {
                            y = 0;
                        }
                    } else {
                        if (currentDirection.getDirectionValue(getCurrentInputMapping()) % 2 == 0) {
                            x += 2;
                            if (x > AssetLoader.getSprite("Lans_Bedroom").getWidth()) {
                                x = AssetLoader.getSprite("Lans_Bedroom").getWidth();
                            }
                            y -= 1;
                            if (y < 0) {
                                y = 0;
                            }
                        }
                    }
                    break;
                case NORTHWEST:
                    if (isRunning) {
                        x -= 2;
                        if (x < 0) {
                            x = AssetLoader.getSprite("Lans_Bedroom").getWidth();
                        }
                        y -= 1;
                        if (y < 0) {
                            y = 0;
                        }
                    } else {
                        if (currentDirection.getDirectionValue(getCurrentInputMapping()) % 2 == 0) {
                            x -= 2;
                            if (x < 0) {
                                x = AssetLoader.getSprite("Lans_Bedroom").getWidth();
                            }
                            y -= 1;
                            if (y < 0) {
                                y = 0;
                            }
                        }
                    }
                    break;
                case SOUTHEAST:
                    if (isRunning) {
                        x += 2;
                        if (x > AssetLoader.getSprite("Lans_Bedroom").getWidth()) {
                            x = AssetLoader.getSprite("Lans_Bedroom").getWidth();
                        }
                        y += 1;
                        if (y > AssetLoader.getSprite("Lans_Bedroom").getHeight()) {
                            y = AssetLoader.getSprite("Lans_Bedroom").getHeight();
                        }
                    } else {
                        if (currentDirection.getDirectionValue(getCurrentInputMapping()) % 2 == 0) {
                            x += 2;
                            if (x > AssetLoader.getSprite("Lans_Bedroom").getWidth()) {
                                x = AssetLoader.getSprite("Lans_Bedroom").getWidth();
                            }
                            y += 1;
                            if (y > AssetLoader.getSprite("Lans_Bedroom").getHeight()) {
                                y = AssetLoader.getSprite("Lans_Bedroom").getHeight();
                            }
                        }
                    }
                    break;
                case SOUTHWEST:
                    if (isRunning) {
                        x -= 2;
                        if (x < 0) {
                            x = 0;
                        }
                        y += 1;
                        if (y > AssetLoader.getSprite("Lans_Bedroom").getHeight()) {
                            y = AssetLoader.getSprite("Lans_Bedroom").getHeight();
                        }
                    } else {
                        if (currentDirection.getDirectionValue(getCurrentInputMapping()) % 2 == 0) {
                            x -= 2;
                            if (x < 0) {
                                x = 0;
                            }
                            y += 1;
                            if (y > AssetLoader.getSprite("Lans_Bedroom").getHeight()) {
                                y = AssetLoader.getSprite("Lans_Bedroom").getHeight();
                            }
                        }
                    }
                    break;
            }
        }
    }


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
