package com.GlitchyDev.GameStates;

import com.GlitchyDev.MMBN_Game;
import com.GlitchyDev.Utility.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;


public class OverworldTestState extends ControllerGameState {
    private int x = 0;
    private int y = 0;


    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) {
        super.init(gameContainer, stateBasedGame);


    }

    @Override
    public void doRender(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {

        graphics.setColor(new Color(1.0f / 256 * 64, 1.0f / 256 * 16, 1.0f / 256 * 80, 1.0f));
        graphics.fillRect(0, 0, gameContainer.getWidth(), gameContainer.getHeight());


        SpriteUtil.drawSprite("Lans_Bedroom", -x + MMBN_Game.WIDTH / 2, -y + MMBN_Game.HEIGHT / 2, 1.0f);

        drawLan();




    }


    private Direction previousDirection = Direction.NORTH;
    public void drawLan() {
        Direction direction = Direction.getDirection(inputMapping);
        if(direction == Direction.NONE)
        {
            direction = previousDirection;
        }
        Direction displayDirection = direction;
        boolean flip = false;
        if(direction.requiresFlip())
        {
            displayDirection = direction.mirror();
            flip = true;
        }
        previousDirection = direction;


        SpriteUtil.drawBottemCenteredSprite("Lan_Walking_" + displayDirection + "_" + direction.getDirectionValue(inputMapping) % 36/6, -x + MMBN_Game.WIDTH / 2, -y + MMBN_Game.HEIGHT / 2 + 5, 1.0f,flip);

    }

    @Override
    public void doUpdate(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) {




    }



    @Override
    public int getID() {
        return GameStateType.OVERWORLD.ordinal();
    }
}
