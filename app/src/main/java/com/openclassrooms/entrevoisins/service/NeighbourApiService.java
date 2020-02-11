package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.List;


/**
 * Neighbour API client
 */
public interface NeighbourApiService {

    /**
     * Get all my Neighbours        les voisins
     * @return {@link List}
     */
    List<Neighbour> getNeighbours();   // du cooup renvoie neighbour

    /**
     * Deletes a neighbour
     * @param neighbour
     */
    void deleteNeighbour(Neighbour neighbour); // supprime neighbour



    void setFavori(int neighboursId);          // 12)


    List<Neighbour>  booleanFavori (List<Neighbour> neighbours);  // 10)


    List<Neighbour> getNeighboursFavori();   // 8)





}
