package com.example.cardiacrecorder;

import java.util.ArrayList;
import java.util.List;

public class DataList {

    public List<DataModel> records = new ArrayList<>(); //a list of type "AddNewData" is declared

    /**
     * this method is used to add any new data
     * if data already exists,it will throw an exception
     * @param data a new record
     */
    public void addData(DataModel data)
    {
        if(records.contains(data))
        {
            throw new IllegalArgumentException();
        }
        records.add(data);
    }

    /**
     * this method returns an instance of sorted record list
     * sort is based on first attribute by default
     * @return a list of data
     */

    public List<DataModel> getData()
    {
        List<DataModel>dataList = records;
        return dataList;
    }

//    public List<DataModel> getData(int x)
//    {
//        List<DataModel> dataList = records;
//        return dataList;
//    }

    /**
     * this method is used for deleting a particular data
     * if the data doesnt exist,it will throw an exception
     * @param data a data that need to be deleted
     */
    public void deleteData(DataModel data)
    {
        List<DataModel> dataList = records;
        if(dataList.contains(data)){
            records.remove(data);
        }
        else
        {
            throw new IllegalArgumentException();
        }
    }

    /**
     * this method returns the size of list
     * @return int
     */
    public int countData()
    {
        return records.size();
    }

    public void updateData(DataModel oldData, DataModel newData) {
        int index = records.indexOf(oldData);
        if (index != -1) {
            records.set(index, newData);
        } else {
            throw new IllegalArgumentException("Data not found");
        }
    }

}
