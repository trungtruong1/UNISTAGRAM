package com.unistagram.meemfeedapp.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.unistagram.memefeedapp.model.MemeReaction;
import org.junit.jupiter.api.Test;

class MemeReactionTest {
    @Test
    void testConstructor() {
        MemeReaction actualMemeReaction = new MemeReaction();
        assertNull(actualMemeReaction.getId());
        assertNull(actualMemeReaction.getUser_id());
        assertNull(actualMemeReaction.getTimestamp());
        assertNull(actualMemeReaction.getReaction_id());
        assertNull(actualMemeReaction.getMeme_id());
    }
    @Test
    void testConstructor2() {
        MemeReaction actualMemeReaction = new MemeReaction("Meme id", "Reaction id", "User");

        assertNull(actualMemeReaction.getId());
        assertEquals("User", actualMemeReaction.getUser_id());
        assertNull(actualMemeReaction.getTimestamp());
        assertEquals("Reaction id", actualMemeReaction.getReaction_id());
        assertEquals("Meme id", actualMemeReaction.getMeme_id());
    }
}

