package com.example.jtodolister;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ArrayList<String> items = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            items.add("This is #" + i + " item of the list.");
        }


        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        recyclerView.setAdapter(new ListAdapter(this, items));

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = items.size();
                items.add("New item #" + temp + "! Hurray!");
                if (items.size() > 2) {
                    recyclerView.getAdapter().notifyItemInserted(items.size()-1);
                } else {
                    recyclerView.getAdapter().notifyDataSetChanged();
                }

            }
        });

    }

    private static class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

        private final LayoutInflater myInflater;
        private final ArrayList myData;

        public ListAdapter(final Context context,final ArrayList data) {
            myInflater = LayoutInflater.from(context);
            myData = data;
        }


        @NonNull
        @Override
        public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            final View view = myInflater.inflate(android.R.layout.simple_list_item_2, viewGroup,false);
            return new ViewHolder(view);

        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.textView.setText(myData.get(position).toString());
        }

        @Override
        public int getItemCount() {
            return myData.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView textView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                textView = (TextView) itemView.findViewById(android.R.id.text1);
            }
        }
    }
}
