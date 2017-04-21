package com.convertion.repositories;

import java.util.Collection;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.convertion.dto.Edges;

@Repository
public class EdgesRepositoryImpl implements EdgesRepository {

    //private static final String KEY = "Edges";

    private RedisTemplate<String, Edges> redisTemplate;
    private HashOperations<String, String, Edges> hashOps;

    @Autowired
    public EdgesRepositoryImpl(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init() {
        hashOps = redisTemplate.opsForHash();
    }

    /** {@inheritDoc} */
    @Override
    public void saveEdges(Edges edge) {
        hashOps.put(edge.getFrom(), edge.getCode(), edge);
    }

    /** {@inheritDoc} */
    @Override
    public Edges findEdge(String code) {
        String[] str = code.split("_");
        return hashOps.get(str[0], code);
    }

    /** {@inheritDoc} */
    @Override
    public Collection<Edges> findAllEdges(String from) {
        return hashOps.entries(from).values();
    }

    /** {@inheritDoc} */
    @Override
    public void deleteEdge(String code) {
        String[] str = code.split("_");
        hashOps.delete(str[0], code);
    }

}
