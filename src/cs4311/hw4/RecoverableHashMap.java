/*
 * RecoverableHashMap class allows the HashMap to be recovered after a fail occured
 * This is created by Jia Shin Tseng
 * This is last modifed by 5/18/2018
 */

package cs4311.hw4;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 *
 * @author verastu
 * @param <K> Integer: key
 * @param <V> String: value
 */
public class RecoverableHashMap<K,V> implements Map<K,V>{
    // recovery text file name
    private String logname;
    private Map<K, V> map;
    // constructor
    public RecoverableHashMap(Map<K,V> _map, String _logname){
        super();
        map = _map;
        logname = _logname;
    }
    // erase the HashMap
    @Override
    public void clear(){
        File fileCker = new File(logname);
        fileCker.delete();
        map.clear();
    }
    // add entry to the hashMap as well as update the log file
    @Override
    public V put(K key, V value){
        V newKey=null;
        File fileCker = new File(logname);
        // if no entry for the key
        if(this.get(key)==null){
            try {
                if(!fileCker.exists())
                    fileCker.createNewFile();
                FileOutputStream file = new FileOutputStream(logname);
                ObjectOutputStream out = new ObjectOutputStream(file);
                out.writeObject(key);
                out.writeObject(value);
                out.close();
                file.close();
                map.put(key, value);
            }
            catch (IOException ex) {
                System.out.println(ex + "occured");
            }
        }
        return newKey;
    }
    // remove entry from the hashMap as well as update the log file
    @Override
    public V remove(Object key){
        V tempV = map.remove(key);File fileCker = new File(logname);
        // if entry exists
        if(tempV!=null){
            try {
                if(!fileCker.exists())
                    fileCker.createNewFile();
                FileOutputStream file = new FileOutputStream(logname);
                ObjectOutputStream out = new ObjectOutputStream(file);
                out.writeObject(key);
                out.writeObject(null);
                out.close();
                file.close();
            }
            catch (IOException ex) {
                System.out.println(ex + "occured");
            }
        }
        return tempV;
    }
    // replace entry from the hashMap as well as update the log file
    @Override
    public V replace(K key, V value){
        V newKey=null;
        File fileCker = new File(logname);
        // if entry exists
        if(this.get(key)!=null){
            try {
                if(!fileCker.exists())
                    fileCker.createNewFile();
                FileOutputStream file = new FileOutputStream(logname);
                ObjectOutputStream out = new ObjectOutputStream(file);
                out.writeObject(key);
                out.writeObject(value);
                out.close();
                file.close();
                map.replace(key, value);
            }
            catch (IOException ex) {
                System.out.println(ex + "occured");
            }
        }
        return newKey;
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return map.get(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        map.putAll(m);
    }

    @Override
    public Set<K> keySet() {
        return map.keySet();
    }

    @Override
    public Collection<V> values() {
        return map.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return map.entrySet();
    }

    @Override
    public V getOrDefault(Object key, V defaultValue) {
        return Map.super.getOrDefault(key, defaultValue); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> action) {
        Map.super.forEach(action); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        Map.super.replaceAll(function); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public V putIfAbsent(K key, V value) {
        return Map.super.putIfAbsent(key, value); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean remove(Object key, Object value) {
        return Map.super.remove(key, value); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        return Map.super.replace(key, oldValue, newValue); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        return Map.super.computeIfAbsent(key, mappingFunction); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        return Map.super.computeIfPresent(key, remappingFunction); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        return Map.super.compute(key, remappingFunction); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        return Map.super.merge(key, value, remappingFunction); //To change body of generated methods, choose Tools | Templates.
    }
}
