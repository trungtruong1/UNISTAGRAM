package com.unistagram.memefeedapp.service;

import java.util.Optional;

import com.unistagram.memefeedapp.model.Reaction;

public interface ReactionService extends MemeService {

    public Optional<Reaction> getReactionById(String id);
}
