/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */
package com.mycompany.registration;

import java.util.ArrayList;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author RC_Student_Lab
 */
public class MessageManagerNGTest {
    
    public MessageManagerNGTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

    /**
     * Test of addMessage method, of class MessageManager.
     */
    @Test
    public void testAddMessage() {
        System.out.println("addMessage");
        message message = null;
        String status = "";
        MessageManager instance = new MessageManager();
        boolean expResult = false;
        boolean result = instance.addMessage(message, status);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of processSingleMessage method, of class MessageManager.
     */
    @Test
    public void testProcessSingleMessage() {
        System.out.println("processSingleMessage");
        String recipientNumber = "";
        int messageIndex = 0;
        MessageManager instance = new MessageManager();
        boolean expResult = false;
        boolean result = instance.processSingleMessage(recipientNumber, messageIndex);
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of populateTestData method, of class MessageManager.
     */
    @Test
    public void testPopulateTestData() {
        System.out.println("populateTestData");
        MessageManager instance = new MessageManager();
        instance.populateTestData();
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of displayLongestMessage method, of class MessageManager.
     */
    @Test
    public void testDisplayLongestMessage() {
        System.out.println("displayLongestMessage");
        MessageManager instance = new MessageManager();
        instance.displayLongestMessage();
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of searchByMessageID method, of class MessageManager.
     */
    @Test
    public void testSearchByMessageID() {
        System.out.println("searchByMessageID");
        MessageManager instance = new MessageManager();
        instance.searchByMessageID();
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of searchByRecipient method, of class MessageManager.
     */
    @Test
    public void testSearchByRecipient() {
        System.out.println("searchByRecipient");
        MessageManager instance = new MessageManager();
        instance.searchByRecipient();
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of deleteByMessageHash method, of class MessageManager.
     */
    @Test
    public void testDeleteByMessageHash() {
        System.out.println("deleteByMessageHash");
        MessageManager instance = new MessageManager();
        instance.deleteByMessageHash();
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of displayFullReport method, of class MessageManager.
     */
    @Test
    public void testDisplayFullReport() {
        System.out.println("displayFullReport");
        MessageManager instance = new MessageManager();
        instance.displayFullReport();
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of displaySentMessages method, of class MessageManager.
     */
    @Test
    public void testDisplaySentMessages() {
        System.out.println("displaySentMessages");
        MessageManager instance = new MessageManager();
        instance.displaySentMessages();
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of getStatistics method, of class MessageManager.
     */
    @Test
    public void testGetStatistics() {
        System.out.println("getStatistics");
        MessageManager instance = new MessageManager();
        String expResult = "";
        String result = instance.getStatistics();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of clearAllData method, of class MessageManager.
     */
    @Test
    public void testClearAllData() {
        System.out.println("clearAllData");
        MessageManager instance = new MessageManager();
        instance.clearAllData();
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of getTotalMessageCount method, of class MessageManager.
     */
    @Test
    public void testGetTotalMessageCount() {
        System.out.println("getTotalMessageCount");
        MessageManager instance = new MessageManager();
        int expResult = 0;
        int result = instance.getTotalMessageCount();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
       
    }

    /**
     * Test of getSentMessageCount method, of class MessageManager.
     */
    @Test
    public void testGetSentMessageCount() {
        System.out.println("getSentMessageCount");
        MessageManager instance = new MessageManager();
        int expResult = 0;
        int result = instance.getSentMessageCount();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of getStoredMessageCount method, of class MessageManager.
     */
    @Test
    public void testGetStoredMessageCount() {
        System.out.println("getStoredMessageCount");
        MessageManager instance = new MessageManager();
        int expResult = 0;
        int result = instance.getStoredMessageCount();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of getDisregardedMessageCount method, of class MessageManager.
     */
    @Test
    public void testGetDisregardedMessageCount() {
        System.out.println("getDisregardedMessageCount");
        MessageManager instance = new MessageManager();
        int expResult = 0;
        int result = instance.getDisregardedMessageCount();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of getAllMessages method, of class MessageManager.
     */
    @Test
    public void testGetAllMessages() {
        System.out.println("getAllMessages");
        MessageManager instance = new MessageManager();
        ArrayList expResult = null;
        ArrayList result = instance.getAllMessages();
        assertEquals(result, expResult);
        // TODO review the generated test code and remove the default call to fail.
        
    }
    
}
