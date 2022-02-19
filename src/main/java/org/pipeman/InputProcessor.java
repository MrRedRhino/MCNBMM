package org.pipeman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class InputProcessor implements com.badlogic.gdx.InputProcessor {
    @Override
    public boolean keyDown(int i) {
        switch (i) {
            case Input.Keys.SPACE -> MCNBMM.getGui().startStop();
            case Input.Keys.DEL -> MCNBMM.getGui().deleteSelectedNotes();
        }
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        MCNBMM.getGui().mouseClick(i, i1, i3);
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(float v, float v1) {
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            MCNBMM.getGui().moveSong(v1 * -1, v);
        } else {
            MCNBMM.getGui().moveSong(v, v1 * -1);
        }
        return false;
    }
}
