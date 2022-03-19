package com.omolayoseun.copyboard;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ViewListFragment extends Fragment {
    ListView listView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.list_frame, container, false);
        listView = view.findViewById(R.id.listView);

        Caller caller = (Caller) getActivity();
        listView.setAdapter(new ListViewAdapterClass(getActivity().getApplicationContext(), caller));

        return view;
    }

}
