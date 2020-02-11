package com.openclassrooms.entrevoisins.events;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.DummyNeighbourGenerator;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import java.util.ArrayList;
import java.util.List;

public class FavoriNeighbourEvent {

    public int idNeighbour;

    public FavoriNeighbourEvent(int id) {

        this.idNeighbour = id;                   // 4)  on recoit juste id

    }
}
