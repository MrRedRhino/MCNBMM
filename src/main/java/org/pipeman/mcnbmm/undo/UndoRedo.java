package org.pipeman.mcnbmm.undo;

import org.pipeman.mcnbmm.sound.Instrument;

import java.util.ArrayList;

public class UndoRedo {
    ArrayList<UndoAction> actions = new ArrayList<>();
    private int posInActions = 0;

    public void addNote(int tick, int note, Instrument i) {
        actions.add(new AddNoteAction(tick, note, i));
        posInActions++;
        System.out.println(posInActions);
        System.out.println(actions);
    }

    public void delNote(int tick, int note, Instrument i) {
        actions.add(new DelNoteAction(tick, note, i));
    }

    public void undo() {
        actions.get(posInActions).undo();
        posInActions = Math.max(0, posInActions - 1);
        System.out.println("Undoing:");
        System.out.println(posInActions);
        System.out.println(actions);
    }

    public void redo() {
        actions.get(posInActions).redo();
        posInActions = Math.min(actions.size() - 1, posInActions + 1);
        System.out.println("Redoing:");
        System.out.println(posInActions);
        System.out.println(actions);
    }
}
