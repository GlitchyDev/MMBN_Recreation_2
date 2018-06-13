package com.GlitchyDev.Utility;

import java.util.ArrayList;

public enum Direction {
    NORTH,
    EAST,
    SOUTH,
    WEST,
    NONE,
    NORTHEAST,
    NORTHWEST,
    SOUTHEAST,
    SOUTHWEST;

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


    @Override
    public String toString() {
        switch(this)
        {
            case NORTH:
                return "North";
            case EAST:
                return "East";
            case SOUTH:
                return "South";
            case WEST:
                return "West";
            case NORTHEAST:
                return "NorthEast";
            case NORTHWEST:
                return "NorthWest";
            case SOUTHEAST:
                return "SouthEast";
            case SOUTHWEST:
                return "SouthWest";
        }
        return "NONE";
    }

    public static Direction getDirection(long[] controlMapping)
    {
        if(controlMapping[0] >= 1) {
            if(controlMapping[1] >= 1) {
                return NORTHEAST;
            }
            else {
                if(controlMapping[3] >= 1) {
                    return NORTHWEST;
                }
                else {
                    return NORTH;
                }
            }
        }
        else {
            if(controlMapping[2] >= 1) {
                if(controlMapping[1] >= 1) {
                    return SOUTHEAST;
                }
                else {
                    if(controlMapping[3] >= 1) {
                        return SOUTHWEST;
                    }
                    else {
                        return SOUTH;
                    }
                }
            }
            else {
                if(controlMapping[1] >= 1) {
                    return EAST;
                }
                else {
                    if(controlMapping[3] >= 1) {
                        return WEST;
                    }
                    else {
                        return NONE;
                    }
                }
            }
        }

    }

}
