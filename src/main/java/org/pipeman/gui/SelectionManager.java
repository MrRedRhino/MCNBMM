package org.pipeman.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import org.pipeman.MCNBMM;
import org.pipeman.sound.Note;


public class SelectionManager {
    private float lastX;
    private float lastY;
    private boolean dragging;
    private float lastHeight;
    private float lastWidth;

    public void mouseDown(int x, int y, int xOffset, int yOffset) {
        lastX = x - xOffset;
        lastY = Gdx.graphics.getHeight() - y + yOffset;
    }

    public void drawSelection(ShapeRenderer renderer, int xOffset, int yOffset) {
        float width = Gdx.input.getX() - lastX - xOffset;
        float height = (Gdx.graphics.getHeight() - Gdx.input.getY()) - lastY + yOffset;
        float xPos = lastX + xOffset;
        float yPos = lastY - yOffset;

        if (Gdx.input.isTouched() && (Math.abs(width) > 3 || Math.abs(height) > 3)) {
            dragging = true;
        } else {
            dragging = false;
        }

        if (dragging) {
            renderer.rect(xPos, yPos, width, height);
            renderer.setColor(Color.MAGENTA);
            renderer.rect(xPos + width, yPos, lastWidth - width, height);

            int bound1 = (int) (xPos + width);
            int bound2 = (int) (xPos + width + lastWidth - width);
            int bound3 = (int) (yPos);
            int bound4 = (int) (yPos + height);

            if (lastWidth < width) {
                renderer.setColor(Color.CHARTREUSE);
            }

            for (int x = Math.min(bound1, bound2); x < Math.max(bound1, bound2); x++) {
                for (int y = Math.min(bound3, bound4); y < Math.max(bound3, bound4); y++) {
                    Vector2 v = GuiUtil.coordToNotePos(new Vector2(x, Gdx.graphics.getHeight() - y), yOffset, xOffset, 0);
                    Note n = MCNBMM.getGui().instruments.get(0).getNote((int) v.x, (int) v.y);
//                    MCNBMM.getGui().instruments.get(0).addNote((int) v.x, (int) v.y);
                    if (n == null) continue;
                    n.selected = lastWidth < width;
//                    renderer.circle(x, y, 3);
                }
            }
            renderer.setColor(Color.FOREST);
            renderer.rect(xPos, yPos + height, lastWidth, lastHeight - height);
            // ============================================================================

            bound1 = (int) (xPos);
            bound2 = (int) (xPos + lastWidth);
            bound3 = (int) (yPos + height);
            bound4 = (int) (yPos + height + lastHeight - height);

            if (lastHeight < height) {
                renderer.setColor(Color.CHARTREUSE);
            }

            for (int x = Math.min(bound1, bound2); x < Math.max(bound1, bound2); x++) {
                for (int y = Math.min(bound3, bound4); y < Math.max(bound3, bound4); y++) {
                    Vector2 v = GuiUtil.coordToNotePos(new Vector2(x, Gdx.graphics.getHeight() - y), yOffset, xOffset, 0);
                    Note n = MCNBMM.getGui().instruments.get(0).getNote((int) v.x, (int) v.y);
//                    MCNBMM.getGui().instruments.get(0).addNote((int) v.x, (int) v.y);
                    if (n == null) continue;
                    n.selected = lastHeight < height;
//                    renderer.circle(x, y, 3);
                }
            }

            lastHeight = height;
            lastWidth = width;
        }
    }
}
