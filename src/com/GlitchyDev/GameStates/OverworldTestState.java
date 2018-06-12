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
    private final long startingValue = -1;
    private final int cacheInputDelay = 3;
    private ArrayList<long[]> cachedInputs = new ArrayList<>();
    private long[] inputMapping = new long[]{startingValue,startingValue,startingValue,startingValue};


    /*
    private long frameCount = 0;
    private ArrayList<Direction> inputBuffer = new ArrayList<>();
    private boolean isMoving = false;
    private int stateFrameCount = 0;
    */

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) {

    }

    @Override
    public void doRender(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {

        graphics.setBackground(new Color(1.0f/256*64, 1.0f/256*16, 1.0f/256*80,1.0f));
        SpriteUtil.drawSprite("Lans_Bedroom",-x + MMBN_Game.WIDTH/2, -y + MMBN_Game.HEIGHT/2,1.0f);

        drawLan();

        graphics.setColor(Color.red);
        int i = 0;
        for(long l: cachedInputs.get(0))
        {
            graphics.drawString(String.valueOf(l),0,i*10);
            i++;
        }


    }

    public void drawLan() {



    }

    @Override
    public void doUpdate(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) {

        for(GButtons button: GButtons.getDirections()) {

            if (inputMapping[button.ordinal()] == startingValue) {
                if(GameController.isButtonPressed(button))
                {
                    inputMapping[button.ordinal()] = 0;
                }
            }
            else
            {
                switch(button.getEquivalent())
                {
                    case NORTH:
                    case WEST:
                        if(GameController.isButtonDown(button)) {
                            inputMapping[button.ordinal()]++;
                            if(inputMapping[button.getReverse().ordinal()] > 0) {
                                inputMapping[button.getReverse().ordinal()] = 0;
                            }
                        }
                        else {
                            inputMapping[button.ordinal()] = startingValue;
                        }
                        break;
                        // Can be overruled by above
                    case EAST:
                    case SOUTH:
                        if(GameController.isButtonDown(button)) {
                            if(inputMapping[button.getReverse().ordinal()] == startingValue)
                            {
                                inputMapping[button.ordinal()]++;
                            }
                        }
                        else {
                            inputMapping[button.ordinal()] = startingValue;
                        }
                        break;
                }
            }

        }

        cachedInputs.add(inputMapping.clone());

        // You can access input index 0 at any point before now, its removed after here


        if(cachedInputs.size() > cacheInputDelay)
        {
            cachedInputs.remove(0);
        }

    }

    @Override
    public int getID() {
        return GameStateType.OVERWORLD.ordinal();
    }


}

