package com.GlitchyDev.Utility;

public enum GButtons {
    UP,
    RIGHT,
    DOWN,
    LEFT,
    A,
    B,
    START,
    SELECT,
    L,
    R;


    public Direction getEquivalent()
    {
        switch(this)
        {
            case UP:
                return Direction.NORTH;
            case RIGHT:
                return Direction.EAST;
            case DOWN:
                return Direction.SOUTH;
            case LEFT:
                return Direction.WEST;
        }
        return Direction.NORTH;
    }

    public GButtons getReverse()
    {
        switch(this)
        {
            case UP:
                return DOWN;
            case RIGHT:
                return LEFT;
            case DOWN:
                return UP;
            case LEFT:
                return RIGHT;
        }
        return null;
    }


    public static GButtons[] getDirections()
    {
        return new GButtons[]{UP,RIGHT,DOWN,LEFT};
    }

}
