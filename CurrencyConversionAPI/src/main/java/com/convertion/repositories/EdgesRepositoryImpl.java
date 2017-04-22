package com.convertion.repositories;

import java.util.Collection;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.convertion.dto.Edges;

/**
 * This is an implementation of the EdgesRepository
 * 
 * @author KAKANAKOU Miguel Stephane (Skakanakou@gmail.com)
 */

@Repository
public class EdgesRepositoryImpl implements EdgesRepository {

    private static final String KEY = "Edges";

    private RedisTemplate<String, Edges> redisTemplate;
    private HashOperations<String, String, Edges> hashOps;

    @SuppressWarnings({ "unchecked", "rawtypes" })
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
        hashOps.put(KEY, edge.getCode(), edge);
    }

    /** {@inheritDoc} */
    @Override
    public Edges findEdge(String code) {
        String[] str = code.split("_");
        return hashOps.get(str[0], code);
    }

    /** {@inheritDoc} */
    @Override
    public Collection<Edges> findAllEdges() {
        return hashOps.entries(KEY).values();
    }

    /** {@inheritDoc} */
    @Override
    public void deleteEdge(String code) {
        String[] str = code.split("_");
        hashOps.delete(str[0], code);
    }

    /** {@inheritDoc} */
    @Override
    public Collection<Edges> findAllEdgesFromU(String from) {
        return hashOps.entries(from).values();
    }

}
