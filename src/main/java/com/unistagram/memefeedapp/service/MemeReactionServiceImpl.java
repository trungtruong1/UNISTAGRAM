package com.unistagram.memefeedapp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.mongodb.client.result.UpdateResult;
import com.unistagram.memefeedapp.model.MemeReaction;
import com.unistagram.memefeedapp.model.Reaction;
import com.unistagram.memefeedapp.repositories.MemeReactionRepository;

@Service
public class MemeReactionServiceImpl implements MemeReactionService {
    @Autowired
    private MemeReactionRepository memeReactionRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public String save(String meme_id, String reaction_id, String user) {
        MemeReaction new_meme_reaction = new MemeReaction(meme_id, reaction_id, user);
        memeReactionRepository.save(new_meme_reaction);
        return new_meme_reaction.getId();
    }

    @Override
    public Optional<MemeReaction> getMemeReactionByMemeAndUser(String meme_id, String user_id) {
        Query query = new Query(Criteria.where("meme_id").is(meme_id)
                                        .and("user_id").is(user_id));
        MemeReaction meme_reaction = mongoTemplate.findOne(query, MemeReaction.class);
        if(meme_reaction == null) {
            return Optional.empty();
        }
        return Optional.of(meme_reaction);
    }

    @Override
    public Optional<MemeReaction> getMemeReactionById(String meme_id, String reaction_id, String user_id) {
        Query query = new Query(Criteria.where("meme_id").is(meme_id)
                                        .and("reaction_id").is(reaction_id)
                                        .and("user_id").is(user_id));
        MemeReaction meme_reaction = mongoTemplate.findOne(query, MemeReaction.class);
        if(meme_reaction == null) {
            return Optional.empty();
        }
        return Optional.of(meme_reaction);
    }

    @Override
    public HashMap<String, Integer> getMemeReactionsByMemeId(String meme_id) {
        Query query = new Query(Criteria.where("meme_id").is(meme_id));
        List<MemeReaction> meme_reactions = mongoTemplate.find(query, MemeReaction.class);

        HashMap<String, Integer> reaction_count = new HashMap<String, Integer>();
        for(MemeReaction meme_reaction: meme_reactions) {
            reaction_count.merge(meme_reaction.getReaction_id(), 1, Integer::sum);
        }

        return reaction_count;
    }

    @Override
    public boolean removeReaction(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query, MemeReaction.class);
        return true;
    }
}
