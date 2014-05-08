/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package klijentnapostaji.citac;

import java.nio.charset.Charset;
import java.text.DateFormat;
import java.util.List;
import klijentnapostaji.citac.filelistgeneratori.FileListGenerator;
import klijentnapostaji.webservice.PrihvatServisLocalImpl;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kraljevic
 */
public class CsvFileTickerTest {
    
    public CsvFileTickerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of setChareset method, of class CsvFileTicker.
     */
    @Test
    public void testSetChareset() {
        System.out.println("setChareset");
        Charset chareset = null;
        CsvFileTicker instance = new CsvFileTicker();
        instance.setChareset(chareset);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setSeparator method, of class CsvFileTicker.
     */
    @Test
    public void testSetSeparator() {
        System.out.println("setSeparator");
        char separator = ' ';
        CsvFileTicker instance = new CsvFileTicker();
        instance.setSeparator(separator);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setServis method, of class CsvFileTicker.
     */
    @Test
    public void testSetServis() {
        System.out.println("setServis");
        PrihvatServisLocalImpl servis = null;
        CsvFileTicker instance = new CsvFileTicker();
        instance.setServis(servis);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setFileListGen method, of class CsvFileTicker.
     */
    @Test
    public void testSetFileListGen() {
        System.out.println("setFileListGen");
        FileListGenerator fileListGen = null;
        CsvFileTicker instance = new CsvFileTicker();
        instance.setFileListGen(fileListGen);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setCsvOBuilder method, of class CsvFileTicker.
     */
    @Test
    public void testSetCsvOBuilder() {
        System.out.println("setCsvOBuilder");
        CsvOmotnicaBuilder csvOBuilder = null;
        CsvFileTicker instance = new CsvFileTicker();
        instance.setCsvOBuilder(csvOBuilder);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of run method, of class CsvFileTicker.
     */
    @Test
    public void testRun() {
        System.out.println("run");
        CsvFileTicker instance = new CsvFileTicker();
        instance.run();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setStupciSaVremenom method, of class CsvFileTicker.
     */
    @Test
    public void testSetStupciSaVremenom() {
        System.out.println("setStupciSaVremenom");
        List<Integer> stupciSaVremenom = null;
        CsvFileTicker instance = new CsvFileTicker();
      //  instance.setStupciSaVremenom(stupciSaVremenom);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setDateFormat method, of class CsvFileTicker.
     */
    @Test
    public void testSetDateFormat() {
        System.out.println("setDateFormat");
        DateFormat dateFormat = null;
        CsvFileTicker instance = new CsvFileTicker();
        instance.setDateFormat(dateFormat);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
