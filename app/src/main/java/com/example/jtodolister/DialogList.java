package com.example.jtodolister;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;


public class DialogList extends DialogFragment {

    //TODO: add more items button


    public static DialogList newInstance() {
        DialogList dl = new DialogList();
        return dl;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        final View thisView = getView();
        final EditText dalTitle = view.findViewById(R.id.dal_editText_title);
        final EditText dalContent = view.findViewById(R.id.dal_editText_content);

        //----YES BUTTON----
        view.findViewById(R.id.dal_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: what happens if the field is empty + actually make the damn thing work
                //MakeShortNoteAddedToast();
                dismiss();
                //addLongFragment(dalTitle.getText().toString().trim(),dalContent.getText().toString().trim());
            }
        });
        //----NO BUTTON----
        view.findViewById(R.id.dal_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        //----ADD MORE ITEMS BUTTON----
        view.findViewById(R.id.dal_add_items_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: this shit doesn't work
                FragmentTransaction ft_dialog_add_list_more_items = getChildFragmentManager().beginTransaction();
                LongMoreItemsFragment lmif = LongMoreItemsFragment.newInstance();
                ft_dialog_add_list_more_items.add(R.id.dal,lmif);
//              ft_dialog_add_list_more_items.add(R.id.dal_layout_more_fragments,lmif);
                ft_dialog_add_list_more_items.addToBackStack(null);
                lmif.setMenuVisibility(true);
                ft_dialog_add_list_more_items.commit();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_add_long, container, false);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }


}
