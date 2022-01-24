package com.example.gitapi.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gitapi.Pojo.ItemsModel;
import com.example.gitapi.Pojo.RootModel;
import com.example.gitapi.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RepositoryListAdapter extends RecyclerView.Adapter<RepositoryListAdapter.RepositoryViewHolder> {
    RootModel rootModel =new RootModel();
    @NonNull
    @Override
    public RepositoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RepositoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RepositoryViewHolder holder, int position) {


        Picasso.get().load(rootModel.getItems().get(position).owner.getAvatar_url()).into(holder.avatar);
        holder.name.setText(rootModel.getItems().get(position).getName());
        holder.author.setText(rootModel.getItems().get(position).getFull_name());
        holder.description.setText(rootModel.getItems().get(position).getDescription());
        holder.url.setText(rootModel.getItems().get(position).owner.getUrl());
        holder.language.setText(rootModel.getItems().get(position).getLanguage());

        holder.stars.setText(String.valueOf(rootModel.getItems().get(position).getStargazers_count()));
        holder.forks.setText(String.valueOf(rootModel.getItems().get(position).getForks_count()));

        boolean isExpanded= rootModel.getItems().get(position).isExpanded();
        holder.extra_content.setVisibility(isExpanded?View.VISIBLE:View.GONE);
    }

    public void setList(ArrayList<ItemsModel> repositoryList){
        rootModel.setItems(repositoryList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return rootModel.getItems().size();
    }

    public class RepositoryViewHolder extends RecyclerView.ViewHolder {
      ImageView avatar;
      TextView name, author, description, url, language, stars, forks;
        CardView expanded_item;
        LinearLayout extra_content;
        public RepositoryViewHolder(@NonNull View itemView) {
            super(itemView);

            avatar=itemView.findViewById(R.id.avatar);

            name=itemView.findViewById(R.id.name);
            author=itemView.findViewById(R.id.author);
            description=itemView.findViewById(R.id.description);
            url=itemView.findViewById(R.id.url);
            language=itemView.findViewById(R.id.language);
            stars=itemView.findViewById(R.id.StarGazersCount);
            forks=itemView.findViewById(R.id.forksCount);
            expanded_item=itemView.findViewById(R.id.expanded_item);
            extra_content=itemView.findViewById(R.id.extra_content);
            expanded_item.setOnClickListener(v -> {
                ItemsModel itemsModel = rootModel.getItems().get(getAdapterPosition());
                itemsModel.setExpanded(!itemsModel.isExpanded());
                notifyItemChanged(getAdapterPosition());
            });
        }
    }
}
