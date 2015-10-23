package com.artoo.personalfinance;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import persistantData.DatabaseHelper;
import utills.CommonUtility;
import model.IgnoreItem;
import adapter.IgnoreListAdapter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class IgnoreListCreateFragment extends Fragment implements
		OnClickListener {
	EditText editTextIgnoreSenderName;
	Button buttonSaveIgnoreItem;
	List<IgnoreItem> ignoreItemsList;
	RecyclerView recyclerViewIgnoreList;
	private LinearLayoutManager layoutManager;
	private DatabaseHelper dbHelper;
	private IgnoreListAdapter ignoreAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.ignore_list_fragment, container,
				false);
		dbHelper = new DatabaseHelper(getActivity());
		new IgnoreListLoaderAsync().execute();
		editTextIgnoreSenderName = (EditText) view
				.findViewById(R.id.edt_ignore_list_name);
		buttonSaveIgnoreItem = (Button) view
				.findViewById(R.id.ignore_list_save_button);
		buttonSaveIgnoreItem.setOnClickListener(this);
		recyclerViewIgnoreList = (RecyclerView) view
				.findViewById(R.id.ignore_items_rv);
		layoutManager = new LinearLayoutManager(getActivity(),
				LinearLayoutManager.VERTICAL, false);
		recyclerViewIgnoreList.setLayoutManager(layoutManager);
		return view;
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == buttonSaveIgnoreItem.getId()
				&& editTextIgnoreSenderName.getText().toString().trim()
						.length() > 0) {
			boolean isNew = true;
			for (IgnoreItem item : ignoreItemsList) {
				if (item.getSource().equals(
						editTextIgnoreSenderName.getText().toString().trim())) {
					isNew = false;
				}
			}
			if (isNew) {
				IgnoreItem ignoreItem = new IgnoreItem();
				try {
					ignoreItem
							.setDate(CommonUtility.DATE_FORMATTER_WITHOUT_TIME
									.parse(CommonUtility.DATE_FORMATTER_WITHOUT_TIME
											.format(new Date())));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ignoreItem.setSource(editTextIgnoreSenderName.getText()
						.toString().trim());
				new IgnoreItemSaver(ignoreItem).execute();
			}
		}
	}

	private class IgnoreItemSaver extends AsyncTask<Void, Void, Void> {
		IgnoreItem item;
		boolean isException;

		IgnoreItemSaver(IgnoreItem item) {
			this.item = item;
		}

		@Override
		protected Void doInBackground(Void... params) {
			try {
				dbHelper.addIgonreItem(item);
			} catch (Exception e) {
				isException = true;
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (!isException) {
				ignoreItemsList.add(item);
				ignoreAdapter.notifyDataSetChanged();
			} else {
				Toast.makeText(getActivity(), "Preference could not be saved",
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	private class IgnoreListLoaderAsync extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			ignoreItemsList = dbHelper.getIgnoreItemList();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			ignoreAdapter = new IgnoreListAdapter(getActivity(),
					ignoreItemsList);
			recyclerViewIgnoreList.setAdapter(ignoreAdapter);
		}
	}
}
