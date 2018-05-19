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

package com.bugfuzz.android.projectwalrus.card.carddata;

import android.arch.core.util.Function;
import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;

import com.bugfuzz.android.projectwalrus.R;
import com.bugfuzz.android.projectwalrus.card.carddata.ui.StaticKeyMifareReadAttemptDialogFragment;
import com.bugfuzz.android.projectwalrus.util.MiscUtils;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Collections;
import java.util.Set;

@MifareReadAttempt.Metadata(
        layoutId = R.layout.layout_static_key_mifare_read_attempt,
        dialogFragment = StaticKeyMifareReadAttemptDialogFragment.class
)
public class StaticKeyMifareReadAttempt extends MifareReadAttempt {

    public final Set<MifareCardData.SectorNumber> sectorNumbers;

    public final MifareCardData.Key key;
    public final MifareCardData.KeySlot slot;

    public StaticKeyMifareReadAttempt(Set<MifareCardData.SectorNumber> sectorNumbers,
            MifareCardData.Key key, MifareCardData.KeySlot slot) {
        if (sectorNumbers.isEmpty()) {
            throw new IllegalArgumentException("Empty sector set");
        }

        if (key == null) {
            throw new IllegalArgumentException("Null key");
        }

        if (slot == null) {
            throw new IllegalArgumentException("Null slot");
        }

        this.sectorNumbers = Collections.unmodifiableSet(sectorNumbers);
        this.key = key;
        this.slot = slot;
    }

    public SpannableStringBuilder getDescription(Context context) {
        SpannableStringBuilder builder = new SpannableStringBuilder();

        MiscUtils.appendAndSetSpan(builder, "Sectors: ",
                new StyleSpan(android.graphics.Typeface.BOLD), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.append(MiscUtils.unparseIntRanges(sectorNumbers,
                new Function<MifareCardData.SectorNumber, Integer>() {
                    @Override
                    public Integer apply(MifareCardData.SectorNumber input) {
                        return input.number;
                    }
                }));
        builder.append('\n');

        MiscUtils.appendAndSetSpan(builder, "Key: ",
                new StyleSpan(android.graphics.Typeface.BOLD), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.append(key.toString());
        builder.append('\n');

        MiscUtils.appendAndSetSpan(builder, "Slot(s): ",
                new StyleSpan(android.graphics.Typeface.BOLD), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.append(slot == MifareCardData.KeySlot.BOTH ? context.getString(R.string.both) :
                slot.toString());

        return builder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StaticKeyMifareReadAttempt that = (StaticKeyMifareReadAttempt) o;

        return new EqualsBuilder()
                .append(sectorNumbers, that.sectorNumbers)
                .append(key, that.key)
                .append(slot, that.slot)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(sectorNumbers)
                .append(key)
                .append(slot)
                .toHashCode();
    }
}
