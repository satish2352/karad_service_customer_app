package com.example.karaduser.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.karaduser.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TestFragemt extends Fragment {

    public TestFragemt() {
        // Required empty public constructor
    }

    public static TestFragemt newInstance() {
        TestFragemt fragment = new TestFragemt();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test_fragemt, container, false);
    }
}
