/*
 *
 *  * Copyright (c) N2Tech Pvt. Ltd. - All Rights Reserved
 *  * Unauthorized copying of this file, via any medium is strictly prohibited
 *  * Proprietary and confidential
 *  * Written by N2Tech 14/3/2017
 *
 */

package com.example.earthshaker.moneybox.common;

public class LayoutNotAddedToXmlException extends Exception {

    @Override
    public String getMessage() {
        return "You must add no data layout in your xml file.";
    }
}
