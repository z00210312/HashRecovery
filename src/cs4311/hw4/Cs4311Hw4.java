/*
 * Test driver for RecoverableHashMap and RecoverManager
 * This is created by Jia Shin Tseng
 * This is last modifed by 5/18/2018
 */

package cs4311.hw4;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author verastu
 */


public class Cs4311Hw4 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Map<Integer, String> map;
        Map<String, Student> map2;
        HashMap<Integer, String> realMap;
        TreeMap<String,Student> realMap2;
        RecoveryManager <Integer, String> mgr;
        RecoveryManager <String, Student> mgr2;
        
        realMap = new HashMap<>();
        mgr = new RecoveryManager<>(realMap, "map.txt", "log.txt");
        map = mgr.getMap();
        realMap2 = new TreeMap<>();
        mgr2 = new RecoveryManager<>(realMap2, "map2.txt", "log2.txt");
        map2 = mgr2.getMap();
        
        
        System.out.println("First Case: ");
        // code start here
        
        map.put(1, "Sue");
        realMap = null;
        mgr.recover();
        map = mgr.getMap();
        
        if("Sue".equals(map.get(1)))
            System.out.println("True");
        mgr.close();
        mgr2.close();
        
        System.out.println("Second Case: ");
        Student s = new Student("Mel Harris", 1);
        map2.put("mh1234", s);
        mgr2.recover();
        map2 = mgr2.getMap();
        if("Mel Harris".equals(map2.get("mh1234").getName()))
            System.out.println("True");
        
        mgr.close();
        mgr2.close();
        
        // code end here
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
        
        @Override
        public String toString(){
            return name;
        }
    }
}
