package programming.mjzamoras.plantdexandroidapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import programming.mjzamoras.plantdexandroidapp.Object.APIPlantIDResponse;
import programming.mjzamoras.plantdexandroidapp.R;

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ResultsViewHolder> {
    public List<APIPlantIDResponse> results;
    private ResultsAction resultsAction;

    @NonNull
    @Override
    public ResultsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context ctx = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View v = inflater.inflate(R.layout.layout_results, parent, false);

        return new ResultsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultsViewHolder holder, int position) {
        APIPlantIDResponse plant = results.get(position);
        String detailsShown = "Plant ID : " + plant.getPlantID() + "\nTest Detail 1 : Test\nTest Detail 2 : Test2 ";
        holder.txtPlantID.setText(detailsShown);
    }

    @Override
    public int getItemCount() {
        if(results == null){
            return  0;
        }else{
            return results.size();
        }
    }

    public interface ResultsAction{
        void select(APIPlantIDResponse plant);
    }

    public ResultsAdapter(ResultsAction resultsAction) {
        this.resultsAction = resultsAction;
    }

    public void setResults(List<APIPlantIDResponse> results) {
        this.results = results;
        notifyDataSetChanged();
    }

    class ResultsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final ImageView img;
        public final TextView txtPlantID;

        public ResultsViewHolder(@NonNull View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
            txtPlantID = (TextView) itemView.findViewById(R.id.txtPlantID);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            APIPlantIDResponse plant = results.get(getAdapterPosition());
            resultsAction.select(plant);
        }
    }
}
