package com.GlitchyDev;

import com.GlitchyDev.GameStates.DebugGameState;
import com.GlitchyDev.GameStates.DebugGameState2;
import com.GlitchyDev.IO.AssetLoader;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import java.io.File;

public class MMBN_Game extends StateBasedGame {

    // Application Properties
    private static final int WIDTH = 240;
    private static final int HEIGHT = 160;
    private static final int SCALEFACTOR = 2;
    private static final int FPS_TARGET = 60;


    // Class Constructor
    public MMBN_Game(String appName)
    {
        super(appName);
    }

    // Initialize your game states (calls init method of each gamestate, and set's the state ID)
    public void initStatesList(GameContainer gc) throws SlickException {
        AssetLoader.loadAssets();
        this.addState(new DebugGameState());
        this.addState(new DebugGameState2());


    }

    // Main Method
    public static void main(String[] args) {

        try {
            AppGameContainer app = new AppGameContainer(new MMBN_Game("Megaman Battle Network Re:2"));
            app.setDisplayMode(WIDTH * SCALEFACTOR, HEIGHT * SCALEFACTOR, false);
            app.setTargetFrameRate(FPS_TARGET);
            app.setShowFPS(true);
            AssetLoader.setDefaultIconss(app,"GameAssets/Sprites/Pet_Icon/PET_1.png","GameAssets/Sprites/Pet_Icon/PET_3.png");
            app.start();
        } catch(SlickException e) {
            e.printStackTrace();
        }
    }
}