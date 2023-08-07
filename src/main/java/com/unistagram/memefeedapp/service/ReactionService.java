package com.unistagram.memefeedapp.service;

import java.util.List;
import java.util.Optional;

import com.unistagram.memefeedapp.model.Reaction;

public interface ReactionService extends MemeService {

    public Optional<Reaction> getReactionById(String id);

    public List<Reaction> getReactionByUsername(String username);
}
