package programming.mjzamoras.plantdexandroidapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import programming.mjzamoras.plantdexandroidapp.Adapters.ResultsAdapter;
import programming.mjzamoras.plantdexandroidapp.Object.APIPlantIDResponse;

public class ClassifyResultActivity extends AppCompatActivity implements ResultsAdapter.ResultsAction {

    private RecyclerView rvResults;
    private List<APIPlantIDResponse> response;
    private ResultsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classify_result);

        rvResults = (RecyclerView) findViewById(R.id.rvResults);
        response = new ArrayList<>();

        APIPlantIDResponse plant = new APIPlantIDResponse("plantDEx-1091");
        APIPlantIDResponse plant2 = new APIPlantIDResponse("plantDEx-2191");
        APIPlantIDResponse plant3 = new APIPlantIDResponse("plantDEx-5111");
        response.add(plant);
        response.add(plant2);
        response.add(plant3);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvResults.setLayoutManager(linearLayoutManager);
        adapter = new ResultsAdapter(this);
        rvResults.setAdapter(adapter);
        adapter.setResults(response);
    }

    @Override
    public void select(APIPlantIDResponse plant) {
        Intent resultIntent = new Intent(ClassifyResultActivity.this, ClassifyResultDetailsActivity.class);
        startActivity(resultIntent);
    }

    public void setResponse(List<APIPlantIDResponse> response) {
        this.response = response;
    }
}
