package com.example.treest;

public enum TransitionType {
    ENTER_FROM_LEFT,
    ENTER_FROM_RIGHT,
    DEFAULT;

    int getStart() {
        switch (this) {
            case ENTER_FROM_LEFT:
                return R.anim.enter_from_left;
            case ENTER_FROM_RIGHT:
                return R.anim.enter_from_right;
            default:
                return 0;
        }
    }

    int getEnd() {
        switch (this) {
            default:
                return 0;
        }
    }


}
