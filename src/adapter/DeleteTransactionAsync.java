package adapter;

import model.Transaction;
import persistantData.DatabaseHelper;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * class deletes transaction and its history from database
 * 
 * @author nayan
 *
 */
public class DeleteTransactionAsync extends AsyncTask<Void, Void, Void> {
	private ProgressDialog pDialog;
	private boolean result = false;
	private Context context;
	private Transaction transaction;

	public DeleteTransactionAsync(Context context, Transaction transaction) {
		super();
		this.context = context;
		this.transaction = transaction;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		pDialog = new ProgressDialog(context);
		pDialog.setMessage("Removing Transaction");
		pDialog.setCancelable(false);
	}

	@Override
	protected Void doInBackground(Void... params) {
		result = (new DatabaseHelper(context)).deleteTransaction(transaction
				.getId());

		return null;
	}

	@Override
	protected void onPostExecute(Void res) {
		super.onPostExecute(res);
		pDialog.dismiss();
		
		if (result)
			((Activity) context).onBackPressed();
		else {
			Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT)
					.show();
		}
	}
}