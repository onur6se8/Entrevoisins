package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.events.DeleteNeighbourEvent;
import com.openclassrooms.entrevoisins.events.FavoriNeighbourEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

public class FavoriFragment extends Fragment {                  // 6

    private NeighbourApiService mApiService;
    private List<Neighbour> mNeighbours;
    private RecyclerView mRecyclerView;


    public static FavoriFragment newInstance() {                     // le constructeur renvoie une nouvelle instance
        FavoriFragment fragment = new FavoriFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mApiService = DI.getNeighbourApiService();          // reçoit une instance
        EventBus.getDefault().register(this); // methode pour realiser une action
    }

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,                                                          //  gere les fragment
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_neighbour_list, container, false);
        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view;                                                                             // permet d'afficher une liste déroulante d'élements   7) v7
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        initList();
        return view;     // et on return la vue
    }

    private void initList() {
        mNeighbours = mApiService.getNeighboursFavori();  // 8)  on initialise la list des favori

        mRecyclerView.setAdapter(new MyNeighbourRecyclerViewAdapter(mNeighbours));                    // on affiche la liste de favori dans MyNei...
    }


    @Subscribe
    public void onFavori( FavoriNeighbourEvent event) {     // si une action se passe dans FavoriNi... un evenement se lance

        mApiService.setFavori(event.idNeighbour);        // 12) on a acces a setFavori et on envoie ( ) qui vient de la partie 4)
        initList();
    }

    @Subscribe
    public void onDeleteNeighbour(DeleteNeighbourEvent event) {  // event
        mApiService.deleteNeighbour(event.neighbour);   // permet d'utiliser event.neighbour
        initList();
    }
}
