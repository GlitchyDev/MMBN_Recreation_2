package com.GlitchyDev.Utility;

public enum GButtons {
    A,
    B,
    START,
    SELECT,
    L,
    R,
    UP,
    LEFT,
    DOWN,
    RIGHT;


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

    public static GButtons[] getDirections()
    {
        return new GButtons[]{UP,RIGHT,DOWN,LEFT};
    }
}
