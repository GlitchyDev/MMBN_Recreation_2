package com.GlitchyDev.GameStates;

import com.GlitchyDev.IO.AssetLoader;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class DebugGameState2 extends BasicGameState {

    @Override
    public int getID() {
        return GameStateType.DEBUG_2.ordinal();
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) {

    }


    private int count = 0;
    private int wait = 0;
    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {

        // 6 In
        // 6 Mid
        // 6 out

        if(wait > 5)
        {
            wait = 0;
            count++;
        }
        else
        {
            wait++;
        }
        if(count > 30)
        {
            count = 0;
        }
        AssetLoader.getSprite("NetworkLogin_" + count).draw(0,0,2);
        graphics.setColor(Color.blue);
        graphics.drawString("NetworkLogin_" + count,200,30);


    }


    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) {

    }
}
