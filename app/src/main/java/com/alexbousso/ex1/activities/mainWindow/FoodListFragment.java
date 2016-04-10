package com.alexbousso.ex1.activities.mainWindow;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alexbousso.ex1.FoodItemContent;
import com.alexbousso.ex1.ImageTextArrayAdapter;
import com.alexbousso.ex1.R;

public class FoodListFragment extends Fragment {
    private ListView listView;
    private FoodItemContent[] items;
    private View view;
    private OnFragmentInteractionListener mListener;

    public FoodListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_food_list, container, false);
        initializeComponents();

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void initializeComponents() {
        listView = (ListView) view.findViewById(R.id.listview);
        initializeListView();
    }

    private void initializeListView() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListener.onFoodItemSelected(items[position]);
            }
        });

        String[] values = getResources().getStringArray(R.array.foodArray);
        int[] imgs = {
                R.mipmap.bananas,
                R.mipmap.apple,
                R.mipmap.cherry,
                R.mipmap.orange,
                R.mipmap.steak,
        };
        items = new FoodItemContent[values.length];
        for (int i = 0; i < values.length; i++) {
            items[i] = new FoodItemContent(values[i], imgs[i]);
        }
        ImageTextArrayAdapter adapter = new ImageTextArrayAdapter(getActivity(), items);
        listView.setAdapter(adapter);
    }

    public interface OnFragmentInteractionListener {
        void onFoodItemSelected(FoodItemContent item);
    }
}
