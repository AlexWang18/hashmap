
import java.util.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author alexw
 */
public class HashMap<K,V>{ //hashmap implementation using generics
    
    private List<Pair<K,V>>[] arr;
    private int firstFreeIndex;
    
    public HashMap(){
        this.arr = new List[32]; //create an array of Lists  
        firstFreeIndex = 0;
    }
    @Override
    public String toString(){
        return Arrays.deepToString(arr);
    }
    public void put(K key, V value){ //add growing
        List<Pair<K,V>> valuesAtHashIndex = getListBasedOnKey(key); 
        
        if(this.firstFreeIndex / this.arr.length > .75){ //if the num of Pairs are more than 75% of our storage capacity
            grow();
        }
         int index = getIndexOfKeyInList(valuesAtHashIndex,key);
         if(index<0){
            valuesAtHashIndex.add(new Pair(key,value)); //create new pair instance, 
            this.firstFreeIndex++; 
         }
         else{
             valuesAtHashIndex.get(index).setValue(value); //reset with a new value at that Pair
         }
         
    }
    
    private void grow() {
        int newsize = arr.length + arr.length;
        
        List<Pair<K,V>>[] temparr = new List[newsize];
        for(int i = 0; i< arr.length;i++){
            copy(temparr, i); //copy each Pair in the list at the olds arrays index
        }
        this.arr = temparr; //set reference to larger container
    }
    private void copy(List<Pair<K, V>>[] temparr, int hashVal) { // pairs hash value are recalculated to evenly distribute across array  
        for (int i = 0; i < this.arr[hashVal].size(); i++) { //for each list at that array's index
            Pair<K, V> pair = this.arr[hashVal].get(i); 

            int newHashValue = Math.abs(pair.getKey().hashCode() % temparr.length); //calc
            if(temparr[newHashValue] == null) {
                temparr[newHashValue] = new ArrayList<>();
            }

        temparr[newHashValue].add(pair);
        }
    }
    public V get(K key){
        List<Pair<K,V>> valuesAtHashIndex = getListBasedOnKey(key); 
        if(valuesAtHashIndex.isEmpty()){
            return null;
        }
         int index = getIndexOfKeyInList(valuesAtHashIndex,key);
         if(index<0){
             return null;
         }
         return valuesAtHashIndex.get(index).getValue(); //send the pairs value at that lists index
    }
    
    public V remove(K key){
        
        List<Pair<K,V>> valuesAtHashIndex = getListBasedOnKey(key);
        
        if(valuesAtHashIndex.isEmpty()){
            return null;
        }
        int index = getIndexOfKeyInList(valuesAtHashIndex, key);
        
        if (index < 0) {
            return null;
        }
        Pair<K, V> pair = valuesAtHashIndex.get(index);
        valuesAtHashIndex.remove(pair);
        return pair.getValue(); 
    }
    
    private List<Pair<K,V>> getListBasedOnKey(K key){
        
        int hashvalue = Math.abs(key.hashCode() % this.arr.length);
        
        if(arr[hashvalue] == null){ //if no such hash value exists create a new arraylist there
            arr[hashvalue] = new ArrayList<>();
        }
        List<Pair<K,V>> valuesAtIndex = arr[hashvalue]; //could also just return the arr[]
        
        return valuesAtIndex;
    }
    private int getIndexOfKeyInList(List<Pair<K,V>> valuesAtIndex, K key){
        
        for(int i = 0; i < valuesAtIndex.size();i++){
            if(valuesAtIndex.get(i).getKey().equals(key)){ //get pair in list for that iterator, get its key and check
                return i;
            }
        }
        return -1; //not found
    }


}
