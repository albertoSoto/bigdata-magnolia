package com.albertosoto.magnolia.bigdata.data.generic;

import java.util.ArrayList;
import java.util.List;

/**
 * backoffice-magnolia
 * com.deicos.magnolia.data
 * Created by Alberto Soto Fernandez in 19/05/2017.
 * Description:
 */
public class DataTableWrapper<T> {
    List<T> data;


    public DataTableWrapper() {
        this(new ArrayList<T>());
    }

    public DataTableWrapper(List<T> t) {
        setData(t);
    }

    /**
     * @return the persons
     */
    public List<T> getData() {
        return data;
    }

    /**
     * @param persons the persons to set
     */
    public void setData(List<T> persons) {
        this.data = persons;
    }
}
