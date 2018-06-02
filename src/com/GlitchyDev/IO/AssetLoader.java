package com.GlitchyDev.IO;

import javafx.scene.media.Media;
import org.newdawn.slick.*;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class AssetLoader {
    private static HashMap<String,Image> spriteAssets;
    private static HashMap<String,HashMap<String,Image>> fonts;
    private static HashMap<String,Sound> soundAssets;
    private static String[] icons;


    public static void loadAssets()  {

        spriteAssets = new HashMap<>();
        soundAssets = new HashMap<>();
        fonts = new HashMap<>();

        System.out.println("AssetLoader: Start Loading");
        // At a later point add in other ways of managing files

        File soundAssetFolder = new File("GameAssets/Sounds");
        System.out.println("AssetLoader: Start Sprite Loading");
        File spriteAssetFolder = new File("GameAssets/Sprites");
        for(File spriteSubFolder: spriteAssetFolder.listFiles()) {
            System.out.println("AssetLoader: Loading Folder " + spriteSubFolder.getName());
            for(File sprite: spriteSubFolder.listFiles())
            {
                System.out.println("AssetLoader: Loading " + sprite.getPath());

                String name = sprite.getName().replace(".png","");
                Image spriteImage = null;
                try {
                    spriteImage = new Image(sprite.getPath());
                } catch (SlickException e) {

                }
                spriteAssets.put(name,spriteImage);
            }
        }

        System.out.println("AssetLoader: Start Font Loading");
        File fontAssetFolder = new File("GameAssets/Fonts");
        for(File fontSubFolder: fontAssetFolder.listFiles()) {
            System.out.println("AssetLoader: Loading Font " + fontSubFolder.getName());
            for(File character: fontSubFolder.listFiles())
            {

                String name = character.getName().replace(".png","");
                System.out.println("AssetLoader: Loading " + fontSubFolder.getName() + "[ " + name + " ]");
                Image characterImage = null;
                try {
                    characterImage = new Image(character.getPath());
                } catch (SlickException e) {

                }
                if(!fonts.containsKey(fontSubFolder.getName()))
                {
                    fonts.put(fontSubFolder.getName(),new HashMap<>());
                }
                fonts.get(fontSubFolder.getName()).put(name,characterImage);
            }
        }
    }

    public static void setDefaultIconss(GameContainer gameContainer, String tinyIcon, String largeIcon) throws SlickException {
        icons = new String[2];
        icons[0] = tinyIcon;
        icons[1] = largeIcon;
        gameContainer.setIcons(icons);

    }


    public Image getCharacter(String font, String character)
    {
        return fonts.get(font).get(character);
    }

    public static Image getSprite(String name)
    {
        return spriteAssets.get(name);
    }


}

