package com.example.jtodolister;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

public class LongMoreItemsFragment extends Fragment {

    public LongMoreItemsFragment() {
        //i dunno, maybe when it's empty it's ok
        //edit: it doesn't
    }

    public static LongMoreItemsFragment newInstance() {

        return new LongMoreItemsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_list_more_items_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageButton addBtn = view.findViewById(R.id.frag_dal_add_items_button_more);
        EditText newItmFragDialog = view.findViewById(R.id.frag_dal_editText_item);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.MakeShortToast(getContext(),"heyaaaa");
            }
        });
    }
}
