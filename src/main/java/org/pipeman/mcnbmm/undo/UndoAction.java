package org.pipeman.mcnbmm.undo;

public interface UndoAction {
    void undo();
    void redo();
}
