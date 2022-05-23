package com.example.android.pets.data;

import android.provider.BaseColumns;

public final class PetsContract {
    private PetsContract(){

    }
    public static final class PetsEntry implements BaseColumns {
        public static final String table_name = "pets";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_PET_NAME = "name";
        public static final String COLUMN_PET_BREED = "breed";
        public static final String COLUMN_PET_GENDER = "gender";
        public static final String COLUMN_PET_WEIGHT = "weight";
        public static final int GENDER_MALE = 1;
        public static final int GENDER_FEMALE = 2;
        public static final int GENDER_UNKNOWN = 0;
    }
}
