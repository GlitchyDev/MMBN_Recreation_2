package com.GlitchyDev.GameStates;

import com.GlitchyDev.IO.AssetLoader;
import com.GlitchyDev.IO.SpriteUtil;
import com.GlitchyDev.MMBN_Game;
import com.GlitchyDev.Utility.BasicMonitoredGameState;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

public class MainMenu extends BasicMonitoredGameState {
    private GameContainer container;
    private long lastStateChange = 0;
    private MainMenuSubState currentSubState;

    private int backgroundFrameCount = 0;
    private int pressStartFrameCount = 0;



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
    public void doRender(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {

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
                break;
            case CAPCOM_LOGO:
                SpriteUtil.drawSprite("Capcom_Logo",9,61,MMBN_Game.SCALE,1);
                break;
            case CAPCOM_FADEOUT:
                adjustedProgress = (1.0/4) * (int)(getStateProgressionPercentage() * 4);
                SpriteUtil.drawSprite("Capcom_Logo",9,61,MMBN_Game.SCALE,1.0 - adjustedProgress);
                break;
            case PRESS_START_FADEIN:

                SpriteUtil.drawSprite("Title_Background", backgroundFrameCount,0,MMBN_Game.SCALE,1.0);
                SpriteUtil.drawSprite("Title_Background",-256 + backgroundFrameCount,0,MMBN_Game.SCALE,1.0);

                SpriteUtil.drawSprite("Menu_Title",9,20,MMBN_Game.SCALE,1.0);
                SpriteUtil.drawSprite("Copyright",7,126,MMBN_Game.SCALE,1.0);

                if(pressStartFrameCount < 25) {
                    SpriteUtil.drawSprite("Press_Start", 78, 100, MMBN_Game.SCALE, 1.0);
                }


                graphics.setColor(new Color(0.0f,0.0f,0.0f, (float) (1.0 - getStateProgressionPercentage())));
                graphics.fillRect(0,0,gameContainer.getWidth(),gameContainer.getHeight());

                break;
            case PRESS_START:

                SpriteUtil.drawSprite("Title_Background", backgroundFrameCount,0,MMBN_Game.SCALE,1.0);
                SpriteUtil.drawSprite("Title_Background",-256 + backgroundFrameCount,0,MMBN_Game.SCALE,1.0);

                SpriteUtil.drawSprite("Menu_Title",9,20,MMBN_Game.SCALE,1.0);
                SpriteUtil.drawSprite("Copyright",7,126,MMBN_Game.SCALE,1.0);

                if(pressStartFrameCount < 25) {
                    SpriteUtil.drawSprite("Press_Start", 78, 100, MMBN_Game.SCALE, 1.0);
                }

                break;
            case SAVE_STATE:

                break;
        }

        graphics.setColor(Color.red);
        graphics.drawString(currentSubState.name() + " " + getTotalUtilization(),80,10);



    }

    @Override
    public void doUpdate(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

        if(gameContainer.getInput().isKeyDown(Input.KEY_0))
        {
            currentSubState = MainMenuSubState.CAPCOM_FADEIN;
            lastStateChange = gameContainer.getTime();
        }

        // On update
        if(getStateProgressionPercentage() >= 1.0)
        {
            switch(currentSubState)
            {
                case PRESS_START_FADEIN:
                    AssetLoader.getSound("MainMenu_Theme").play();
                    break;
                case PRESS_START:
                    pressStartFrameCount = 0;
                    break;
                case SAVE_STATE:
                    break;
            }
            currentSubState = currentSubState.getNextState();
            lastStateChange = gameContainer.getTime();
        }
        // Normal Actions
        else
        {
            switch(currentSubState)
            {
                case PRESS_START_FADEIN:
                    backgroundFrameCount += 2;
                    backgroundFrameCount %= 256;
                    break;
                case PRESS_START:

                    backgroundFrameCount += 2;
                    backgroundFrameCount %= 256;

                    if(pressStartFrameCount >= 32)
                    {
                        pressStartFrameCount = 0;
                    }
                    else
                    {
                        pressStartFrameCount++;
                    }

                    break;
                case SAVE_STATE:
                    break;
            }
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
                    return 0.4;
                case CAPCOM_LOGO:
                    return 1.7;
                case CAPCOM_FADEOUT:
                    return 0.4;
                case PRESS_START_FADEIN:
                    return 0.2;
                case PRESS_START:
                    return 54.0;
                case SAVE_STATE:
                    return 60.0;
                default:
                    return 0;
            }
        }


    }
}

