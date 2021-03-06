package org.pipeman.mcnbmm.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import org.pipeman.mcnbmm.sound.Instrument;
import org.pipeman.mcnbmm.sound.Instruments;
import org.pipeman.mcnbmm.sound.Note;
import org.pipeman.mcnbmm.undo.UndoRedo;

import java.util.*;

public class GUI {
    private final ShapeRenderer shapeRenderer = new ShapeRenderer();
    int songScrollOffsetX = 0;
    int songScrollOffsetY = 90;
    final ArrayList<Instrument> instruments = new ArrayList<>();
    boolean playing = false;
    final int tps = 10;
    int cursorTick = 0;
    final BitmapFont font = new BitmapFont();
    final PlayCursor cursor = new PlayCursor();
    final SelectionManager selectionManager;
    public final UndoRedo undoRedo = new UndoRedo();

    public GUI() {
        selectionManager = new SelectionManager();
        instruments.add(new Instrument(Instruments.HARP, 0));
        instruments.add(new Instrument(Instruments.BELLS, 1));
        instruments.add(new Instrument(Instruments.BIT, 2));
        Instrument i = instruments.get(0);
        Instrument i2 = instruments.get(1);

        HashMap<Integer, ArrayList<Note>> yes = new HashMap<>();
        HashMap<Integer, ArrayList<Note>> yes2 = new HashMap<>();
        for (int j = 0; j < 24; j++) {
            ArrayList<Note> tmp = new ArrayList<>();
            tmp.add(new Note(j, i.getSound()));
            ArrayList<Note> tmp2 = new ArrayList<>();
            tmp2.add(new Note(j, i2.getSound()));
            yes.put(j, tmp);
            yes2.put(j, tmp2);
        }

        i.setNotes(yes);
        i2.setNotes(yes2);
    }

    public void draw(SpriteBatch batch) {
        batch.begin();
        font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond() + " - " + Gdx.graphics.getDeltaTime() + "ms", 20, Gdx.graphics.getHeight());
        batch.end();

        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.begin();

        for (Instrument instrument : instruments) {
            instrument.drawLines(shapeRenderer, songScrollOffsetY);
        }

        GuiUtil.drawCross(shapeRenderer, songScrollOffsetY, instruments.size());

        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        for (Instrument instrument : instruments) {
            instrument.drawNotes(shapeRenderer, songScrollOffsetX, songScrollOffsetY);
            instrument.drawNoteNames(shapeRenderer, songScrollOffsetY);
        }

        batch.begin();
        for (Instrument instrument : instruments) {
            instrument.drawIcon(batch, songScrollOffsetY);
        }
        batch.end();

        shapeRenderer.set(ShapeRenderer.ShapeType.Line);
        cursor.draw(shapeRenderer, songScrollOffsetX, cursorTick, playing);
        shapeRenderer.setColor(Color.GRAY);
        Gdx.gl.glLineWidth(2);
        selectionManager.drawSelection(shapeRenderer, songScrollOffsetX, songScrollOffsetY);
        shapeRenderer.end();
    }

    public void dispose() {
        font.dispose();
        shapeRenderer.dispose();
    }

    public void moveSong(double x, double y) {
        songScrollOffsetX += songScrollOffsetX < 0 || x < 0 ? x * 30 : 0;
        songScrollOffsetY += songScrollOffsetY < 90 || y < 0 ? y * 30 : 0;
    }

    public void mouseClick(int x, int y, int button) {
        Vector2 mousePos = new Vector2(x, y);
        selectionManager.mouseDown(x, y, songScrollOffsetX, songScrollOffsetY);

        if (button == Input.Buttons.LEFT) {
            if (x - songScrollOffsetX < 300) return;

            int instrumentIndex = GuiUtil.coordToInstrumentIndex(mousePos, songScrollOffsetY);
            Vector2 noteVector = GuiUtil.coordToNotePos(mousePos, songScrollOffsetY, songScrollOffsetX, instrumentIndex);
            if (instrumentIndex < 0 || instrumentIndex >= instruments.size() || noteVector.y < 0 || noteVector.y > 24) return;

            int tick = (int) noteVector.x;
            int note = (int) noteVector.y;
            Instrument instrument = instruments.get(instrumentIndex);

            if (!Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                instruments.forEach(i -> i.notes.values().forEach(notes -> notes.forEach(n -> n.selected = false)));
            }

            if (Gdx.input.isKeyPressed(Input.Keys.SYM)) {
                instrument.addNote(tick, note);
                undoRedo.addNote(tick, note, instrument);
                selectionManager.setAborted(true);
            } else {
                selectionManager.setAborted(instrument.selectNote(tick, note));
            }
        }
    }

    public void deleteSelectedNotes() {
        instruments.forEach(i -> i.notes.values().forEach(notes -> notes.removeIf(n -> (n.selected))));
    }

    public void undo() {
        undoRedo.undo();
    }

    public void redo() {
        undoRedo.redo();
    }

    public void startStop() {
        playing = !playing;
        if (playing) {
            Timer t = new Timer();
            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (!playing) {
                        cursorTick = 0;
                        t.cancel();
                        return;
                    }
                    for (Instrument i : instruments) {
                        i.playNote(cursorTick);
                    }
                    cursorTick++;

                }
            }, 0, 1000 / tps);
        } else {
            cursorTick = 0;
        }
    }
}
