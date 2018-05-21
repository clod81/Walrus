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

package com.bugfuzz.android.projectwalrus.device;

import android.support.annotation.StringRes;

import com.bugfuzz.android.projectwalrus.R;
import com.bugfuzz.android.projectwalrus.card.carddata.CardData;

public abstract class CardDataIOOperation extends CardDeviceOperation {

    CardDataIOOperation(CardDevice cardDevice) {
        super(cardDevice);
    }

    public abstract Class<? extends CardData> getCardDataClass();

    @StringRes
    public int getWaitingStringId() {
        return R.string.performing_operation;
    }

    @StringRes
    public int getStopStringId() {
        return R.string.cancel_button;
    }

    @StringRes
    public int getErrorStringId() {
        return R.string.failed_to_perform_operation;
    }
}
