package com.testapp.camdrive;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.testapp.camdrive.R;

public class LoadDialog extends DialogFragment {

    @SuppressLint("SetTextI18n")
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState ) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.load_dialog);
        dialog.show();
        return dialog;
    }
}
