package com.GlitchyDev.Utility;

import com.GlitchyDev.IO.AssetLoader;
import com.GlitchyDev.MMBN_Game;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;

public class TextureSampler {


    public static Color getColor(String image, int x, int y)
    {
        return AssetLoader.getSprite(image).getColor(x * MMBN_Game.SCALE,y * MMBN_Game.SCALE);
    }

    public static int getWidth(String image)
    {
        return AssetLoader.getSprite(image).getWidth()/MMBN_Game.SCALE;
    }

    public static int getHeight(String image)
    {
        return AssetLoader.getSprite(image).getWidth()/MMBN_Game.SCALE;
    }
}
