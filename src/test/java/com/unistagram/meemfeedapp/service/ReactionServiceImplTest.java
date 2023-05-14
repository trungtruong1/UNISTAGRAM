package com.unistagram.meemfeedapp.service;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.unistagram.memefeedapp.model.Reaction;
import com.unistagram.memefeedapp.repositories.MemeRepository;
import com.unistagram.memefeedapp.repositories.ReactionRepository;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;

import com.unistagram.memefeedapp.service.ReactionServiceImpl;
import org.bson.types.Binary;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

@ContextConfiguration(classes = {ReactionServiceImpl.class})
@ExtendWith(SpringExtension.class)
class ReactionServiceImplTest {
    @MockBean
    private MemeRepository memeRepository;

    @MockBean
    private MongoTemplate mongoTemplate;

    @MockBean
    private ReactionRepository reactionRepository;

    @Autowired
    private ReactionServiceImpl reactionServiceImpl;
    @Test
    void testSave() throws IOException {
        Reaction reaction = new Reaction();
        reaction.setAuthor("JaneDoe");
        reaction.setId("42");
        reaction.setImage(new Binary("AXAXAXAX".getBytes("UTF-8")));
        reaction.setTimestamp(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        reaction.setTitle("Dr");
        when(reactionRepository.save(Mockito.<Reaction>any())).thenReturn(reaction);
        assertNull(reactionServiceImpl.save("Dr",
                new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))), "JaneDoe"));
        verify(reactionRepository).save(Mockito.<Reaction>any());
    }
    @Test
    void testGetReactionById() throws UnsupportedEncodingException {
        Reaction reaction = new Reaction();
        reaction.setAuthor("JaneDoe");
        reaction.setId("42");
        reaction.setImage(new Binary("AXAXAXAX".getBytes("UTF-8")));
        reaction.setTimestamp(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        reaction.setTitle("Dr");
        when(mongoTemplate.findOne(Mockito.<Query>any(), Mockito.<Class<Reaction>>any())).thenReturn(reaction);
        assertTrue(reactionServiceImpl.getReactionById("42").isPresent());
        verify(mongoTemplate).findOne(Mockito.<Query>any(), Mockito.<Class<Reaction>>any());
    }
}

