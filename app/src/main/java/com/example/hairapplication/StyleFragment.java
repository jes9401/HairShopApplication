package com.example.hairapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StyleFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StyleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StyleFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public StyleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StyleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StyleFragment newInstance(String param1, String param2) {
        StyleFragment fragment = new StyleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }



    @Override
    public void onActivityCreated(Bundle b){
        super.onActivityCreated(b);


        final RadioGroup genderRadio = (RadioGroup)getView().findViewById(R.id.genderRadio);
        final RelativeLayout manLayout = (RelativeLayout)getView().findViewById(R.id.manLayout);
        final RelativeLayout wonmanLayout = (RelativeLayout)getView().findViewById(R.id.wonmanLayout);

        genderRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == R.id.Man){ // 라디오버튼 남자가 선택된 경우 남자 스타일 선택
                    manLayout.setVisibility(View.VISIBLE);
                    wonmanLayout.setVisibility(View.INVISIBLE);
                }else{ // 라디오버튼 여자가 선택된 경우 여자 스타일 선택
                    manLayout.setVisibility(View.INVISIBLE);
                    wonmanLayout.setVisibility(View.VISIBLE);
                }
            }
        });




        ImageButton w_imbtn1, w_imbtn2, w_imbtn3, w_imbtn4, w_imbtn5;
        w_imbtn1 = (ImageButton) getView().findViewById(R.id.cut_woman);
        w_imbtn2 = (ImageButton)getView().findViewById(R.id.pum_woman);
        w_imbtn3 = (ImageButton)getView().findViewById(R.id.color_woman);
        w_imbtn4 = (ImageButton)getView().findViewById(R.id.styiling_woman);
        w_imbtn5 = (ImageButton)getView().findViewById(R.id.clinic_woman);

        w_imbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),W_cutActivity.class);
                startActivity(intent);

            }
        });
        w_imbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),W_pumActivity.class);
                startActivity(intent);

            }
        });
        w_imbtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),W_colorActivity.class);
                startActivity(intent);

            }
        });
        w_imbtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),W_stylingActivity.class);
                startActivity(intent);

            }
        });
        w_imbtn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),W_clinicActivity.class);
                startActivity(intent);

            }
        });


        ImageButton m_imbtn1, m_imbtn2, m_imbtn3, m_imbtn4, m_imbtn5;
        m_imbtn1 = (ImageButton)getView().findViewById(R.id.cut_man);
        m_imbtn2 = (ImageButton)getView().findViewById(R.id.pum_man);
        m_imbtn3 = (ImageButton)getView().findViewById(R.id.color_man);
        m_imbtn4 = (ImageButton)getView().findViewById(R.id.styiling_man);
        m_imbtn5 = (ImageButton)getView().findViewById(R.id.clinic_man);

        m_imbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),M_cutActivity.class);
                startActivity(intent);

            }
        });
        m_imbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),M_pumActivity.class);
                startActivity(intent);

            }
        });
        m_imbtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),M_colorActivity.class);
                startActivity(intent);

            }
        });
        m_imbtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),M_stylingActivity.class);
                startActivity(intent);

            }
        });
        m_imbtn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),M_clinicActivity.class);
                startActivity(intent);

            }
        });


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_style, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
