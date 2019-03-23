package org.hugobulnes.orm.scanner;

import org.hugobulnes.orm.annotations.Table;
import org.hugobulnes.orm.properties.ClassProperty;
import org.hugobulnes.orm.properties.Property;
import java.lang.annotation.Annotation;
import java.util.Map;

public class ClassScanner extends Scanner{

    /**
     * Default Constructor
     * @param model Class
     */ 
    public ClassScanner(){
    }

    /**
     * Constructor expecting a callback Scanner
     * @param next Scanner
     */
    public ClassScanner(Scanner next){
        if(next != null) this.link(next);
    }

    /**
     * Override method scan. It will look for data annotations on the model 
     * class level and stored on the hashmap reference.
     * @param mapping Map<String, Property> <reference>
     * @return boolean
     */ 
    public boolean scan(Class<?> model, Map<String, Property> mapping) 
        throws Exception{

        //get table annotation from model
        Table annotationTable = (Table) model.getAnnotation(Table.class);
        
        if(annotationTable != null){
            //create Table Property
            ClassProperty table = new ClassProperty(annotationTable.value());

            mapping.put("table", table);

            //call next
            return this.checkNext(model, mapping);
        }else{
            throw new Exception("Table annotation not found on entity model");
        }
    }

}
