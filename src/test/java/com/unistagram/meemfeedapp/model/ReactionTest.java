package com.unistagram.meemfeedapp.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.io.UnsupportedEncodingException;

import com.unistagram.memefeedapp.model.Reaction;
import org.bson.types.Binary;
import org.junit.jupiter.api.Test;

class ReactionTest {
    @Test
    void testConstructor() {
        Reaction actualReaction = new Reaction();
        assertNull(actualReaction.getAuthor());
        assertNull(actualReaction.getTitle());
        assertNull(actualReaction.getTimestamp());
        assertNull(actualReaction.getImage());
        assertNull(actualReaction.getId());
    }
    @Test
    void testConstructor2() throws UnsupportedEncodingException {
        Binary image = new Binary("AXAXAXAX".getBytes("UTF-8"));
        Reaction actualReaction = new Reaction("Dr", image, "JaneDoe");

        assertEquals("JaneDoe", actualReaction.getAuthor());
        assertEquals("Dr", actualReaction.getTitle());
        Binary image2 = actualReaction.getImage();
        assertSame(image, image2);
        assertNull(actualReaction.getId());
        assertEquals(8, image2.length());
        assertEquals((byte) 0, image2.getType());
        assertEquals(8, image2.getData().length);
    }
}

