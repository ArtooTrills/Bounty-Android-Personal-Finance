package adapter;

import java.util.List;

import persistantData.DatabaseHelper;
import utills.CommonUtility;

import com.artoo.personalfinance.R;

import model.IgnoreItem;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class IgnoreListAdapter extends
		RecyclerView.Adapter<IgnoreListAdapter.ViewHolder> {
	Context context;
	private List<IgnoreItem> ignoreItems;
	private LayoutInflater inflater;
	private DatabaseHelper dbHelper;

	public IgnoreListAdapter(Context context, List<IgnoreItem> ignoreItems) {
		super();
		this.context = context;
		dbHelper = new DatabaseHelper(context);
		this.ignoreItems = ignoreItems;
		inflater = LayoutInflater.from(context);
	}

	class ViewHolder extends RecyclerView.ViewHolder {
		TextView textViewItemName, textViewDate;
		Button imgBtnDeleteItem;

		public ViewHolder(View itemView) {
			super(itemView);
			textViewItemName = (TextView) itemView
					.findViewById(R.id.ignore_list_name);
			textViewDate = (TextView) itemView
					.findViewById(R.id.ignore_list_date);
			imgBtnDeleteItem = (Button) itemView
					.findViewById(R.id.delete_ignore_button);
		}

	}

	@Override
	public int getItemCount() {
		return ignoreItems.size();
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, final int position) {
		holder.textViewItemName.setText(ignoreItems.get(position).getSource());
		holder.textViewDate.setText(CommonUtility.DATE_FORMATTER_WITHOUT_TIME
				.format(ignoreItems.get(position).getDate()));
		holder.imgBtnDeleteItem.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean res = dbHelper.deleteIgnoreItem(ignoreItems.get(
						position).getSource());
				if (res) {
					
					ignoreItems.remove(position);
					notifyDataSetChanged();
				}
			}
		});
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
		View view = inflater.inflate(R.layout.ignore_row_layout, arg0, false);
		return new ViewHolder(view);
	}

}
