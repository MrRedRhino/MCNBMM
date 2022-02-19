package org.pipeman.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import org.pipeman.MCNBMM;
import org.pipeman.gui.ColorPalette;

import java.util.*;

public class Instrument {
    Texture icon;
    Sound sound;
    final int lineIndex;
    final int lineDistance = MCNBMM.sheetLineDistance;
    final int distanceX = 15;
    final int distanceY = 10;
    public HashMap<Integer, ArrayList<Note>> notes = new HashMap<>();

    public Instrument(Instruments instrument, int lineIndex) {
        this.lineIndex = lineIndex;
        switch (instrument) {
            case BASS -> {
                sound = Gdx.audio.newSound(Gdx.files.internal("sounds/bd.mp3"));
                icon = new Texture("textures/oak_log.png");
            }
            case SNARE -> {
                sound = Gdx.audio.newSound(Gdx.files.internal("sounds/snare.mp3"));
                icon = new Texture("textures/sand.png");
            }
            case HIHAT -> {
                sound = Gdx.audio.newSound(Gdx.files.internal("sounds/hat.mp3"));
                icon = new Texture("textures/glass.png");
            }
            case KICK -> {
                sound = Gdx.audio.newSound(Gdx.files.internal("sounds/bd.mp3"));
                icon = new Texture("textures/stone.png");
            }
            case BELLS -> {
                sound = Gdx.audio.newSound(Gdx.files.internal("sounds/bell.mp3"));
                icon = new Texture("textures/gold_block.png");
            }
            case FLUTE -> {
                sound = Gdx.audio.newSound(Gdx.files.internal("sounds/flute.mp3"));
                icon = new Texture("textures/clay_block.png");
            }
            case CHIME -> {
                sound = Gdx.audio.newSound(Gdx.files.internal("sounds/icechime.mp3"));
                icon = new Texture("textures/ice.png");
            }
            case GUITAR -> {
                sound = Gdx.audio.newSound(Gdx.files.internal("sounds/guitar.mp3"));
                icon = new Texture("textures/wool.png");
            }
            case XYLOPHONE -> {
                sound = Gdx.audio.newSound(Gdx.files.internal("sounds/xylobone.mp3"));
                icon = new Texture("textures/bone_block.png");
            }
            case IRON_XYLOPHONE -> {
                sound = Gdx.audio.newSound(Gdx.files.internal("sounds/iron_xylophone.mp3"));
                icon = new Texture("textures/iron_block.png");
            }
            case COW_BELL -> {
                sound = Gdx.audio.newSound(Gdx.files.internal("sounds/bell.mp3"));
                icon = new Texture("textures/soul_sand.png");
            }
            case DIDGERIDOO -> {
                sound = Gdx.audio.newSound(Gdx.files.internal("sounds/didgeridoo.mp3"));
                icon =  new Texture("pumpkin.png");
            }
            case BIT -> {
                sound = Gdx.audio.newSound(Gdx.files.internal("sounds/bit.mp3"));
                icon = new Texture("textures/emerald_block.png");
            }
            case BANJO -> {
                sound = Gdx.audio.newSound(Gdx.files.internal("sounds/banjo.mp3"));
                icon = new Texture("textures/hay_bale.ong");
            }
            case PLING -> {
                sound = Gdx.audio.newSound(Gdx.files.internal("sounds/pling.mp3"));
                icon = new Texture("textures/glowstone.png");
            }
            case HARP -> {
                sound = Gdx.audio.newSound(Gdx.files.internal("sounds/harp.mp3"));
                icon = new Texture("textures/grass_block.png");
            }
        }
    }

    public void drawLines(ShapeRenderer renderer, int yOffset) {
        int y = lineIndex * lineDistance + yOffset;

        renderer.setColor(ColorPalette.sheetLine);
        for (int line = 0; line < 24; line++) {
            Gdx.gl.glLineWidth(1);
            Vector2 start = new Vector2(290, Gdx.graphics.getHeight() - (line * distanceY + y));
            Vector2 end = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() - (line * distanceY + y));
            renderer.line(start, end);
        }
    }

    public void drawNotes(ShapeRenderer renderer, int xOffset, int yOffset) {
        int xPos;
        int y = lineIndex * lineDistance + yOffset + 230;
        for (Map.Entry<Integer, ArrayList<Note>> entry : notes.entrySet()) {
            xPos = entry.getKey() * distanceX + xOffset + 300;
            for (Note n : entry.getValue()) {
                if (xPos < 290) continue;
                if (xPos > Gdx.graphics.getWidth()) break;

                if (n.selected) {
                    renderer.setColor(Color.GREEN);
                    renderer.rect(xPos, Gdx.graphics.getHeight() - (y - n.note * distanceY), 15, 10);
                    renderer.setColor(Color.BLACK);
                } else {
                    renderer.rect(xPos, Gdx.graphics.getHeight() - (y - n.note * distanceY), 15, 10);
                }
            }
        }
    }

    public void drawIcon(SpriteBatch batch, int yOffset) {
        batch.draw(icon, 221, Gdx.graphics.getHeight() - yOffset - 80 - lineIndex * lineDistance);
    }

    public void playNote(int tick) {
        if (!notes.containsKey(tick)) return;
        for (Note n : notes.get(tick)) {
            n.play();
        }
    }

    public void setNotes(HashMap<Integer, ArrayList<Note>> notes) {
        this.notes = notes;
    }

    public Sound getSound() {
        return sound;
    }

    public boolean selectNote(int tick, int note) {
        Note n = getNote(tick, note);
        if (n != null) {
            n.selected = !n.selected;
        }
        return n == null;
    }

    // returns true if the note has been added, false ich it already existed
    public boolean addNote(int tick, int note) {
        Note newNote = new Note(note, this.getSound());
        if (notes.containsKey(tick)) {
            ArrayList<Note> ns = notes.get(tick);
            for (Note n : ns) {
                if (n.note == note) {
                    return false;
                }
            }
            ns.add(newNote);
        } else {
            notes.put(tick, new ArrayList<>(List.of(newNote)));
        }
        return true;
    }

    public void addOrSelectNote(int tick, int note) {
        Note newNote = new Note(note, this.getSound());
        if (notes.containsKey(tick)) {
            ArrayList<Note> ns = notes.get(tick);
            for (Note n : ns) {
                if (n.note == note) {
                    n.selected = !n.selected;
                    return;
                }
            }
            ns.add(newNote);
        } else {
            notes.put(tick, new ArrayList<>(List.of(newNote)));
        }
    }

    public Note getNote(int tick, int note) {
        if (notes.containsKey(tick)) {
            for (Note n : notes.get(tick)) {
                if (n.note == note) {
                    return n;
                }
            }
        }
        return null;
    }
}
