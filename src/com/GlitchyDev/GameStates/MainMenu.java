package com.GlitchyDev.GameStates;

import com.GlitchyDev.IO.AssetLoader;
import com.GlitchyDev.IO.SaveLoader;
import com.GlitchyDev.IO.SpriteUtil;
import com.GlitchyDev.MMBN_Game;
import com.GlitchyDev.Utility.BasicMonitoredGameState;
import com.GlitchyDev.Utility.GButtons;
import com.GlitchyDev.Utility.GameController;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

public class MainMenu extends BasicMonitoredGameState {
    private GameContainer container;
    private long lastStateChange = 0;
    private MainMenuSubState currentSubState;

    private int backgroundFrameCount = 0;
    private int pressStartFrameCount = 0;
    private int pressStartControlerCoolDown = 0;
    private int saveFrameCount = -3;
    private int cursorPosition = 0;



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

                adjustedProgress = (1.0/16) * (int)(getStateProgressionPercentage() * 16);
                graphics.setColor(new Color(0.0f,0.0f,0.0f, (float) (1.0 - adjustedProgress)));
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


            case PRESS_START_FADEOUT:
                SpriteUtil.drawSprite("Title_Background", backgroundFrameCount,0,MMBN_Game.SCALE,1.0);
                SpriteUtil.drawSprite("Title_Background",-256 + backgroundFrameCount,0,MMBN_Game.SCALE,1.0);

                SpriteUtil.drawSprite("Menu_Title",9,20,MMBN_Game.SCALE,1.0);
                SpriteUtil.drawSprite("Copyright",7,126,MMBN_Game.SCALE,1.0);

                if(pressStartFrameCount < 25) {
                    SpriteUtil.drawSprite("Press_Start", 78, 100, MMBN_Game.SCALE, 1.0);
                }

                adjustedProgress = (1.0/16) * (int)(getStateProgressionPercentage() * 16);
                graphics.setColor(new Color(0.0f,0.0f,0.0f, (float) (adjustedProgress)));
                graphics.fillRect(0,0,gameContainer.getWidth(),gameContainer.getHeight());
                break;
            case SAVE_STATE:
                SpriteUtil.drawSprite("Title_Background", backgroundFrameCount,0,MMBN_Game.SCALE,1.0);
                SpriteUtil.drawSprite("Title_Background",-256 + backgroundFrameCount,0,MMBN_Game.SCALE,1.0);

                SpriteUtil.drawSprite("Menu_Title",9,20,MMBN_Game.SCALE,1.0);
                SpriteUtil.drawSprite("Copyright",7,126,MMBN_Game.SCALE,1.0);

                if(pressStartFrameCount >= 0) {
                    if (SaveLoader.doesSaveExists()) {
                        if (cursorPosition == 0) {
                            SpriteUtil.drawSprite("NewGame", 90, 92, MMBN_Game.SCALE, 1.0);
                            SpriteUtil.drawSprite("ContinueG", 90, 108, MMBN_Game.SCALE, 1.0);
                            SpriteUtil.drawSprite("Cursor", 76 - ((saveFrameCount % 18)/6), 92, MMBN_Game.SCALE, 1.0);
                        } else {
                            SpriteUtil.drawSprite("NewGameG", 90, 92, MMBN_Game.SCALE, 1.0);
                            SpriteUtil.drawSprite("Continue", 90, 108, MMBN_Game.SCALE, 1.0);
                            SpriteUtil.drawSprite("Cursor", 76 - ((saveFrameCount % 18)/6), 108, MMBN_Game.SCALE, 1.0);
                        }
                    } else {
                        SpriteUtil.drawSprite("NewGame", 90, 92, MMBN_Game.SCALE, 1.0);
                        SpriteUtil.drawSprite("Cursor", 76 - ((saveFrameCount % 18)/6), 92, MMBN_Game.SCALE, 1.0);
                    }
                }
                else
                {
                    SpriteUtil.drawSprite("Press_Start", 78, 100, MMBN_Game.SCALE, 1.0);
                }


                break;
        }

        graphics.setColor(Color.red);
        graphics.drawString(currentSubState.name() + " " + getTotalUtilization(),80,10);
        if(getTotalUtilization() >= 100)
        {
            System.out.println("YOUR LAGGING OUT @ " + getTotalUtilization());
        }



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
                    AssetLoader.getSound("MainMenu_Theme").play(1.0f,0.1f);
                    break;
                case PRESS_START:
                    pressStartFrameCount = 0;
                    pressStartControlerCoolDown = 0;
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

                    pressStartControlerCoolDown++;

                    if(GameController.isButtonPressed(GButtons.START) && pressStartControlerCoolDown >= 40)
                    {
                        currentSubState = MainMenuSubState.SAVE_STATE;
                        saveFrameCount = -3;
                        if(SaveLoader.doesSaveExists())
                        {
                            cursorPosition = 1;
                        }
                        else
                        {
                            cursorPosition = 0;
                        }
                        AssetLoader.getSound("PressStart").play(1.0f,0.8f);
                    }

                    break;
                case PRESS_START_FADEOUT:
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
                    backgroundFrameCount += 2;
                    backgroundFrameCount %= 256;

                    saveFrameCount++;

                    boolean inputUsed = false;
                    if(GameController.isButtonPressed(GButtons.UP) && !inputUsed && SaveLoader.doesSaveExists())
                    {
                        if(cursorPosition == 1)
                        {
                            cursorPosition = 0;
                            inputUsed = true;
                        }
                    }
                    if(GameController.isButtonPressed(GButtons.DOWN) && !inputUsed && SaveLoader.doesSaveExists())
                    {
                        if(cursorPosition == 0)
                        {
                            cursorPosition = 1;
                            inputUsed = true;
                        }
                    }
                    if(GameController.isButtonPressed(GButtons.A) || GameController.isButtonPressed(GButtons.START) && !inputUsed)
                    {
                        // Exit into new game or save game

                        inputUsed = true;
                        AssetLoader.getSound("SelectSave").play(1.0f,0.8f);
                    }
                    if(GameController.isButtonPressed(GButtons.B) && !inputUsed)
                    {
                        currentSubState = MainMenuSubState.PRESS_START;
                        AssetLoader.getSound("Deselect").play(1.0f,0.8f);
                        inputUsed = true;
                    }

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
        PRESS_START_FADEOUT,
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
                    return PRESS_START_FADEOUT;
                case PRESS_START_FADEOUT:
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
                case PRESS_START_FADEOUT:
                    return 0.1;
                case SAVE_STATE:
                    return 54.0;
                default:
                    return 0;
            }
        }


    }

}

