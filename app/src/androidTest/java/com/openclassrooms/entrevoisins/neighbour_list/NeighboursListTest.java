
package com.openclassrooms.entrevoisins.neighbour_list;

import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertEquals;


/**
 * Test class for list of neighbours                  Classe de test pour la liste des voisins
 */
@RunWith(AndroidJUnit4.class)
public class NeighboursListTest {

    // This is fixed           Ceci est corrigé
    private static int ITEMS_COUNT = 12;

    private ListNeighbourActivity mActivity;


    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityRule =
            new ActivityTestRule(ListNeighbourActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
    }

    /**
     * We ensure that our recyclerview is displaying at least on item           Nous nous assurons que notre vue d'ensemble du recyclage s'affiche au moins sur l'article
     */
    @Test
    public void myNeighboursList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.    Faites d'abord défiler jusqu'à la position qui doit être mise en correspondance et cliquez dessus.
        onView(ViewMatchers.withId(R.id.list_neighbours))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * When we delete an item, the item is no more shown           Lorsque nous supprimons un élément, celui-ci n'est plus affiché
     */
    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem() {
        // Given : We remove the element at position 2             Étant donné: nous supprimons l'élément en position 2
        onView(ViewMatchers.withId(R.id.list_neighbours)).check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon                Lorsque vous cliquez sur une icône de suppression
        onView(ViewMatchers.withId(R.id.list_neighbours))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        // Then : the number of element is 11             Alors: le nombre d'élément est 11

        onView(ViewMatchers.withId(R.id.list_neighbours)).check(withItemCount(ITEMS_COUNT-1));
    }

    @Test
    public void infoActivity() {  // on clique sur le voisin et on controle si le bon est affiche grace a son prenom

        onView(withId(R.id.list_neighbours)).perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));

        onView(withId(R.id.voisin_nom)).check(matches(withText("Caroline")));

    }

    @Test
    public void deleteAndRemoveButton() { // on mets un voisin en favori et on utilise le bouton supprimer pour verifier si il est encore present
        onView(withId(R.id.list_neighbours)).perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));
        onView(withId(R.id.neighbour_favori)).perform(ViewActions.click());
        onView(withId(R.id.button_retour)).perform(ViewActions.click());

        onView(withId(R.id.list_neighbours)).perform(RecyclerViewActions.actionOnItemAtPosition(3, ViewActions.click()));
        onView(withId(R.id.neighbour_favori)).perform(ViewActions.click());
        pressBack();

        onView(withId(R.id.list_neighbours)).perform(ViewActions.swipeLeft() );

        onView(withId(R.id.list_favori_neighbours)).perform(RecyclerViewActions.actionOnItemAtPosition(0, new DeleteViewAction()));

        onView(ViewMatchers.withId(R.id.list_favori_neighbours)).check(withItemCount(1));
        onView(ViewMatchers.withId(R.id.list_neighbours)).check(withItemCount(ITEMS_COUNT-1));

        onView(withId(R.id.list_favori_neighbours)).perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));
        onView(withId(R.id.neighbour_favori)).perform(ViewActions.click());
        pressBack();

        onView(ViewMatchers.withId(R.id.list_favori_neighbours)).check(withItemCount(0));
        onView(ViewMatchers.withId(R.id.list_neighbours)).check(withItemCount(ITEMS_COUNT-1));
    }

    @Test  // on ajoute 2 voisin en favori et on controle si il y'a effectivement 2 personne
    public void neighbourFavori() {
        onView(withId(R.id.list_neighbours)).perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));
        onView(withId(R.id.neighbour_favori)).perform(ViewActions.click());
        pressBack();
        onView(withId(R.id.list_neighbours)).perform(RecyclerViewActions.actionOnItemAtPosition(3, ViewActions.click()));
        onView(withId(R.id.neighbour_favori)).perform(ViewActions.click());
        pressBack();
        onView(withId(R.id.list_neighbours)).perform(ViewActions.swipeLeft() );

        onView(withId(R.id.list_favori_neighbours)).check(withItemCount(2));
        //
    }  // ensuite les tests unitaires control si les bonnes personnes sont en favori, les tests sont complementaire


    @Test
    public void starPicture() {
        onView(withId(R.id.list_neighbours)).perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));
        onView(withId(R.id.neighbour_favori)).check(matches(new DrawableMatcher(android.R.drawable.btn_star_big_off)));
        onView(withId(R.id.neighbour_favori)).perform(ViewActions.click());
        onView(withId(R.id.neighbour_favori)).check(matches(new DrawableMatcher(android.R.drawable.btn_star_big_on)));
    }
}