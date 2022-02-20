package org.pipeman.mcnbmm.undo;

import org.pipeman.mcnbmm.MCNBMM;
import org.pipeman.mcnbmm.sound.Instrument;

public class DelNoteAction implements UndoAction {
    int tick;
    int note;
    Instrument instrument;

    public DelNoteAction(int tick, int note, Instrument instrument) {
        this.tick = tick;
        this.note = note;
        this.instrument = instrument;
    }

    @Override
    public void undo() {
        instrument.addNote(tick, note);
    }

    @Override
    public void redo() {
        instrument.notes.get(tick).removeIf(n -> n.note == note);
    }

    @Override
    public String toString() {
        return "Del note: " + note + " " + tick;
    }
}
