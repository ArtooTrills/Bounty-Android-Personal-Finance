package com.example.earthshaker.moneybox.budget;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.earthshaker.moneybox.R;
import com.example.earthshaker.moneybox.budget.dao.BudgetModificationDao;
import com.example.earthshaker.moneybox.categories.CategoryEvent;
import com.example.earthshaker.moneybox.common.CategoryUtils;
import com.example.earthshaker.moneybox.common.SoftKeyboardUtils;
import com.squareup.picasso.Picasso;


import de.greenrobot.event.EventBus;

/**
 * Created by earthshaker on 15/5/17.
 */

public class EnterAmountDialog extends DialogFragment {
    private BudgetConfig mBudgetConfig;
    private boolean isEdit;
    private EditText amount;
    private int DIALOG_HEIGHT;
    private LinearLayout saveAmount, deleteAmount;
    private View view;
    private ImageView categoryIcon;
    private TextView categoryName;
    private Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBudgetConfig = getArguments().getParcelable("budget_category");
        isEdit = getArguments().getBoolean("is_edit");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        initializeViewVariables();
        attachListeners();
        setData();
        if (mBudgetConfig.getCategory().equalsIgnoreCase("All")) {
            categoryIcon.setVisibility(View.GONE);
            DIALOG_HEIGHT = 4;
        } else {
            DIALOG_HEIGHT = 5;
            categoryIcon.setVisibility(View.VISIBLE);
            Picasso.with(context)
                    .load(CategoryUtils.getCategoryIcon(mBudgetConfig.getCategory()))
                    .placeholder(R.drawable.background_fill)
                    .into(categoryIcon);
        }

        Dialog builder = new Dialog(getActivity());
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.setContentView(view);
        return builder;
    }

    @Override
    public void onResume() {
        super.onResume();
        setDialogDimensions();
        amount.postDelayed(() -> {
            InputMethodManager keyboard =
                    (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            keyboard.showSoftInput(amount, 0);
        }, 300);
    }

    public static EnterAmountDialog newInstance(@NonNull BudgetConfig budgetCategory) {
        EnterAmountDialog fragment = new EnterAmountDialog();
        Bundle args = new Bundle();
        args.putParcelable("budget_category", budgetCategory);
        args.putBoolean("is_edit", budgetCategory.getTotalamount() != 0);
        fragment.setArguments(args);
        return fragment;
    }

    private void initializeViewVariables() {
        view = getActivity().getLayoutInflater()
                .inflate(R.layout.dialog_budget_set_amount, new LinearLayout(getActivity()), false);

        categoryIcon = (ImageView) view.findViewById(R.id.iv_category_icon);
        categoryName = (TextView) view.findViewById(R.id.tv_category_name);
        amount = (EditText) view.findViewById(R.id.amount);
        saveAmount = (LinearLayout) view.findViewById(R.id.save);
        deleteAmount = (LinearLayout) view.findViewById(R.id.delete);
        if (!isEdit) deleteAmount.setVisibility(View.GONE);
        SoftKeyboardUtils.focusAndOpenKeyboard(amount, context);
    }

    private void attachListeners() {
        saveAmount.setOnClickListener(v -> {
            saveAmount();
        });

        deleteAmount.setOnClickListener(v -> deleteAmount());
        amount.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                InputMethodManager in =
                        (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(amount.getApplicationWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                saveAmount();
                return true;
            }
            return false;
        });
    }


    private void setData() {
        categoryName.setText(mBudgetConfig.getCategory());
    }

    private void setDialogDimensions() {
        if (getDialog() != null && getDialog().getWindow() != null) {
            DisplayMetrics metrics = getResources().getDisplayMetrics();
            int width = metrics.widthPixels;
            int height = metrics.heightPixels;
            getDialog().getWindow().setLayout(width * 8 / 10, height * DIALOG_HEIGHT / 10);
            if (mBudgetConfig.getTotalamount() != 0.0) {
                amount.setText(
                        String.format("%s", mBudgetConfig.getTotalamount()));
                amount.selectAll();
            }
        }
    }


    /**
     * Save the budget amount
     */
    private void saveAmount() {
        try {
            double amountValue = Double.parseDouble(amount.getText().toString());
            if (amountValue == 0) {
                Toast.makeText(context, "Enter Valid Amount", Toast.LENGTH_SHORT).show();
                return;
            }
            mBudgetConfig.setTotalamount(amountValue);
            BudgetModificationDao.saveBudget(mBudgetConfig, isEdit);
            dismiss();
        } catch (NumberFormatException e) {
            Toast.makeText(context, "Please enter valid amount", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteAmount() {
        BudgetModificationDao.deleteBudget(mBudgetConfig.getCategory());
        dismiss();
    }


}