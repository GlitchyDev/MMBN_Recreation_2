package com.GlitchyDev;



import org.newdawn.slick.*;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {


    public static void main(String[] args) {
        File file = new File("GameAssets/Fonts/Thin_Uppercase");
        for(File letter : file.listFiles())
        {
            String name = letter.getName().replace(".png","");
            String newName = name.replace("TN_","");
            letter.renameTo(new File("GameAssets/Fonts/Thin_Uppercase/" + newName + ".png"));
        }

    }
}



        /*
        try {
            BufferedImage source = ImageIO.read(new File("GameAssets/Sprites/Font/Lettering.png"));


            int count = 0;
            for(int sy = 0; sy < source.getHeight()/16.0; sy++) {
                for (int sx = 0; sx < source.getWidth() / 8.0; sx++) {
                    BufferedImage image = new BufferedImage(7,16,BufferedImage.TYPE_INT_ARGB);

                    for(int x = 0; x < 7; x++)
                    {
                        for(int y = 0; y < 16; y++)
                        {
                            image.setRGB(x,y,source.getRGB(sx * 8 + x, sy * 16 + y));
                        }
                    }
                    ImageIO.write(image,"PNG",new File("GameAssets/Sprites/Font/" + count + ".png"));
                    count++;

                }
            }



        } catch (IOException e) {
            e.printStackTrace();
        }
        */


