package com.openclassrooms.entrevoisins.service;

import android.app.Dialog;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.app.PendingIntent.getActivity;

/**
 * Dummy mock for the Api
 */
public class DummyNeighbourApiService implements  NeighbourApiService {

    private List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();       // renvoie neighbour

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> getNeighbours() {   // du cooup renvoie neighbour
        return neighbours;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteNeighbour(Neighbour neighbour) {  //  supprime neighbour
        neighbours.remove(neighbour);
    }






    public void setFavori(int id) {                              // 13)

        for (int i = 0; i < neighbours.size(); i++) {         // on fait le tour de la list
            if (neighbours.get(i).getId() == id) {                                                        // si dans la list id = id recu en parametre
                if(!neighbours.get(i).getFavori())     // si il n'est pas en favori = False
                {
                    neighbours.get(i).setFavori(true);   // on le mets en favori = true

                }
                else if (neighbours.get(i).getFavori()) {  // favori = True
                    neighbours.get(i).setFavori(false);                                                 //  false
                }
            }
        }
    }




    public List<Neighbour> booleanFavori(List<Neighbour> neighbours) {                                        // 11)
        List<Neighbour> neighbourFavori = new ArrayList<>();   // on cree une nouvelle list
        for(int i = 0; i < neighbours.size(); i++)  // on fait le tour de la list de neighbour
        {
            if(neighbours.get(i).getFavori()) // si il est = true alors
            {
                neighbourFavori.add(neighbours.get(i));  // on le rajoute dans la nouvelle list
            }
        }
        return neighbourFavori;  // on return la nouvelle list
    }

    @Override
    public List<Neighbour> getNeighboursFavori() {
        return booleanFavori(neighbours);
    }                                                   // 9)  renvoi la list neighbour
}
