/**
 * Copyright (c) 2008-2023, MOVES Institute, Naval Postgraduate School (NPS). All rights reserved.
 * This work is provided under a BSD open-source license, see project license.html and license.txt
 */

package edu.nps.moves.dis7.pdus;

/** Abstract interface for PDU SubCategory */
public interface SubCategory
{
    /**
     * Value for this field
     * @return the value of this SubCategory
     */
    int getValue();
    
    /**
     * Description for this field
     * @return the description of this SubCategory
     */
    String getDescription();
}
