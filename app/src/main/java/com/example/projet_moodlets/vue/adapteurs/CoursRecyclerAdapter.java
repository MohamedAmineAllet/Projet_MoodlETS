package com.example.projet_moodlets.vue.adapteurs;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.projet_moodlets.R;
import com.example.projet_moodlets.modele.entites.Cours;
import java.util.List;

public class CoursRecyclerAdapter extends RecyclerView.Adapter<CoursRecyclerAdapter.ViewHolder> {
    private List<Cours> items;
    private Context context;

    public CoursRecyclerAdapter(Context context, List<Cours> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.cours_card_dashboard, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cours c = items.get(position);
        holder.code.setText(c.getCode());
        holder.nom.setText(c.getTitle());
        int resId = context.getResources().getIdentifier(c.getImageCours(), "drawable", context.getPackageName());
        holder.img.setImageResource(resId != 0 ? resId : R.drawable.icone_web);
    }

    @Override
    public int getItemCount() { return items.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView code, nom;
        ImageView img;
        public ViewHolder(View v) {
            super(v);
            code = v.findViewById(R.id.ls_code_cours);
            nom = v.findViewById(R.id.ls_nom_cours);
            img = v.findViewById(R.id.icone_cours);
        }
    }
}