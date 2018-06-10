package com.GlitchyDev.Utility;

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
