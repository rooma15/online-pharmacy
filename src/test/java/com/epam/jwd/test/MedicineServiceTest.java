package com.epam.jwd.test;

import com.epam.jwd.ConnectionPool.ConnectionPool;
import com.epam.jwd.ConnectionPool.Impl.DBConnectionPool;
import com.epam.jwd.Servlet.model.MedicineDto;
import com.epam.jwd.Servlet.service.impl.MedicineService;
import com.epam.jwd.Util;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class MedicineServiceTest {
    ConnectionPool connectionPool = DBConnectionPool.getInstance();
    MedicineService service = new MedicineService();

    @Before
    public  void createConnections(){
        try {
            connectionPool.init();
        } catch (SQLException e) {
            Util.lOGGER.error(e.getMessage());
        }
    }

    @After
    public void destroyConnections(){
        connectionPool.destroy();
    }

    @Test
    public void findAllTest(){
        List<MedicineDto> all = service.findAll();
        String name1 = "Вильпрафен";
        String name2 = "tester";
        int dose1 = 500;
        int dose2 = 120;
        assertEquals(name1, all.get(0).getName());
        assertEquals(name2, all.get(all.size() - 1).getName());
        assertEquals(dose1, all.get(0).getDose());
        assertEquals(dose2, all.get(all.size() - 1).getDose());
        assertEquals(7, all.size());
    }

    @Test
    public void findByIdTest(){
        assertEquals("солицин", service.findById(24).get().getName());
    }

    @Test
    public void updateByIdTest(){
        MedicineDto newMedicine = new MedicineDto("Test",
                400,
                true,
                "desc",
                "ind",
                "contraind",
                "side Effects",
                "consist",
                "compos",
                500,
                "Антибиотики",
                "medicine.jpg");
        assertEquals(true, service.updateById(22, newMedicine));
        assertEquals(newMedicine, service.findById(22).get());
    }

}
