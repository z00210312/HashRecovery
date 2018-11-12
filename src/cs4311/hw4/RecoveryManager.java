/*
 * RecoveryManager class allows the HashMap to flush and to recover from crash state
 * This is created by Jia Shin Tseng
 * This is last modifed by 5/18/2018
 */

package cs4311.hw4;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author verastu
 */
public class RecoveryManager<K,V> implements Serializable{
    // map for the hashmap
    private RecoverableHashMap map;
    private Map realmap;
    // recovery text file names
    private final String filename;
    private final String logname;
    // constructor
    public RecoveryManager(Map _map, String _filename, String _logname){
        realmap = _map;
        map = new RecoverableHashMap(_map,_logname);
        filename = _filename;
        logname = _logname;
    }
    // clear log file and update the map file
    public void flush(){
        File fileCker;
        // clear logs
        fileCker = new File(logname);
        if(fileCker.exists())
            fileCker.delete();
        // save the HashMap into file
        fileCker = new File(filename);
        try {
            if(!fileCker.exists()){
                fileCker.createNewFile();
            }
            FileOutputStream file = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(file);
            for (Iterator it = map.entrySet().iterator(); it.hasNext();) {
                Entry entry = (Entry) it.next();
                out.writeObject(entry.getKey());
                out.writeObject(entry.getValue());
            }
            out.close();
            file.close();
        }
        catch (FileNotFoundException ex) {
            System.out.println(ex + "occured");
        }
        catch (IOException ex) {
            System.out.println(ex + "occured");
        }
    }
    // recover the map file and log operations
    public void recover(){
        // start new HashMap;
        map = new RecoverableHashMap(realmap,logname);
        
        File fileCker;
        FileInputStream file;
        ObjectInputStream in;
        Map tempmap = new HashMap();
        try {
            
            // read SavedMap
            fileCker = new File(filename);
            if(fileCker.exists()){
                file = new FileInputStream(filename);
                in = new ObjectInputStream(file);
                while(file.available()!=0){
                    tempmap.put(in.readObject(), in.readObject());
                }
                in.close();
                file.close();
            }
            // read logs
            fileCker = new File(logname);
            if(fileCker.exists()){
                file = new FileInputStream(logname);
                in = new ObjectInputStream(file);
                while(file.available()!=0){
                    Object key = in.readObject();
                    Object value = in.readObject();
                    tempmap.put((K)key, (V)value);
                    if(tempmap.get(key) == null)
                        tempmap.remove(key);
                }
                in.close();
                file.close();
            }
            // save into hashMap;
            map.putAll(tempmap);
        }
        catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex + "occured");
        } 
    }
    // return map
    public Map getMap(){
        return map;
    }
    // display HashMap
    public void display(){
        for (Iterator it = map.entrySet().iterator(); it.hasNext();) {
            Entry entry = (Entry) it.next();
            System.out.println(entry.getKey() + "@" + entry.getValue());
        }
    }
    public void close(){
        // clear the map;
        map.clear();
        // clear the stored hashMap
        File fileCker;
        fileCker = new File(filename);
        fileCker.delete();
    }
}