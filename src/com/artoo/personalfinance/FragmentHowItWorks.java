package com.artoo.personalfinance;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class FragmentHowItWorks extends Fragment {
	Button okButton;
	HomeFragmentPresenter homeFragmentPresenter;
	
	public FragmentHowItWorks(HomeFragmentPresenter homeFragmentPresenter) {
		super();
		this.homeFragmentPresenter = homeFragmentPresenter;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.how_it_works, container, false);
		okButton = (Button) view.findViewById(R.id.btn_ok_how_it_works);
		okButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				homeFragmentPresenter.showHomeFragment();
			}
		});
		return view;
	}
}
