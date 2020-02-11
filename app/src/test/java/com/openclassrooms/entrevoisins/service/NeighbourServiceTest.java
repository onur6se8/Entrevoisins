package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.ui.neighbour_list.FavoriFragment;
import com.openclassrooms.entrevoisins.ui.neighbour_list.InfoNeighbourActivity;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.internal.matchers.Null;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Unit test on Neighbour service
 */
@RunWith(JUnit4.class)
public class NeighbourServiceTest {

    private NeighbourApiService service;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    @Test
    public void getNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));       //  permet d’exprimer précisément une attente quant à la valeur d’un objet
    }

    @Test
    public void deleteNeighbourWithSuccess() {
        Neighbour neighbourToDelete = service.getNeighbours().get(0);                 // prend la valeurs de neighbour 0
        service.deleteNeighbour(neighbourToDelete);                            // supprime neighbour 0
        assertFalse(service.getNeighbours().contains(neighbourToDelete));  // regarde si il ya une valeur dans neighbour 0 et il doit recevoir false comme reponse
    }




    @Test
    public void initListFavori() {
        List<Neighbour> neighbourFavori = service.getNeighboursFavori();
        assertEquals(0,neighbourFavori.size());
    }

    @Test
    public void addFavori () {
        service.setFavori(1);  // mets en true

        assertTrue(service.getNeighbours().get(0).getFavori());  // controle si il est en true
        List<Neighbour> neighbourAddFavori = service.getNeighboursFavori();   // reprend la list des voisin en true

        assertEquals(service.getNeighbours().get(0),neighbourAddFavori.get(0));  // compare le voisin de la list neighbour avec le voisin de la list des favori si c'est bien la memee personne
        assertEquals(1, neighbourAddFavori.size());  // controle qu'il ya effectivement une personne sur la list des favori

    }

    @Test
    public void removeFavori() {
        service.setFavori(1);
        service.setFavori(3);

        service.setFavori(1);
        assertFalse(service.getNeighbours().get(0).getFavori());  // controle si il est en false maintenant
        List<Neighbour> neighbourDeleteFavori = service.getNeighboursFavori(); // reprend la list des voisins en true

        assertEquals(1, neighbourDeleteFavori.size()); // controle si il ya une personne dans la list
        assertEquals(service.getNeighbours().get(2),neighbourDeleteFavori.get(0));

    }

    @Test
    public void deleteFavori() {
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        service.setFavori(1);
        service.setFavori(3);

        service.deleteNeighbour(neighbourToDelete);

        List<Neighbour> neighbourDeleteFavori = service.getNeighboursFavori(); // reprend la list des voisins en true

        assertEquals(1, neighbourDeleteFavori.size()); // controle si il ya une personne dans la list
        assertEquals(service.getNeighbours().get(1),neighbourDeleteFavori.get(0));
     // le nbr de neighbour
    }
}

