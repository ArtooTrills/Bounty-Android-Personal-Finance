package com.example.earthshaker.moneybox.common.dao.db;

import android.provider.BaseColumns;

/**
 * Created by earthshaker on 14/5/17.
 */

public interface BaseContract extends BaseColumns {
    String COLUMN_NAME_CREATED_TIME = "createdTime";
    String COLUMN_NAME_LAST_UPDATED_TIME = "lastUpdatedTime";
    String COLUMN_NAME_STATUS = "status";

}
