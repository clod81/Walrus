/*
 * Copyright 2018 Daniel Underhay & Matthew Daley.
 *
 * This file is part of Walrus.
 *
 * Walrus is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Walrus is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Walrus.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.bugfuzz.android.projectwalrus.card.carddata.ui;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bugfuzz.android.projectwalrus.R;
import com.bugfuzz.android.projectwalrus.databinding.MifareReadSetupBinding;

public class MifareReadSetupDialogFragment extends DialogFragment {

    public static MifareReadSetupDialogFragment create(int callbackId) {
        MifareReadSetupDialogFragment dialog = new MifareReadSetupDialogFragment();

        Bundle args = new Bundle();
        args.putInt("callback_id", callbackId);
        dialog.setArguments(args);

        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new MaterialDialog.Builder(getActivity())
                .title("MIFARE read parameters")
                .customView(R.layout.layout_mifare_read_setup, true)
                .positiveText(R.string.start)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog,
                            @NonNull DialogAction which) {

                    }
                })
                .negativeText(android.R.string.cancel)
                .build();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        MifareReadSetupBinding binding = DataBindingUtil.bind(
                ((MaterialDialog) getDialog()).getContentView());
        binding.setViewModel(ViewModelProviders.of(this).get(MifareReadSetupViewModel.class));
    }
}
