package widgets;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.TextView;

/**
 * Class enables an edittext to act like datepicker
 * 
 * @author nayan
 *
 */
public class TextDatePicker implements OnClickListener, OnDateSetListener {

	private TextView dateTextView;
	private Context _context;
	// tells if dialog is already opened
	private boolean isDialogShowing;

	public TextDatePicker(Context context, TextView editTextViewID) {
		this.dateTextView = editTextViewID;
		this.dateTextView.setOnClickListener(this);
		this._context = context;
	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		updateDisplay(year, monthOfYear, dayOfMonth);
	}

	@Override
	public void onClick(View v) {
		prepareDialog();
	}

	/**
	 * open a datepicker dailog on click of edittext, default date selected is
	 * todays date on the date picker
	 */
	private void prepareDialog() {
		// open a new dialog only if no other dialog is opened
		if (!isDialogShowing) {
			isDialogShowing = true;
			Calendar calendar = Calendar.getInstance();
			DatePickerDialog dialog = new DatePickerDialog(_context, this,
					calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
					calendar.get(Calendar.DAY_OF_MONTH));
			dialog.show();
			dialog.setOnDismissListener(new OnDismissListener() {

				@Override
				public void onDismiss(DialogInterface dialog) {
					isDialogShowing = false;
				}
			});
		}
	}

	/**
	 * set the date selected by user into edittext
	 */
	private void updateDisplay(int year, int monthOfYear, int dayOfMonth) {
		dateTextView.setText(new StringBuilder()
				// Month is 0 based so add 1
				.append(dayOfMonth).append("-").append(monthOfYear + 1)
				.append("-").append(year));
	}
}