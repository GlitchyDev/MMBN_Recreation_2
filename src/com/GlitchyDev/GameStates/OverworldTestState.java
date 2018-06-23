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

    private final int playerHeightOffset = 6;


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

        graphics.setColor(Color.pink);
        graphics.drawRect(gameContainer.getWidth()/2, gameContainer.getHeight()/2 + playerHeightOffset * MMBN_Game.SCALE,1,1);


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
                 SpriteUtil.drawBottemCenteredSprite("Shadow_" + direction.getDirectionValue(getCurrentInputMapping()) % 18/6, MMBN_Game.WIDTH / 2, MMBN_Game.HEIGHT / 2 + playerHeightOffset - 1, 1.0f,flip);
                 SpriteUtil.drawBottemCenteredSprite("Lan_Running_" + displayDirection + "_" + (direction.getDirectionValue(getCurrentInputMapping()) - 1) % 48/6, MMBN_Game.WIDTH / 2, MMBN_Game.HEIGHT / 2 + playerHeightOffset + 1, 1.0f,flip);
             }
             else {
                 SpriteUtil.drawBottemCenteredSprite("Shadow_" + direction.getDirectionValue(getCurrentInputMapping()) % 18/6, MMBN_Game.WIDTH / 2, MMBN_Game.HEIGHT / 2 + playerHeightOffset, 1.0f,flip);
                 SpriteUtil.drawBottemCenteredSprite("Lan_Walking_" + displayDirection + "_" + (direction.getDirectionValue(getCurrentInputMapping()) - 1) % 36/6, MMBN_Game.WIDTH / 2, MMBN_Game.HEIGHT / 2 + playerHeightOffset + 1, 1.0f,flip);
             }
         }
         else {
             // 39 pixels tall
            SpriteUtil.drawBottemCenteredSprite("Shadow_2", MMBN_Game.WIDTH / 2, MMBN_Game.HEIGHT / 2 + playerHeightOffset, 1.0f,flip);
            SpriteUtil.drawBottemCenteredSprite("Lan_Standing_" + displayDirection, MMBN_Game.WIDTH / 2, MMBN_Game.HEIGHT / 2 + playerHeightOffset + 1, 1.0f,flip);
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
        final int oldX = x;
        final int oldY = y;

        if(!currentDirection.isDiagnal())
        {
            x = getCurrentInputMapping()[GButtons.B.ordinal()] > 1 ? x + 2 * currentDirection.getX() : x + currentDirection.getX();
            y = getCurrentInputMapping()[GButtons.B.ordinal()] > 1 ? y + 2 * currentDirection.getY() : y + currentDirection.getY();
        }
        else
        {
            if(getCurrentInputMapping()[GButtons.B.ordinal()] > 1 || (currentDirection.getDirectionValue(getCurrentInputMapping()) - 1) % 2 == 0)
            {
                x += currentDirection.getX();
                y += currentDirection.getY();
            }
        }

        switch (currentDirection) {
            case NORTH:
            case SOUTH:
                doVerticalCollisions(currentDirection == Direction.NORTH, oldX, oldY);
                break;
            case EAST:
            case WEST:
                doHorizontalCollisions(currentDirection == Direction.WEST, oldX, oldY);
                break;
            case NORTHEAST:
            case NORTHWEST:
            case SOUTHEAST:
            case SOUTHWEST:
                doDiagnalCollisions(oldX,oldY);
                break;
        }
    }

    public void doDiagnalCollisions(int oldX, int oldY)
    {
        if(getCurrentSection().a == 1.0f)
        {
            x = oldX;
            if(getCurrentSection().a == 1.0f) {
                y = oldY;
            }

        }
    }


    public void doVerticalCollisions(boolean isGoingUp, int oldX, int oldY) {

        if (getCurrentSection().a == 1.0f) {
            int goLeft = 0;
            int goRight = 0;
            if(getCurrentSection().b == 1.0f)
            {
                y += isGoingUp ? 1 : -1;
            }
            int offset = 0;

            boolean continueRunning = true;
            while (continueRunning) {
                offset++;

                if (goLeft == 0) {
                    if (getOffsetSection(-offset, 0).g == 1.0f && getOffsetSection(-offset, 0).a == 1.0f) {
                        goLeft = -1;
                    } else {
                        if (getOffsetSection(-offset, 0).a == 0.0f) {
                            goLeft = 1;
                        }
                    }
                }
                if (goRight == 0) {
                    if (getOffsetSection(offset, 0).g == 1.0f && getOffsetSection(offset, 0).a == 1.0f) {
                        goRight = -1;
                    } else {
                        if (getOffsetSection(offset, 0).a == 0.0f) {
                            goRight = 1;
                        }
                    }
                }

                if (goLeft == -1 && goRight == -1) {
                    y = oldY;
                    continueRunning = false;
                }
                if (goLeft == 1) {
                    x += (-1 * (getCurrentInputMapping()[GButtons.B.ordinal()] > 1 ? 2 : 1));
                    continueRunning = false;
                }
                if (goRight == 1) {
                    x += (1 * (getCurrentInputMapping()[GButtons.B.ordinal()] > 1 ? 2 : 1));
                    continueRunning = false;
                }
            }


        }
    }


    public void doHorizontalCollisions(boolean isGoingLeft, int oldX, int oldY) {

        if (getCurrentSection().a == 1.0f) {
            int goUp = 0;
            int goDown = 0;
            if(getCurrentSection().b == 1.0f)
            {
                x += isGoingLeft ? 1 : -1;
            }
            int offset = 0;

            boolean continueRunning = true;
            while (continueRunning) {
                offset++;

                if (goUp == 0) {
                    if (getOffsetSection(0, -offset).g == 1.0f && getOffsetSection(0, -offset).a == 1.0f) {
                        goUp = -1;
                    } else {
                        if (getOffsetSection(0, -offset).a == 0.0f) {
                            goUp = 1;
                        }
                    }
                }
                if (goDown == 0) {
                    if (getOffsetSection(0, offset).g == 1.0f && getOffsetSection(0, offset).a == 1.0f) {
                        goDown = -1;
                    } else {
                        if (getOffsetSection(0, offset).a == 0.0f) {
                            goDown = 1;
                        }
                    }
                }

                if (goUp == -1 && goDown == -1) {
                    x = oldX;
                    continueRunning = false;
                }
                if (goUp == 1) {
                    y += -1;
                    continueRunning = false;
                }
                if (goDown == 1) {
                    y += 1;
                    continueRunning = false;
                }
            }


        }
    }



    public boolean isInsideMapBounds() {
        final int width = AssetLoader.getSprite("Lans_Bedroom").getWidth();
        final int height = AssetLoader.getSprite("Lans_Bedroom").getHeight();
        return x < width && y < height && x >= 0 && y >= 0;
    }


    public Color getCurrentSection()
    {
        return getOffsetSection(0,0);
    }

    public Color getOffsetSection(int xOffset, int yOffset)
    {
        return AssetLoader.getSprite("Lans_Bedroom_Collision").getColor(x + xOffset, y + yOffset);
    }

    public Color getGridSection(Direction direction) {
        switch (currentDirection) {
            case NORTH:
                return getOffsetSection(0,-1);
            case EAST:
                return getOffsetSection(1,0);
            case SOUTH:
                return getOffsetSection(0,1);
            case WEST:
                return getOffsetSection(-1,0);
            case NORTHEAST:
                return getOffsetSection(1,-1);
            case NORTHWEST:
                return getOffsetSection(-1,-1);
            case SOUTHEAST:
                return getOffsetSection(1,1);
            case SOUTHWEST:
                return getOffsetSection(-1,1);
            default:
                return null;
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
