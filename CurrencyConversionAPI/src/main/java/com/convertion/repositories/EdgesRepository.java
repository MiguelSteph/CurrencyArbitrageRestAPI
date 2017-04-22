package com.convertion.repositories;

import java.util.Collection;

import com.convertion.dto.Edges;

public interface EdgesRepository {

    /**
     * Save the given edge.
     * 
     * @param edge
     */
    void saveEdges(Edges edge);

    /**
     * Find and return the edge that match with the provided code.
     * 
     * @param code
     * @return the edge that match with the provided code.
     */
    Edges findEdge(String code);

    /**
     * Return all the edge
     * 
     * @return A list of edges
     */
    Collection<Edges> findAllEdges();
    
    Collection<Edges> findAllEdgesFromU(String from);

    /**
     * Delete the edge that match with the provided code.
     * 
     * @param code
     */
    void deleteEdge(String code);

}
