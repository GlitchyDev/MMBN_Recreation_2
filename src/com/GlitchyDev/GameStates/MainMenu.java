package com.GlitchyDev.GameStates;

import com.GlitchyDev.IO.AssetLoader;
import com.GlitchyDev.IO.SpriteUtil;
import com.GlitchyDev.MMBN_Game;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MainMenu extends BasicGameState {
    private GameContainer container;
    private long lastStateChange = 0;
    private MainMenuSubState currentSubState;


    @Override
    public int getID() {
        return GameStateType.MAINMENU.ordinal();
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        lastStateChange = gameContainer.getTime();
        currentSubState = MainMenuSubState.CAPCOM_FADEIN;
        this.container = gameContainer;
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {

        /*
        graphics.setColor(new Color(1.0f,0.0f,0.0f, (float) getStateProgressionPercentage()));
        graphics.fillRect(0,0,100,100);
        graphics.setColor(Color.red);
        graphics.drawString(currentSubState.name(),50,50);
        */

        switch(currentSubState)
        {
            case CAPCOM_FADEIN:
                double adjustedProgress = (1.0/4) * (int)(getStateProgressionPercentage() * 4);
                SpriteUtil.drawSprite("Capcom_Logo",9,61,MMBN_Game.SCALE,adjustedProgress);
                System.out.println(adjustedProgress);
                break;
            case CAPCOM_LOGO:
                SpriteUtil.drawSprite("Capcom_Logo",9,61,MMBN_Game.SCALE,1);
                break;
            case CAPCOM_FADEOUT:
                adjustedProgress = (1.0/4) * (int)(getStateProgressionPercentage() * 4);
                SpriteUtil.drawSprite("Capcom_Logo",9,61,MMBN_Game.SCALE,1.0 - adjustedProgress);
                break;
            case PRESS_START_FADEIN:

                break;
            case PRESS_START:
                int adjustment = (int)(240 * (getPassedTime()%1.0));
                SpriteUtil.drawSprite("Title_Background",adjustment,0,MMBN_Game.SCALE,1.0);
                SpriteUtil.drawSprite("Title_Background",-240 + adjustment,0,MMBN_Game.SCALE,1.0);

                break;
            case SAVE_STATE:

                break;
        }

        graphics.setColor(Color.red);
        graphics.drawString(currentSubState.name(),50,50);



    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

        if(gameContainer.getInput().isKeyDown(Input.KEY_0))
        {
            currentSubState = MainMenuSubState.CAPCOM_FADEIN;
            lastStateChange = gameContainer.getTime();
        }
        if(getStateProgressionPercentage() >= 1.0)
        {
            currentSubState = currentSubState.getNextState();
            lastStateChange = gameContainer.getTime();
        }
    }


    public double getPassedTime()
    {
        return (container.getTime()-lastStateChange)/1000.0;
    }
    public double getStateProgressionPercentage()
    {
        return (1.0/currentSubState.getStateLength()) * (container.getTime()-lastStateChange)/1000.0;
    }


    private enum MainMenuSubState{
        CAPCOM_FADEIN,
        CAPCOM_LOGO,
        CAPCOM_FADEOUT,
        PRESS_START_FADEIN,
        PRESS_START,
        SAVE_STATE;

        public MainMenuSubState getNextState() {
            switch(this)
            {
                case CAPCOM_FADEIN:
                    return CAPCOM_LOGO;
                case CAPCOM_LOGO:
                    return CAPCOM_FADEOUT;
                case CAPCOM_FADEOUT:
                    return PRESS_START_FADEIN;
                case PRESS_START_FADEIN:
                    return PRESS_START;
                case PRESS_START:
                    return CAPCOM_FADEIN;
                case SAVE_STATE:
                    return CAPCOM_FADEIN;
                default:
                    return null;
            }
        }

        public double getStateLength() {
            switch(this)
            {
                case CAPCOM_FADEIN:
                    return 0.3;
                case CAPCOM_LOGO:
                    return 1.9;
                case CAPCOM_FADEOUT:
                    return 0.3;
                case PRESS_START_FADEIN:
                    return 0.2;
                case PRESS_START:
                    return 60.0;
                case SAVE_STATE:
                    return 60.0;
                default:
                    return 0;
            }
        }


    }
}

