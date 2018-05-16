package com.albertosoto.magnolia.bigdata.data.generic;

import java.io.Serializable;
import java.util.HashMap;

/**
 * backoffice-magnolia
 * com.deicos.magnolia.data
 * Created by Alberto Soto Fernandez in 19/05/2017.
 * Description:
 * extends Enums usage
 */
public class EnumStructure implements Serializable {

    private CgEnum[] enumItems;

    public CgEnum[] getEnumData() {
        return enumItems;
    }

    public void setEnumData(CgEnum[] events) {
        this.enumItems = events;
    }

    /**
     * Devuelve el objeto necesario para rendering
     *
     * @return
     */
    public HashMap<String, String> getDataValues() {
        HashMap<String, String> rtn = new HashMap<>();
        for (CgEnum elem : enumItems) {
            rtn.put(elem.getItemValue(), elem.getItemLabel());
        }
        return rtn;
    }
}
