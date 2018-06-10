package com.GlitchyDev.Utility;

import com.GlitchyDev.IO.AssetLoader;
import com.GlitchyDev.MMBN_Game;
import org.newdawn.slick.Color;

public class SpriteUtil {

    public static void drawSprite(String name, int x, int y, double alpha)
    {
        AssetLoader.getSprite(name).draw(x * MMBN_Game.SCALE,y * MMBN_Game.SCALE,MMBN_Game.SCALE,new Color(1.0f,1.0f,1.0f,(float)(alpha)));
    }

    public static void drawFlippedSprite(String name, int x, int y, double alpha)
    {
        AssetLoader.getSprite(name).getFlippedCopy(true,false).draw(x * MMBN_Game.SCALE,y * MMBN_Game.SCALE,MMBN_Game.SCALE,new Color(1.0f,1.0f,1.0f,(float)(alpha)));
    }

    public static void drawSprite(String name, int x, int y, double alpha, boolean flipped)
    {
        if(flipped)
        {
            drawFlippedSprite(name, x, y, alpha);
        }
        else
        {
            drawSprite(name, x, y, alpha);
        }
    }

    public static void drawBottemCenteredSprite(String name, int x, int y, double alpha, boolean flipped)
    {
        int height = AssetLoader.getSprite(name).getHeight();
        int width = AssetLoader.getSprite(name).getWidth();
        drawSprite(name,x-width/2,y-height,alpha,flipped);
    }
}
