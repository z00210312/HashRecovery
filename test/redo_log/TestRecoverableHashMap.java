/*
 * JUnit test cases for RecoverableHashMap
 * This is created by instructor
 * This is last modifed in 5/18/2018
 */

package redo_log;

import cs4311.hw4.RecoveryManager;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Dell
 */
public class TestRecoverableHashMap {
    Map<Integer, String> map;
    Map<String, Student> map2;
    HashMap<Integer, String> realMap;
    TreeMap<String,Student> realMap2;
    RecoveryManager <Integer, String> mgr;
    RecoveryManager <String, Student> mgr2;
    
    public TestRecoverableHashMap() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws IOException {
        realMap = new HashMap<>();
        mgr = new RecoveryManager<>(realMap, "map.txt", "log.txt");
        map = mgr.getMap();
        realMap2 = new TreeMap<>();
        mgr2 = new RecoveryManager<>(realMap2, "map2.txt", "log2.txt");
        map2 = mgr2.getMap();
    }
    
    @After
    public void tearDown() throws IOException {
        mgr.close();
        mgr2.close();
    }

    /**
     * Simple case where Map is not saved, just operations in log
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    @Test
    public void recoverFromEmpty() throws IOException, ClassNotFoundException {
        map.put(1, "Sue");
        realMap = null;
        mgr.recover();
        map = mgr.getMap();
        assertEquals("Sue", map.get(1));
    }
    
    /**
     * Slightly more complex scenario where Map has been saved
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    @Test
    public void recoverFromNonEmpty() throws IOException, ClassNotFoundException {
        map.put(1, "Sue");
        mgr.flush();
        map.put(2, "Bob");
        realMap = null;
        mgr.recover();
        map = mgr.getMap();
        assertEquals("Sue", map.get(1));
        assertEquals("Bob", map.get(2));
     }
    
    /**
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @Test
    public void putThenClear() throws IOException, ClassNotFoundException {
        map.put(1, "Sue");
        map.clear();
        mgr.recover();
        map = mgr.getMap();
        assertEquals(null, map.get(1));
    }
    
    @Test
    public void remove() throws IOException, ClassNotFoundException {
        map.put(1, "Sue");
        map.remove(1);
        mgr.recover();
        map = mgr.getMap();
        assertEquals(null, map.get(1));
    }
    
    @Test
    public void replace() throws IOException, ClassNotFoundException {
        map.put(1, "Sue");
        map.replace(1, "Paul");
        mgr.recover();
        map = mgr.getMap();
        assertEquals("Paul", map.get(1));
    }

    @Test
    public void testDifferentTypes() throws IOException, ClassNotFoundException {
        Student s = new Student("Mel Harris", 1);
        map2.put("mh1234", s);
        mgr2.recover();
        map2 = mgr2.getMap();
        assertEquals("Mel Harris", map2.get("mh1234").getName());
    }

    private static class Student implements Serializable {
        String name;
        int yrInSchool;

        public Student(String nm, int yr) {
            name = nm;
            yrInSchool = yr;
        }

        String getName() { return name; }
        int getYrInSchool() { return yrInSchool; }
    }
}