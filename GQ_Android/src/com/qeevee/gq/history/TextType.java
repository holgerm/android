package com.qeevee.gq.history;

public enum TextType implements HistoryItemModifier {
    PLAIN, QUESTION, REACTION_ON_CORRECT, REACTION_ON_WRONG;

    /**
     * Default for actors is {@link TextType#PLAIN}.
     */
    public static final TextType DEFAULT = PLAIN;
}
