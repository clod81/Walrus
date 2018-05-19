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

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import com.bugfuzz.android.projectwalrus.card.carddata.MifareCardData;
import com.bugfuzz.android.projectwalrus.card.carddata.MifareReadAttempt;
import com.bugfuzz.android.projectwalrus.card.carddata.StaticKeyMifareReadAttempt;
import com.bugfuzz.android.projectwalrus.ui.SimpleBindingListAdapter;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

public class MifareReadSetupDialogViewModel extends ViewModel {

    private final MutableLiveData<List<ReadAttemptItem>> readAttempts =
            new MutableLiveData<>();
    private final MutableLiveData<ReadAttemptDialogInfo> showNewReadAttemptDialog =
            new MutableLiveData<>();

    private int nextReadAttemptItemId = 0;

    public MifareReadSetupDialogViewModel() {
        readAttempts.setValue(new ArrayList<ReadAttemptItem>());

        // TODO XXX: remove vvv
        for (int i = 0; i < 3; ++i) {
            onNewReadAttempt(new StaticKeyMifareReadAttempt(
                    new LinkedHashSet<>(
                            Lists.transform(Arrays.asList(1, 2, 3, 4),
                                    new Function<Integer, MifareCardData.SectorNumber>() {
                                        @Override
                                        @Nullable
                                        public MifareCardData.SectorNumber apply(
                                                @Nullable Integer input) {
                                            return new MifareCardData.SectorNumber(input);
                                        }
                                    })),
                    new MifareCardData.Key(
                            new byte[]{(byte) 0xaa, (byte) i, (byte) i, (byte) i, (byte) i,
                                    (byte) i}),
                    MifareCardData.KeySlot.A), -1);
        }
        // TODO XXX: remove ^^^
    }

    public LiveData<List<ReadAttemptItem>> getReadAttempts() {
        return readAttempts;
    }

    public LiveData<ReadAttemptDialogInfo> getShowNewReadAttemptDialog() {
        return showNewReadAttemptDialog;
    }

    public void onReadAttemptItemClick(ReadAttemptItem readAttemptItem) {
        showNewReadAttemptDialog.setValue(new ReadAttemptDialogInfo(readAttemptItem.readAttempt,
                readAttemptItem.readAttempt.getClass(), readAttemptItem.id));
    }

    public void onReadAttemptMove(int fromPosition, int toPosition) {
        List<ReadAttemptItem> currentList = readAttempts.getValue();

        if (fromPosition >= currentList.size() || toPosition >= currentList.size()) {
            return;
        }

        List<ReadAttemptItem> newList = new ArrayList<>(currentList);
        Collections.swap(newList, fromPosition, toPosition);
        readAttempts.setValue(newList);
    }

    public void onReadAttemptSwipe(int position) {
        List<ReadAttemptItem> currentList = readAttempts.getValue();

        if (position >= currentList.size()) {
            return;
        }

        List<ReadAttemptItem> newList = new ArrayList<>(currentList);
        newList.remove(position);
        readAttempts.setValue(newList);
    }

    public void onAddReadAttemptClick() {
        showNewReadAttemptDialog.setValue(new ReadAttemptDialogInfo(null,
                StaticKeyMifareReadAttempt.class, -1));
    }

    public void onNewReadAttemptDialogShown() {
        showNewReadAttemptDialog.setValue(null);
    }

    public void onNewReadAttempt(MifareReadAttempt readAttempt, int callbackId) {
        List<ReadAttemptItem> newList = new ArrayList<>(readAttempts.getValue());

        if (callbackId != -1) {
            int i = 0;
            for (ReadAttemptItem readAttemptItem : newList) {
                if (readAttemptItem.id == callbackId) {
                    newList.set(i, new ReadAttemptItem(readAttemptItem.id, readAttempt));
                    break;
                }

                ++i;
            }
        } else {
            newList.add(new ReadAttemptItem(nextReadAttemptItemId++, readAttempt));
        }

        readAttempts.setValue(newList);
    }

    public void onStartClick() {
        // TODO YYY: create MifareReadSetup class, set a result MLD here, handle out in fragment
        // (abstract - overridden by device specific anon class)
    }

    public static class ReadAttemptItem implements
            SimpleBindingListAdapter.Item<MifareReadAttempt> {

        public final int id;
        public final MifareReadAttempt readAttempt;

        public ReadAttemptItem(int id, MifareReadAttempt readAttempt) {
            this.id = id;
            this.readAttempt = readAttempt;
        }

        @Override
        public int getId() {
            return id;
        }

        @Override
        public MifareReadAttempt getContents() {
            return readAttempt;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }

            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            ReadAttemptItem that = (ReadAttemptItem) o;

            return new EqualsBuilder()
                    .append(id, that.id)
                    .append(readAttempt, that.readAttempt)
                    .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 37)
                    .append(id)
                    .append(readAttempt)
                    .toHashCode();
        }
    }

    public class ReadAttemptDialogInfo {

        public final MifareReadAttempt readAttempt;
        public final Class<? extends MifareReadAttempt> readAttemptClass;
        public final int callbackId;

        public ReadAttemptDialogInfo(MifareReadAttempt readAttempt,
                Class<? extends MifareReadAttempt> readAttemptClass, int callbackId) {
            this.readAttempt = readAttempt;
            this.readAttemptClass = readAttemptClass;
            this.callbackId = callbackId;
        }
    }
}
