package org.pipeman.mcnbmm.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import org.pipeman.mcnbmm.MCNBMM;
import org.pipeman.mcnbmm.sound.Note;


public class SelectionManager {
    private float lastX;
    private float lastY;
    private float lastHeight;
    private float lastWidth;
    private boolean aborted = false;

    public void mouseDown(int x, int y, int xOffset, int yOffset) {
        lastX = x - xOffset;
        lastY = Gdx.graphics.getHeight() - y + yOffset;
        aborted = false;
    }

    public void drawSelection(ShapeRenderer renderer, int xOffset, int yOffset) {
        float width = Gdx.input.getX() - lastX - xOffset;
        float height = (Gdx.graphics.getHeight() - Gdx.input.getY()) - lastY + yOffset;
        float xPos = lastX + xOffset;
        float yPos = lastY - yOffset;

        if (Gdx.input.isTouched() && (Math.abs(width) > 3 || Math.abs(height) > 3) && !aborted) {
            renderer.rect(xPos, yPos, width, height);

            for (int x = Math.min((int) (xPos + width), (int) (xPos + lastWidth));
                 x < Math.max((int) (xPos + width), (int) (xPos + lastWidth)); x++) {
                for (int y = Math.min((int) (yPos), (int) (yPos + height));
                     y < Math.max((int) (yPos), (int) (yPos + height)); y++) {
                    doNoteThings(xOffset, yOffset, width, x, y, lastWidth);
                }
            }

            for (int x = Math.min((int) (xPos), (int) (xPos + lastWidth));
                 x < Math.max((int) xPos, (int) (xPos + lastWidth)); x++) {
                for (int y = Math.min((int) (yPos + height), (int) (yPos + lastHeight));
                     y < Math.max((int) (yPos + height), (int) (yPos + lastHeight)); y++) {
                    doNoteThings(xOffset, yOffset, height, x, y, lastHeight);
                }
            }

            lastHeight = height;
            lastWidth = width;
        }
    }

    public void setAborted(boolean aborted) {
        this.aborted = aborted;
    }

    private void doNoteThings(int xOffset, int yOffset, float v2, int x, int y, float v3) {
        int i = GuiUtil.coordToInstrumentIndex(new Vector2(x, Gdx.graphics.getHeight() - y), yOffset);
        if (i < 0 || i > MCNBMM.getGui().instruments.size() - 1) return;

        Vector2 v = GuiUtil.coordToNotePos(new Vector2(x, Gdx.graphics.getHeight() - y), yOffset, xOffset, i);
        Note n = MCNBMM.getGui().instruments.get(i).getNote((int) v.x, (int) v.y);
        float v1 = Math.abs(v3) - Math.abs(v2);
        if (n == null || v1 == 0) return;
        n.selected = v1 < 0;
    }
}