/*
public class OverworldTestState extends BasicMonitoredGameState {
    private int x;
    private int y;
    private long frameCount;

    private ArrayList<Direction> currentlyInputedDirections;
    private ArrayList<Direction> previouslyInputedDirections;


    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) {
        x = 0;
        y = 0;
        frameCount = 0;
        currentlyInputedDirections = new ArrayList<>();
        previouslyInputedDirections = new ArrayList<>();
        previouslyInputedDirections.add(Direction.SOUTH);
    }

    @Override
    public void doRender(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {

        graphics.setBackground(new Color(1.0f/256*64, 1.0f/256*16, 1.0f/256*80,1.0f));
        SpriteUtil.drawSprite("Lans_Bedroom",-x + MMBN_Game.WIDTH/2, -y + MMBN_Game.HEIGHT/2,1.0f);

        drawLan();
        // 6 Frames for his walk animation

        //graphics.setColor(AssetLoader.getSprite("Lans_Bedroom").getColor(x,y));
        //graphics.fillRect(gameContainer.getWidth()/2 - 4*MMBN_Game.SCALE,gameContainer.getHeight()/2 - 4*MMBN_Game.SCALE,4*MMBN_Game.SCALE,4*MMBN_Game.SCALE);
    }

    public void drawLan()
    {
        //if(currentlyInputedDirections.size() == 0) {

        ArrayList<Direction> currentDirections = new ArrayList<>();
        if(currentlyInputedDirections.size() != 0)
        {
            currentDirections = currentlyInputedDirections;
        }
        else
        {
            currentDirections = previouslyInputedDirections;
        }


        if(currentDirections.size() == 2) {
            boolean flip = currentDirections.contains(Direction.WEST);
            if(currentDirections.contains(Direction.NORTH)) {
                SpriteUtil.drawBottemCenteredSprite("Lan_Standing_NE",MMBN_Game.WIDTH/2,  MMBN_Game.HEIGHT/2,1.0f,flip);
            }
            else {
                SpriteUtil.drawBottemCenteredSprite("Lan_Standing_SE",MMBN_Game.WIDTH/2,  MMBN_Game.HEIGHT/2,1.0f,flip);
            }
        }
        else {
            switch (currentDirections.get(0)) {
                case NORTH:
                    SpriteUtil.drawBottemCenteredSprite("Lan_Standing_N", MMBN_Game.WIDTH / 2, MMBN_Game.HEIGHT / 2, 1.0f, false);
                    break;
                case EAST:
                    SpriteUtil.drawBottemCenteredSprite("Lan_Standing_E", MMBN_Game.WIDTH / 2, MMBN_Game.HEIGHT / 2, 1.0f, false);
                    break;
                case SOUTH:
                    SpriteUtil.drawBottemCenteredSprite("Lan_Standing_S", MMBN_Game.WIDTH / 2, MMBN_Game.HEIGHT / 2, 1.0f, false);
                    break;
                case WEST:
                    SpriteUtil.drawBottemCenteredSprite("Lan_Standing_E", MMBN_Game.WIDTH / 2, MMBN_Game.HEIGHT / 2, 1.0f, true);
                    break;
            }


        }


    }

    @Override
    public void doUpdate(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) {
        for(GButtons button: GButtons.getDirections()) {
            if (GameController.isButtonPressed(button))
            {
                if(currentlyInputedDirections.contains(button.getEquivalent().reverse())) {
                    currentlyInputedDirections.remove(button.getEquivalent().reverse());
                    currentlyInputedDirections.add(button.getEquivalent());
                }
                else {
                    currentlyInputedDirections.add(button.getEquivalent());
                }
            }
        }

        ArrayList<Direction> removedDirections = new ArrayList<>();
        for(GButtons button: GButtons.getDirections()) {
            if (!GameController.isButtonDown(button)) {
                if(currentlyInputedDirections.contains(button.getEquivalent())) {
                    currentlyInputedDirections.remove(button.getEquivalent());
                    removedDirections.add(button.getEquivalent());
                }
            }
        }
        if(currentlyInputedDirections.size() == 0 && removedDirections.size() != 0) {
            System.out.println("OMG");
            previouslyInputedDirections = removedDirections;
        }

        if(frameCount%2==0 || GameController.isButtonDown(GButtons.B)) {
            if (currentlyInputedDirections.size() == 1) {
                switch (currentlyInputedDirections.get(0)) {
                    case NORTH:
                        y -= 2;
                        if (y < 0) {
                            y = 0;
                        }
                        break;
                    case EAST:
                        x += 2;
                        if (x > AssetLoader.getSprite("Lans_Bedroom").getWidth()) {
                            x = AssetLoader.getSprite("Lans_Bedroom").getWidth();
                        }
                        break;
                    case SOUTH:
                        y += 2;
                        if (y > AssetLoader.getSprite("Lans_Bedroom").getHeight()) {
                            y = AssetLoader.getSprite("Lans_Bedroom").getHeight();
                        }
                        break;
                    case WEST:
                        x -= 2;
                        if (x < 0) {
                            x = 0;
                        }
                        break;
                }
            } else {
                for (Direction direction : currentlyInputedDirections) {
                    switch (direction) {
                        case NORTH:
                            y -= 1;
                            if (y < 0) {
                                y = 0;
                            }
                            break;
                        case EAST:
                            x += 2;
                            if (x > AssetLoader.getSprite("Lans_Bedroom").getWidth()) {
                                x = AssetLoader.getSprite("Lans_Bedroom").getWidth();
                            }
                            break;
                        case SOUTH:
                            y += 1;
                            if (y > AssetLoader.getSprite("Lans_Bedroom").getHeight()) {
                                y = AssetLoader.getSprite("Lans_Bedroom").getHeight();
                            }
                            break;
                        case WEST:
                            x -= 2;
                            if (x < 0) {
                                x = 0;
                            }
                            break;
                    }
                }

            }
        }
        frameCount++;




    }

    @Override
    public int getID() {
        return GameStateType.OVERWORLD.ordinal();
    }


}

 */
