package com.unistagram.meemfeedapp.service;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.unistagram.memefeedapp.model.Meme;
import com.unistagram.memefeedapp.repositories.MemeRepository;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;

import com.unistagram.memefeedapp.service.MemeServiceImpl;
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

@ContextConfiguration(classes = {MemeServiceImpl.class})
@ExtendWith(SpringExtension.class)
class MemeServiceImplTest {
    @MockBean
    private MemeRepository memeRepository;

    @Autowired
    private MemeServiceImpl memeServiceImpl;

    @MockBean
    private MongoTemplate mongoTemplate;
    @Test
    void testSave() throws IOException {
        Meme meme = new Meme();
        meme.setAuthor("JaneDoe");
        meme.setId("42");
        meme.setImage(new Binary("AXAXAXAX".getBytes("UTF-8")));
        meme.setTimestamp(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        meme.setTitle("Dr");
        when(memeRepository.save(Mockito.<Meme>any())).thenReturn(meme);
        assertNull(memeServiceImpl.save("Dr",
                new MockMultipartFile("Name", new ByteArrayInputStream("AXAXAXAX".getBytes("UTF-8"))), "JaneDoe"));
        verify(memeRepository).save(Mockito.<Meme>any());
    }
    @Test
    void testGetMemeById() throws UnsupportedEncodingException {
        Meme meme = new Meme();
        meme.setAuthor("JaneDoe");
        meme.setId("42");
        meme.setImage(new Binary("AXAXAXAX".getBytes("UTF-8")));
        meme.setTimestamp(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        meme.setTitle("Dr");
        when(mongoTemplate.findOne(Mockito.<Query>any(), Mockito.<Class<Meme>>any())).thenReturn(meme);
        assertTrue(memeServiceImpl.getMemeById("42").isPresent());
        verify(mongoTemplate).findOne(Mockito.<Query>any(), Mockito.<Class<Meme>>any());
    }
}

