package com.unistagram.meemfeedapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.unistagram.memefeedapp.model.MemeReaction;
import com.unistagram.memefeedapp.repositories.MemeReactionRepository;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

import com.unistagram.memefeedapp.service.MemeReactionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {MemeReactionServiceImpl.class})
@ExtendWith(SpringExtension.class)
class MemeReactionServiceImplTest {
    @MockBean
    private MemeReactionRepository memeReactionRepository;

    @Autowired
    private MemeReactionServiceImpl memeReactionServiceImpl;

    @MockBean
    private MongoTemplate mongoTemplate;
    @Test
    void testSave() {
        MemeReaction memeReaction = new MemeReaction();
        memeReaction.setId("42");
        memeReaction.setMeme_id("Meme id");
        memeReaction.setReaction_id("Reaction id");
        memeReaction.setTimestamp(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        memeReaction.setUser_id("User id");
        when(memeReactionRepository.save(Mockito.<MemeReaction>any())).thenReturn(memeReaction);
        assertNull(memeReactionServiceImpl.save("Meme id", "Reaction id", "User"));
        verify(memeReactionRepository).save(Mockito.<MemeReaction>any());
    }
    @Test
    void testGetMemeReactionByMemeAndUser() {
        MemeReaction memeReaction = new MemeReaction();
        memeReaction.setId("42");
        memeReaction.setMeme_id("Meme id");
        memeReaction.setReaction_id("Reaction id");
        memeReaction.setTimestamp(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        memeReaction.setUser_id("User id");
        when(mongoTemplate.findOne(Mockito.<Query>any(), Mockito.<Class<MemeReaction>>any())).thenReturn(memeReaction);
        assertTrue(memeReactionServiceImpl.getMemeReactionByMemeAndUser("Meme id", "User id").isPresent());
        verify(mongoTemplate).findOne(Mockito.<Query>any(), Mockito.<Class<MemeReaction>>any());
    }

    @Test
    void testGetMemeReactionByMemeAndUser2() {
        assertEquals(Optional.empty(), memeReactionServiceImpl.getMemeReactionByMemeAndUser("null", "null"));
    }

    @Test
    void testGetMemeReactionById() {
        MemeReaction memeReaction = new MemeReaction();
        memeReaction.setId("42");
        memeReaction.setMeme_id("Meme id");
        memeReaction.setReaction_id("Reaction id");
        memeReaction.setTimestamp(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        memeReaction.setUser_id("User id");
        when(mongoTemplate.findOne(Mockito.<Query>any(), Mockito.<Class<MemeReaction>>any())).thenReturn(memeReaction);
        assertTrue(memeReactionServiceImpl.getMemeReactionById("Meme id", "Reaction id", "User id").isPresent());
        verify(mongoTemplate).findOne(Mockito.<Query>any(), Mockito.<Class<MemeReaction>>any());
    }

    @Test
    void testGetMemeReactionById2() {
        assertEquals(Optional.empty(), memeReactionServiceImpl.getMemeReactionById("I want to sleep", "not lmao", "lmao"));
    }

    @Test
    void testGetMemeReactionsByMemeId() {
        when(mongoTemplate.find(Mockito.<Query>any(), Mockito.<Class<MemeReaction>>any())).thenReturn(new ArrayList<>());
        assertTrue(memeReactionServiceImpl.getMemeReactionsByMemeId("Meme id").isEmpty());
        verify(mongoTemplate).find(Mockito.<Query>any(), Mockito.<Class<MemeReaction>>any());
    }
    @Test
    void testGetMemeReactionsByMemeId2() {
        MemeReaction memeReaction = new MemeReaction();
        memeReaction.setId("42");
        memeReaction.setMeme_id("meme_id");
        memeReaction.setReaction_id("meme_id");
        memeReaction.setTimestamp(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        memeReaction.setUser_id("meme_id");

        ArrayList<MemeReaction> memeReactionList = new ArrayList<>();
        memeReactionList.add(memeReaction);
        when(mongoTemplate.find(Mockito.<Query>any(), Mockito.<Class<MemeReaction>>any())).thenReturn(memeReactionList);
        HashMap<String, Integer> actualMemeReactionsByMemeId = memeReactionServiceImpl
                .getMemeReactionsByMemeId("Meme id");
        assertEquals(1, actualMemeReactionsByMemeId.size());
        Integer expectedGetResult = new Integer(1);
        assertEquals(expectedGetResult, actualMemeReactionsByMemeId.get("meme_id"));
        verify(mongoTemplate).find(Mockito.<Query>any(), Mockito.<Class<MemeReaction>>any());
    }
    @Test
    void testRemoveReaction() {
        when(mongoTemplate.remove(Mockito.<Query>any(), Mockito.<Class<Object>>any())).thenReturn(null);
        assertTrue(memeReactionServiceImpl.removeReaction("42"));
        verify(mongoTemplate).remove(Mockito.<Query>any(), Mockito.<Class<Object>>any());
    }
}

