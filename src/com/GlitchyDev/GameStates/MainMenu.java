package com.GlitchyDev.GameStates;

import com.GlitchyDev.IO.AssetLoader;
import com.GlitchyDev.IO.SaveLoader;
import com.GlitchyDev.Utility.SpriteUtil;
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
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) {
        lastStateChange = gameContainer.getTime();
        currentSubState = MainMenuSubState.CAPCOM_FADEIN;
        this.container = gameContainer;

        if(SaveLoader.doesSaveExists())
        {
            cursorPosition = 1;
        }
        else
        {
            cursorPosition = 0;
        }
    }

    @Override
    protected void doRender(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {

        // The "Rendering" takes each substate into a different subsection
        switch(currentSubState)
        {
            case CAPCOM_FADEIN:
                drawCapcomLogo();
                drawScreenFadeIn(4,graphics);
                break;
            case CAPCOM_LOGO:
                drawCapcomLogo();
                break;
            case CAPCOM_FADEOUT:
                drawCapcomLogo();
                drawScreenFadeOut(4,graphics);
                break;
            case PRESS_START_FADEIN:

                drawMainMenu();
                if(pressStartFrameCount < 25) {
                    drawPressStart();
                }
                drawScreenFadeIn(17,graphics);
                break;
            case PRESS_START:
                drawMainMenu();
                if(pressStartFrameCount < 25) {
                    drawPressStart();
                }
                break;
            case PRESS_START_FADEOUT:
                drawMainMenu();
                if(pressStartFrameCount < 25) {
                    drawPressStart();
                }
                drawScreenFadeOut(16,graphics);
                break;
            case SAVE_STATE:
                drawMainMenu();
                if(pressStartFrameCount >= 0) {
                    drawSaveSelect();
                }
                else
                {
                    drawPressStart();
                }
                break;
            case SAVE_STATE_FADEOUT:
                drawMainMenu();
                drawSaveSelect();
                drawScreenFadeOut(15,graphics);
                break;
            case SAVE_SELECTED:
                drawMainMenu();
                drawSaveSelect();
                drawScreenFadeOut(16,graphics);
                break;
        }

        /*
        graphics.setColor(Color.red);
        graphics.drawString(currentSubState.name() + " " + getTotalUtilization(),80,10);
        if(getTotalUtilization() >= 100)
        {
            System.out.println("YOUR LAGGING OUT @ " + getTotalUtilization());
        }
        */



    }

    private void drawCapcomLogo()
    {
        SpriteUtil.drawSprite("Capcom_Logo",9,61,1.0);
    }

    private void drawScreenFadeIn(int sections, Graphics graphics)
    {
        double adjustedProgress = 1.0 - (1.0/sections) * (int)(getStateProgressionPercentage() * sections);
        graphics.setColor(new Color(0.0f,0.0f,0.0f, (float) adjustedProgress));
        graphics.fillRect(0,0,container.getWidth(),container.getHeight());
    }
    private void drawScreenFadeOut(int sections, Graphics graphics)
    {
        double adjustedProgress = (1.0/sections) * (int)(getStateProgressionPercentage() * sections);
        graphics.setColor(new Color(0.0f,0.0f,0.0f, (float) adjustedProgress));
        graphics.fillRect(0,0,container.getWidth(),container.getHeight());
    }

    private void drawMainMenu() {
        SpriteUtil.drawSprite("Title_Background", backgroundFrameCount, 0, 1.0);
        SpriteUtil.drawSprite("Title_Background", -256 + backgroundFrameCount, 0, 1.0);
        SpriteUtil.drawSprite("Menu_Title", 9, 20, 1.0);
        SpriteUtil.drawSprite("Copyright", 7, 126, 1.0);
    }

    private void drawPressStart()
    {
        SpriteUtil.drawSprite("Press_Start", 78, 100,1.0);
    }

    private void drawSaveSelect()
    {
        if (SaveLoader.doesSaveExists()) {
            if (cursorPosition == 0) {
                SpriteUtil.drawSprite("NewGame", 90, 92,1.0);
                SpriteUtil.drawSprite("ContinueG", 90, 108,1.0);
                SpriteUtil.drawSprite("Cursor", 76 - ((saveFrameCount % 18)/6), 92,1.0);
            } else {
                SpriteUtil.drawSprite("NewGameG", 90, 92,1.0);
                SpriteUtil.drawSprite("Continue", 90, 108,1.0);
                SpriteUtil.drawSprite("Cursor", 76 - ((saveFrameCount % 18)/6), 108,1.0);
            }
        } else {
            SpriteUtil.drawSprite("NewGame", 90, 92,1.0);
            SpriteUtil.drawSprite("Cursor", 76 - ((saveFrameCount % 18)/6), 92,1.0);
        }
    }



    @Override
    protected void doUpdate(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) {


        if(getStateProgressionPercentage() >= 1.0)
        {
            switch(currentSubState)
            {
                case PRESS_START_FADEIN:
                    AssetLoader.getSound("MainMenu_Theme").play(1.0f,1.0f);
                    break;
                case PRESS_START:
                    pressStartFrameCount = 0;
                    pressStartControlerCoolDown = 0;
                    break;
                case SAVE_SELECTED:
                    stateBasedGame.enterState(GameStateType.OVERWORLD.ordinal());
            }
            currentSubState = currentSubState.getNextState();
            lastStateChange = gameContainer.getTime();
        }
        else
        {
            switch(currentSubState)
            {
                case PRESS_START_FADEIN:
                    updateBackground();
                    break;
                case PRESS_START:
                    updateBackground();
                    updatePressStart();

                    pressStartControlerCoolDown++;

                    if(GameController.isButtonPressed(GButtons.START) && pressStartControlerCoolDown >= 40)
                    {
                        currentSubState = MainMenuSubState.SAVE_STATE;
                        saveFrameCount = -3;
                        AssetLoader.getSound("PressStart").play(1.0f,0.8f);
                    }

                    break;
                case PRESS_START_FADEOUT:
                    updateBackground();
                    updatePressStart();
                    break;
                case SAVE_STATE:
                    updateBackground();
                    saveFrameCount++;

                    boolean inputUsed = false;
                    if(GameController.isButtonPressed(GButtons.UP) && SaveLoader.doesSaveExists())
                    {
                        if(cursorPosition == 1)
                        {
                            cursorPosition = 0;
                            inputUsed = true;
                            AssetLoader.getSound("SwitchOptions").stop();
                            AssetLoader.getSound("SwitchOptions").play(1.0f,1.0f);
                        }
                    }
                    if(GameController.isButtonPressed(GButtons.DOWN) && !inputUsed && SaveLoader.doesSaveExists())
                    {
                        if(cursorPosition == 0)
                        {
                            cursorPosition = 1;
                            inputUsed = true;
                            AssetLoader.getSound("SwitchOptions").stop();
                            AssetLoader.getSound("SwitchOptions").play(1.0f,1.0f);
                        }
                    }
                    if(GameController.isButtonPressed(GButtons.A) || GameController.isButtonPressed(GButtons.START) && !inputUsed)
                    {
                        // Exit into new game or save game

                        inputUsed = true;
                        AssetLoader.getSound("SelectSave").play(1.0f,1.0f);
                        AssetLoader.getSound("MainMenu_Theme").stop();
                        currentSubState = MainMenuSubState.SAVE_SELECTED;
                        lastStateChange = gameContainer.getTime();
                    }
                    if(GameController.isButtonPressed(GButtons.B) && !inputUsed)
                    {
                        currentSubState = MainMenuSubState.PRESS_START;
                        AssetLoader.getSound("Deselect").play(1.0f,0.8f);
                    }

                    break;
                case SAVE_STATE_FADEOUT:
                    updateBackground();
                case SAVE_SELECTED:
                    updateBackground();
                    break;
            }
        }
    }


    private void updateBackground()
    {
        backgroundFrameCount += 2;
        backgroundFrameCount %= 256;
    }
    private void updatePressStart()
    {
        if(pressStartFrameCount >= 32)
        {
            pressStartFrameCount = 0;
        }
        else
        {
            pressStartFrameCount++;
        }
    }


    public double getPassedTime()
    {
        return (container.getTime()-lastStateChange)/1000.0;
    }

    private double getStateProgressionPercentage()
    {
        return (1.0/currentSubState.getStateLength()) * (container.getTime()-lastStateChange)/1000.0;
    }


    private enum MainMenuSubState{
        CAPCOM_FADEIN, // Fade into Logo
        CAPCOM_LOGO, // Keep Logo on Screen
        CAPCOM_FADEOUT, // Fade out Logo
        PRESS_START_FADEIN, // Fade in Main Menu ( Start )
        PRESS_START, // Main Menu ( Start )
        PRESS_START_FADEOUT, // Fade out Main Menu ( Start )
        SAVE_STATE, // Select Save
        SAVE_STATE_FADEOUT, // Select Save
        SAVE_SELECTED; // Save Selected

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
                    return SAVE_STATE_FADEOUT;
                case SAVE_STATE_FADEOUT:
                    return CAPCOM_FADEIN;
                case SAVE_SELECTED:
                    return PRESS_START_FADEIN;
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
                case SAVE_STATE_FADEOUT:
                    return 0.1;
                case SAVE_SELECTED:
                    return 0.25;
                default:
                    return 0;
            }
        }


    }

}

