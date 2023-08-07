package com.unistagram.meemfeedapp.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.io.UnsupportedEncodingException;

import com.unistagram.memefeedapp.model.Meme;
import org.bson.types.Binary;
import org.junit.jupiter.api.Test;

class MemeTest {
    @Test
    void testConstructor() throws UnsupportedEncodingException {
        Meme actualMeme = new Meme();
        Binary image = new Binary("AXAXAXAX".getBytes("UTF-8"));
        actualMeme.setImage(image);
        assertNull(actualMeme.getAuthor());
        assertNull(actualMeme.getId());
        assertSame(image, actualMeme.getImage());
        assertNull(actualMeme.getTimestamp());
        assertNull(actualMeme.getTitle());
    }

    @Test
    void testConstructor2() throws UnsupportedEncodingException {
        Binary image = new Binary("AXAXAXAX".getBytes("UTF-8"));
        Meme actualMeme = new Meme("Dr", image, "JaneDoe");
        Binary image2 = new Binary("AXAXAXAX".getBytes("UTF-8"));
        actualMeme.setImage(image2);
        assertEquals("JaneDoe", actualMeme.getAuthor());
        assertNull(actualMeme.getId());
        Binary image3 = actualMeme.getImage();
        assertSame(image2, image3);
        assertEquals(image, image3);
        assertEquals("Dr", actualMeme.getTitle());
    }
}

