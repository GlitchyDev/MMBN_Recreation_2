package com.GlitchyDev.IO;

import org.newdawn.slick.Color;

public class SpriteUtil {

    public static void drawSprite(String name, int x, int y, float scale, double alpha)
    {
        AssetLoader.getSprite(name).draw(x * scale,y * scale,scale,new Color(1.0f,1.0f,1.0f,(float)(alpha)));
    }


}
