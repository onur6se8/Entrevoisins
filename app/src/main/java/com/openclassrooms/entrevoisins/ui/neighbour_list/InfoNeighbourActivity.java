package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.events.FavoriNeighbourEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.DummyNeighbourGenerator;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import static android.app.PendingIntent.getActivity;

public class InfoNeighbourActivity extends AppCompatActivity {                                           //  2)

    public static final String KEY_ID = "KEY_ID";
    public static final String KEY_NAME = "KEY_NAME";
    public static final String KEY_AVATAR = "KEY_AVATAR";


    private ImageView   mImageVoisin;        // mes variables
    private TextView    mNomVoisin;
    private ImageButton mBoutonRetour;
    private FloatingActionButton mBoutonFavori;
    private TextView    mNonVoisin2;


    private List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();                  //  Nous permet d'avoir acces a la list Neighbour


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_voisin);

        int      id   = getIntent().getIntExtra(KEY_ID, -1);                                // on prend les valeurs de Intent
        String  name  = getIntent().getStringExtra(KEY_NAME);
        String avatar = getIntent().getStringExtra(KEY_AVATAR);

        mImageVoisin  = (ImageView) findViewById(R.id.voisin_image);   // mes element graphique         3)  ( on peut aller voir dans le layout activity_info_voisin.xml )
        mNomVoisin    = (TextView) findViewById(R.id.voisin_nom);
        mBoutonRetour = (ImageButton) findViewById(R.id.button_retour);
        mBoutonFavori = (FloatingActionButton) findViewById(R.id.neighbour_favori);
        mNonVoisin2   = (TextView) findViewById(R.id.re_nom2);



        Glide.with(mImageVoisin)   // Picasso.with(getBaseContext()).load(avatar).into(mImageVoisin);           on affiche l'image de avatar
                .load(avatar)
                .into(mImageVoisin);


        mNonVoisin2.setText(name);     // on affiche le nom
        mNomVoisin.setText(name);


        mBoutonRetour.setOnClickListener(new View.OnClickListener() {   // bouton retour                    on mets fin a l'activite
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        starPicture(id);


        mBoutonFavori.setOnClickListener(new View.OnClickListener() {   // bouton favori
            @Override
            public void onClick(View view) {

                EventBus.getDefault().post(new FavoriNeighbourEvent(id));        // comme dans MyNeighbourRecyclerViewAdapter

                starPicture(id);
            }
        });


    }

    private void starPicture (int id){

        for (int i = 0; i < neighbours.size(); i++) {   // une boucle qui fait le tour de la list
            if (neighbours.get(i).getId() == id) {
                if (neighbours.get(i).getFavori()) {

                    mBoutonFavori.setImageDrawable(ContextCompat.getDrawable(this, android.R.drawable.btn_star_big_on));
                } else {

                    mBoutonFavori.setImageDrawable(ContextCompat.getDrawable(this, android.R.drawable.btn_star_big_off));
                }
            }
        }
    }
}
