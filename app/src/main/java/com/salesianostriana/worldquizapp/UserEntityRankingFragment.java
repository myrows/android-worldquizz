package com.salesianostriana.worldquizapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.salesianostriana.worldquizapp.model.UserEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import es.dmoral.toasty.Toasty;


public class UserEntityRankingFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private IRankingListener mListener;
    Context context;
    RecyclerView recyclerView;
    List<UserEntity> listaDummyUsuarios;
    QuerySnapshot listaDummyReal;
    MyUserEntityRecyclerViewAdapter adapter;
    private boolean ordenAsc=false;
    FirebaseFirestore myDB = FirebaseFirestore.getInstance();
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public UserEntityRankingFragment() {
    }

    public static UserEntityRankingFragment newInstance(int columnCount) {
        UserEntityRankingFragment fragment = new UserEntityRankingFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_userentity_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
        }

        listaDummyUsuarios = new ArrayList<>();

        myDB.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    // Task completed successfully
                    listaDummyUsuarios = task.getResult().toObjects(UserEntity.class);
                    Collections.sort(listaDummyUsuarios, new comparadorPuntos());
                    adapter = new MyUserEntityRecyclerViewAdapter(listaDummyUsuarios,mListener,context);
                    recyclerView.setAdapter(adapter);
                } else {
                    // Task failed with an exception
                    Exception exception = task.getException();
                }
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IRankingListener) {
            mListener = (IRankingListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_opciones_ranking, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filtroRanking:
                if(ordenAsc) {

                    Toasty.info(context, "Ordenado por puntos", Toast.LENGTH_SHORT).show();
                    Collections.sort(listaDummyUsuarios, new comparadorPuntos());
                    adapter = new MyUserEntityRecyclerViewAdapter(listaDummyUsuarios,mListener,context);
                    recyclerView.setAdapter(adapter);

                } else {

                    item.setIcon(R.drawable.ic_filter_green);

                    Collections.sort(listaDummyUsuarios, new comparadorEfectividad());

                    Toasty.info(context, "Ordenado por efectividad", Toast.LENGTH_SHORT).show();
                    Collections.sort(listaDummyUsuarios, new comparadorEfectividad());
                    adapter = new MyUserEntityRecyclerViewAdapter(listaDummyUsuarios,mListener,context);
                    recyclerView.setAdapter(adapter);
                }
                ordenAsc = !ordenAsc;

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {

        super.onResume();

        myDB.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    // Task completed successfully
                    listaDummyUsuarios = task.getResult().toObjects(UserEntity.class);
                    Collections.sort(listaDummyUsuarios, new comparadorPuntos());
                    adapter = new MyUserEntityRecyclerViewAdapter(listaDummyUsuarios, mListener, context);
                    recyclerView.setAdapter(adapter);
                } else {
                    // Task failed with an exception
                    Exception exception = task.getException();
                }
            }
        });
    }
}

class comparadorPuntos implements Comparator<UserEntity> {
    public int compare(UserEntity a, UserEntity b) {
        return b.getTotalPoints()-a.getTotalPoints();
    }
}

class comparadorEfectividad implements Comparator<UserEntity> {
    public int compare(UserEntity a, UserEntity b) {
        return (int)(b.getAverageScore()-a.getAverageScore());
    }
}
