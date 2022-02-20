package org.pipeman.mcnbmm.sound;

import com.badlogic.gdx.audio.Sound;

public class Note {
    public final int note;
    final float volume = 1f;
    final Sound sound;
    public boolean selected;

    public Note(int note, Sound sound) {
        this.sound = sound;
        this.note = note;
    }

    public void play() {
        sound.play(volume, getPitch(), 0);
    }

    public float getPitch() {
        if (note == 0) return 0.5F; // Fis
        if (note == 12) return 1F; // Fis #2
        if (note == 24) return 2F; // Fis #3
        return (float) Math.pow(2, ((float) -12 + note) / 12);
    }

    public String toString() {
        return "V: " + volume + ", P: " + getPitch() + ", I: " + note;
    }
}
