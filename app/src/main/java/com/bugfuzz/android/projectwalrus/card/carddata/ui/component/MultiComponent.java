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

package com.bugfuzz.android.projectwalrus.card.carddata.ui.component;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import java.util.List;

public class MultiComponent extends ContainerComponent {

    private final List<Component> children;
    private final LinearLayout viewGroup;

    public MultiComponent(Context context, String title, List<Component> children) {
        super(context, title);

        this.children = children;

        viewGroup = new LinearLayout(context);
        viewGroup.setOrientation(LinearLayout.VERTICAL);

        for (Component child : children)
            if (child.getView() != null)
                viewGroup.addView(child.getView());
    }

    @Nullable
    @Override
    protected View getInnerView() {
        return viewGroup;
    }

    @Override
    public List<Component> getChildren() {
        return children;
    }
}
