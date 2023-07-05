package com.example.cardiacrecorder;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

public class DataListTest {



    @Test
    public void testAddData() {



        DataList dataList1=new DataList();

        DataModel dataModel=new DataModel("75","130","72","01-01-2023","12:00AM","New Comment1");

        dataList1.addData(dataModel);

        assertEquals(1, dataList1.getData().size());


        DataModel dataModel2=new DataModel("175","10","92","01-12-2023","10:00AM","New Comment2");

        dataList1.addData(dataModel2);
        assertEquals(2, dataList1.getData().size());

        assertTrue(dataList1.getData().contains(dataModel));
        assertTrue(dataList1.getData().contains(dataModel2));

    }



    @Test
    public void testDeleteData() {


        DataList dataList1=new DataList();

        DataModel dataModel=new DataModel("75","130","72","01-01-2023","12:00AM","New Comment1");

        dataList1.addData(dataModel);

        assertEquals(1, dataList1.getData().size());


        DataModel dataModel2=new DataModel("175","10","92","01-12-2023","10:00AM","New Comment2");

        dataList1.addData(dataModel2);
        assertEquals(2, dataList1.getData().size());



        dataList1.deleteData(dataModel);
       assertEquals(1, dataList1.getData().size());
        assertFalse(dataList1.getData().contains(dataModel));

        dataList1.deleteData(dataModel2);
        assertEquals(0, dataList1.getData().size());
        assertFalse(dataList1.getData().contains(dataModel2));
    }

    @Test
    public void testAddRecordException() {



        DataList dataList=new DataList();

        DataModel dataModel=new DataModel("75","130","72","01-01-2023","12:00AM","New Comment1");

        dataList.addData(dataModel);

        assertThrows(IllegalArgumentException.class, () -> dataList.addData(dataModel));
    }

    /**
     * testing deleteData method for exceptions
     */
    @Test
    public void testDeleteRecordException() {


        DataList dataList=new DataList();

        DataModel dataModel=new DataModel("75","130","72","01-01-2023","12:00AM","New Comment1");

        dataList.addData(dataModel);

        assertThrows(IllegalArgumentException.class, () -> dataList.addData(dataModel));


        dataList.deleteData(dataModel);

        assertThrows(IllegalArgumentException.class, () -> dataList.deleteData(dataModel));
    }





    }
