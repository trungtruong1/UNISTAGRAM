package com.unistagram.memefeedapp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.unistagram.memefeedapp.model.MemeReaction;
import com.unistagram.memefeedapp.model.Reaction;

public interface MemeReactionService {
    public String save(String meme_id, String reaction_id, String user);

    public Optional<MemeReaction> getMemeReactionByMemeAndUser(String meme_id, String user_id);

    public Optional<MemeReaction> getMemeReactionById(String meme_id, String reaction_id, String user_id);

    public HashMap<String, Integer> getMemeReactionsByMemeId(String meme_id);

    public boolean removeReaction(String id);

}
