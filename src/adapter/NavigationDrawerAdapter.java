package adapter;

import com.artoo.personalfinance.FragmentPresenter;
import com.artoo.personalfinance.R;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class NavigationDrawerAdapter extends
		RecyclerView.Adapter<NavigationDrawerAdapter.NavViewHolder> {
	private LayoutInflater inflater;
	private String[] navItems;
	private int[] navItemDrawables;
	Context context;
	private FragmentPresenter itemPresenter;

	public NavigationDrawerAdapter(Context context, String[] navItems,
			FragmentPresenter navItemPresenter, int[] navItemDrawables) {
		super();
		inflater = LayoutInflater.from(context);
		this.context = context;
		this.navItems = navItems;
		this.itemPresenter = navItemPresenter;
		this.navItemDrawables=navItemDrawables;
	}

	@Override
	public int getItemCount() {
		return navItems.length;
	}

	class NavViewHolder extends RecyclerView.ViewHolder implements
			View.OnClickListener {
		private TextView rowTitle;
		private ImageView rowImage;

		public NavViewHolder(View itemView) {
			super(itemView);
			rowTitle = (TextView) itemView.findViewById(R.id.nav_row_text_view);
			rowImage = (ImageView) itemView.findViewById(R.id.nav_row_img);
			itemView.setOnClickListener(this);
		}

		@Override
		public void onClick(View v) {
			itemPresenter.showFragment(getPosition());
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onBindViewHolder(NavViewHolder holder, int position) {
		holder.rowTitle.setText(navItems[position]);
		holder.rowImage.setBackground(context.getResources().getDrawable(navItemDrawables[position]));
	}

	@Override
	public NavViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
		View view = inflater.inflate(R.layout.nav_row, arg0, false);
		NavViewHolder navViewHolder = new NavViewHolder(view);
		return navViewHolder;
	}
}