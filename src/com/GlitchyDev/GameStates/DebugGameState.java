package com.GlitchyDev.GameStates;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.util.Random;

public class DebugGameState extends BasicGameState {
    private Random random;



    @Override
    public int getID() {
        return GameStateType.DEBUG_1.ordinal();
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) {
        random = new Random();
    }

    // 25 Frames on
    // 7 frames off

    int frames = 0;
    int cache = 0;
    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
        graphics.setColor(Color.green);
        frames++;



        if(frames % 10 == 0)
        {
            cache = random.nextInt();
        }
        random.setSeed(cache);

        int xWidth = 8;
        int yHeight = 12;
        int area = (gameContainer.getWidth() / xWidth) * (gameContainer.getHeight() / yHeight);
        for (int i = 0; i < area; i++) {
            int x = (i * xWidth) % gameContainer.getWidth();
            int y = ((i * xWidth) / gameContainer.getWidth()) * yHeight;
            graphics.drawString(String.valueOf(random.nextInt()).substring(0, 1), x, y);
        }

    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        if(Math.random() < 0.01)
        {
            stateBasedGame.enterState(GameStateType.DEBUG_2.ordinal());
        }
    }
}
