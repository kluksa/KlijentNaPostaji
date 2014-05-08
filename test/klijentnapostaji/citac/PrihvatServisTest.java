/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package klijentnapostaji.citac;

import java.util.ArrayList;
import java.util.Date;
import klijentnapostaji.webservice.CsvOmotnica;
import klijentnapostaji.webservice.PrihvatServisLocalImpl;
import klijentnapostaji.webservice.PrihvatSirovihPodataka;
import klijentnapostaji.webservice.PrihvatSirovihPodataka_Service;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author kraljevic
 */
public class PrihvatServisTest {
    private PrihvatSirovihPodataka servis;
    private CsvOmotnicaBuilder builder;
    
    public PrihvatServisTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        PrihvatSirovihPodataka_Service psss = new PrihvatSirovihPodataka_Service();
        this.servis = psss.getPrihvatSirovihPodatakaPort();
        this.builder = new CsvOmotnicaBuilder();
        builder.setDatoteka("DaToTeKa");
        builder.setIzvor("i.z.v.o.r.");
        builder.setPostaja("POSTAJA");
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getVrijemeZadnjeg method, of class PrihvatServisLocalImpl.
     */
    @Test
    public void testGetVrijemeZadnjeg() {
        System.out.println("getVrijemeZadnjeg");
        String str = "";
        String str0 = "";
        String str1 = "";
        PrihvatServisLocalImpl instance = new PrihvatServisLocalImpl();
        Date expResult = new Date(1234L);
        Date result = instance.getVrijemeZadnjeg(str, str0, str1);
        assertEquals(expResult, result);
    }

    /**
     * Test of prebaciOmotnicu method, of class PrihvatServisLocalImpl.
     */
    @Test
    public void testPrebaciOmotnicu() throws Exception {
        System.out.println("prebaciOmotnicu");
        String[] headers =  new String[]{"A","B","C"};
        ArrayList<String[]> linije =  new ArrayList<>();
        linije.add(new String[]{"1","2","3"});
        linije.add(new String[]{"11","2","3"});
        linije.add(new String[]{"1","22","3"});
        linije.add(new String[]{"1","22","33"});
        CsvOmotnica create = builder.create(headers, linije);
        PrihvatServisLocalImpl instance = new PrihvatServisLocalImpl();
        instance.prebaciOmotnicu(create);
        // TODO review the generated test code and remove the default call to fail.
    }
    
}
