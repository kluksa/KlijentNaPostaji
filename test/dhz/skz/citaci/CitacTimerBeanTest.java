/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dhz.skz.citaci;

import javax.ejb.embeddable.EJBContainer;
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
public class CitacTimerBeanTest {
    
    public CitacTimerBeanTest() {
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

//    /**
//     * Test of myTimer method, of class CitacTimerBean.
//     */
//    @Test
//    public void testMyTimer() throws Exception {
//        System.out.println("myTimer");
//        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
//        CitacTimerBean instance = (CitacTimerBean)container.getContext().lookup("java:global/classes/CitacTimerBean");
//        instance.myTimer();
//        container.close();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of pokreniCitace method, of class CitacTimerBean.
     */
    @Test
    public void testPokreniCitace() throws Exception {
        System.out.println("pokreniCitace");
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        CitacTimerBean instance = (CitacTimerBean)container.getContext().lookup("java:global/classes/CitacTimerBean");
        instance.pokreniCitace();
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
