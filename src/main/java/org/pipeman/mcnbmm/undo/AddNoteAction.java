package org.pipeman.mcnbmm.undo;

import org.pipeman.mcnbmm.MCNBMM;
import org.pipeman.mcnbmm.sound.Instrument;
import org.pipeman.mcnbmm.sound.Note;

public class AddNoteAction implements UndoAction {
    int tick;
    int note;
    Instrument instrument;

    public AddNoteAction(int tick, int note, Instrument instrument) {
        this.tick = tick;
        this.note = note;
        this.instrument = instrument;
    }

    @Override
    public void undo() {
        instrument.notes.get(tick).removeIf(n -> n.note == note);
        MCNBMM.getGui().undoRedo.actions.add(new DelNoteAction(tick, note, instrument));
    }

    @Override
    public void redo() {
        instrument.addNote(tick, note);
        MCNBMM.getGui().undoRedo.actions.add(new AddNoteAction(tick, note, instrument));
    }

    @Override
    public String toString() {
        return "Add note: " + note + " " + tick;
    }
}
