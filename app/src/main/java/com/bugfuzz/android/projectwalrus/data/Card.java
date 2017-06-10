package com.bugfuzz.android.projectwalrus.data;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable()
public class Card {
    @DatabaseField(generatedId = true)
    public int id;

    @DatabaseField
    public String name;

    @DatabaseField(dataType = DataType.SERIALIZABLE)
    public CardData cardData;

    public Card() {
    }
}
