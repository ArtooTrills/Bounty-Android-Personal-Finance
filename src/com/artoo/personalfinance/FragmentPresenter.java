package com.artoo.personalfinance;

public interface FragmentPresenter {
	/**
	 * each entry in navigation drawer corresponds to a fragment. This method
	 * shows a particular fragment
	 * 
	 * @param position
	 *            is the position of item present in the list of navigation
	 *            drawer, beginning with 0
	 */
	void showFragment(int position);
}
