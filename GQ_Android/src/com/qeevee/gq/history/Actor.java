package com.qeevee.gq.history;

public enum Actor implements HistoryItemModifier {
    EGO, NPC, GAME;

    /**
     * Default for actors is {@link Actor#GAME}.
     */
    public static final Actor DEFAULT = GAME;

}
