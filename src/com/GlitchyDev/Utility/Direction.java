package com.GlitchyDev.Utility;

import java.util.ArrayList;

public enum Direction {
    NORTH,
    EAST,
    SOUTH,
    WEST;

    public Direction reverse()
    {
        switch(this)
        {
            case NORTH:
                return SOUTH;
            case EAST:
                return WEST;
            case SOUTH:
                return NORTH;
            case WEST:
                return EAST;
        }
        return NORTH;
    }






}
