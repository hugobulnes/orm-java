package org.hugobulnes.orm.scanner;

import org.hugobulnes.orm.properties.Property;
import java.lang.reflect.Method;
import java.lang.reflect.Field;
import java.util.Map;

abstract public class Scanner{

    private Scanner next;

    /**
     * Default Constructor
     */ 
    public Scanner(){
    }


    /**
     * Will link another scanner to the callback stack
     * @param next Scanner
     * @return Scanner
     */ 
    public Scanner link(Scanner next){
        this.next = next;
        return next;
    }
    
    /**
     * Abstract method use to perform a scan process on the model class
     * @param mapping Map<String, Property>
     * @return boolean
     */ 
    public abstract boolean scan(
            Class<?> model, Map<String, Property> mapping) throws Exception;

    /**
     * It will check if there is another Scanner linked to the callback stack
     * and perform the scan function from that Scanner
     * @param mapping Map<String, Property>
     * @return boolean
     */  
    protected boolean checkNext(
            Class<?> model, Map<String, Property> mapping) throws Exception{
        if(next == null){
            return true;
        }
        return next.scan(model, mapping);
    }

}
