package programming.mjzamoras.plantdexandroidapp;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import programming.mjzamoras.plantdexandroidapp.Adapters.ResultsAdapter;
import programming.mjzamoras.plantdexandroidapp.Object.APIPlantIDResponse;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements ResultsAdapter.ResultsAction {


    private TextView txtSearch;
    private RecyclerView rvResults;
    private List<APIPlantIDResponse> response;
    private ResultsAdapter adapter;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search, container, false);

        response = new ArrayList<>();

        APIPlantIDResponse plant = new APIPlantIDResponse("plantDEx-1091");
        APIPlantIDResponse plant2 = new APIPlantIDResponse("plantDEx-2191");
        APIPlantIDResponse plant3 = new APIPlantIDResponse("plantDEx-5111");
        response.add(plant);
        response.add(plant2);
        response.add(plant3);

        txtSearch = (TextView) v.findViewById(R.id.txtSearch);
        rvResults = (RecyclerView) v.findViewById(R.id.rvResults);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvResults.setLayoutManager(linearLayoutManager);
        adapter = new ResultsAdapter(this);
        adapter.setResults(response);

        return v;
    }

    @Override
    public void select(APIPlantIDResponse plant) {
        Toast.makeText(getContext(), plant.getPlantID(), Toast.LENGTH_LONG).show();
    }
}
