package adapter;

import java.util.List;

import com.artoo.personalfinance.R;

import model.IgnoreItem;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class IgnoreListAdapter extends
		RecyclerView.Adapter<IgnoreListAdapter.ViewHolder> {
	Context context;
	private List<IgnoreItem> ignoreItems;
	private LayoutInflater inflater;

	public IgnoreListAdapter(Context context, List<IgnoreItem> ignoreItems) {
		super();
		this.context = context;
		this.ignoreItems = ignoreItems;
		inflater = LayoutInflater.from(context);
	}

	class ViewHolder extends RecyclerView.ViewHolder {
		TextView textView;

		public ViewHolder(View itemView) {
			super(itemView);
			textView = (TextView) itemView.findViewById(R.id.text_view_utility);
		}
	}

	@Override
	public int getItemCount() {
		return ignoreItems.size();
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		holder.textView.setText(ignoreItems.get(position).getSource());
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
		View view = inflater.inflate(R.layout.simple_text_view, arg0, false);
		return new ViewHolder(view);
	}
}
