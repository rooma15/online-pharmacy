package com.epam.jwd.test;
import com.epam.jwd.ConnectionPool.ConnectionPool;
import com.epam.jwd.ConnectionPool.Impl.DBConnectionPool;
import com.epam.jwd.Servlet.model.Prescription;
import com.epam.jwd.Servlet.service.impl.PrescriptionService;
import com.epam.jwd.Servlet.Util.Util;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
public class PrescriptionServiceTest {
    ConnectionPool connectionPool = DBConnectionPool.getInstance();
    PrescriptionService service = new PrescriptionService();

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
    public void checkPrescriptionTest(){
        assertEquals(true, service.checkPrescription(16, 50));
    }

    @Test
    public void findAcceptedTest(){
        String name1 = "волипирин";
        String name2 = "контомицол";
        assertEquals(name1, service.findAccepted(true).get(0).getMedicineName());
        assertEquals(name2, service.findAccepted(true).get(1).getMedicineName());
        assertEquals(2, service.findAccepted(true).size());
        assertEquals(0, service.findAccepted(false).size());
    }

    @Test
    public void findByUserIdMedicineIdTest() {
        Prescription prescription = new Prescription(18, 50, 16, true);
        assertEquals(prescription, service.findByUserIdMedicineId(50, 16).get());
    }

    @Test
    public void findByUserIdTest(){
        Prescription prescription = new Prescription(18, 50, 16, true);
        List<Prescription> prescriptions = new ArrayList<>();
        prescriptions.add(prescription);
        assertArrayEquals(prescriptions.toArray(), service.findByUserId(50).toArray());
    }

    @Test
    public void setAcceptedTest(){
        assertEquals(true, service.setAccepted(25));
    }

}
