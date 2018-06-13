package com.GlitchyDev.GameStates;

import com.GlitchyDev.IO.AssetLoader;
import com.GlitchyDev.IO.SaveLoader;
import com.GlitchyDev.MMBN_Game;
import com.GlitchyDev.Utility.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;


public class OverworldTestState extends BasicMonitoredGameState {
    private int x = 0;
    private int y = 0;

    private final long startingValue = -2;
    private final long enabledValue = -1;

    private final int cacheInputDelay = 3;
    private ArrayList<long[]> cachedInputs = new ArrayList<>();
    private long[] inputMapping;


    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) {
        inputMapping = new long[GButtons.values().length];
        for(int i = 0; i < inputMapping.length; i++)
        {
            inputMapping[i] = startingValue;
        }
    }

    @Override
    public void doRender(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {

        graphics.setColor(new Color(1.0f / 256 * 64, 1.0f / 256 * 16, 1.0f / 256 * 80, 1.0f));
        graphics.fillRect(0, 0, gameContainer.getWidth(), gameContainer.getHeight());


        SpriteUtil.drawSprite("Lans_Bedroom", -x + MMBN_Game.WIDTH / 2, -y + MMBN_Game.HEIGHT / 2, 1.0f);

        drawLan();




    }

    public void drawLan() {


    }

    public void drawDebugInputMapping(Graphics graphics)
    {
        graphics.setColor(Color.blue);
        graphics.fillRect(0, 0, 60, 140);

        graphics.setColor(Color.red);
        int i = 0;
        for (long l : inputMapping) {
            graphics.drawString(String.valueOf(l), 0, i * 12);
            i++;
        }
        i = 0;
        if(cachedInputs.size() != 0) {
            for (long l : cachedInputs.get(0)) {
                graphics.drawString(String.valueOf(l), 35, i * 12);
                i++;
            }
        }

        graphics.drawString(Direction.getDirection(inputMapping).toString(),70,0);
    }

    @Override
    public void doUpdate(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) {

        if(GameController.isButtonPressed(GButtons.DEBUG)) {
            pollInputs();
        }



    }

    public void pollInputs()
    {

        // Remove the previous "Delayed" input
        if (cachedInputs.size() > cacheInputDelay) {
            cachedInputs.remove(0);
        }

        /*
        Understanding Behavior
        - Button is pressed, behavior is enabled and button can be further updated
         */
        for (GButtons button : GButtons.values()) {
            if (inputMapping[button.ordinal()] == startingValue) {
                if (GameController.isButtonPressed(button)) {
                    inputMapping[button.ordinal()] = enabledValue;
                }
            }

            switch (button) {
                case UP:
                case LEFT:
                    if (GameController.isButtonDown(button)) {
                        if (inputMapping[button.ordinal()] == enabledValue) {
                            inputMapping[button.ordinal()] = 1;
                        } else {
                            inputMapping[button.ordinal()]++;
                        }
                        if (inputMapping[button.getReverse().ordinal()] > enabledValue) {
                            inputMapping[button.getReverse().ordinal()] = enabledValue;
                        }
                    } else {
                        inputMapping[button.ordinal()] = startingValue;
                    }
                    break;
                // Can be overruled by above
                case RIGHT:
                case DOWN:
                    if (GameController.isButtonDown(button)) {
                        if (!GameController.isButtonDown(button.getReverse())) {
                            if (inputMapping[button.ordinal()] == enabledValue) {
                                inputMapping[button.ordinal()] = 1;
                            } else {
                                inputMapping[button.ordinal()]++;
                            }
                        }
                    } else {
                        inputMapping[button.ordinal()] = startingValue;
                    }
                    break;
                case A:
                case B:
                case START:
                case SELECT:
                case L:
                case R:
                    if (GameController.isButtonDown(button)) {
                        if (inputMapping[button.ordinal()] == enabledValue) {
                            inputMapping[button.ordinal()] = 1;
                        } else {
                            inputMapping[button.ordinal()]++;
                        }
                    } else {
                        inputMapping[button.ordinal()] = startingValue;
                    }
                    break;
            }

        }
            cachedInputs.add(inputMapping.clone());

        // You can access input index 0 at any point before now, its removed after here
    }

    @Override
    public int getID() {
        return GameStateType.OVERWORLD.ordinal();
    }
}

