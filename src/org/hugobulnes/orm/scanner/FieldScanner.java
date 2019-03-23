package org.hugobulnes.orm.scanner;

import org.hugobulnes.orm.annotations.Column;
import org.hugobulnes.orm.annotations.Id;
import org.hugobulnes.orm.properties.FieldProperty;
import org.hugobulnes.orm.properties.ClassProperty;
import org.hugobulnes.orm.properties.Property;
import java.lang.reflect.Field;
import java.lang.annotation.Annotation;
import java.util.Map;

public class FieldScanner extends Scanner{

    /**
     * Default Constructor
     * @param model Class
     */ 
    public FieldScanner(){
    }

    /**
     * Constructor expecting a callback Scanner
     * @param next Scanner
     */
    public FieldScanner(Scanner next){
        if(next != null) this.link(next);
    }

    /**
     * Override method scan. It will loop thru model class field properties 
     * looking for data annotations and stored on the hasmap reference provided
     * @param mapping Map<String, Property>
     * @return boolean
     */ 
    public boolean scan(Class<?> model, Map<String, Property> mapping) 
        throws Exception{

        if(mapping.containsKey("table")){

            for(Field field : model.getDeclaredFields()){
                
                Column annotation = field.getAnnotation(Column.class);

                if(annotation != null){

                    //create property
                    FieldProperty fieldProperty = new FieldProperty(field);

                    mapping.put(annotation.value(), fieldProperty);
                    mapping.put(field.getName().toLowerCase(), fieldProperty);
                    
                    //register to table
                    ClassProperty table = (ClassProperty) mapping.get("table");
                    table.addFieldToList(fieldProperty);


                    if(mapping.containsKey("PK") == false){
                        Id primaryKeyAnnotation = 
                            field.getAnnotation(Id.class);
                        if(primaryKeyAnnotation != null){
                            mapping.put("PK", fieldProperty);
                        }
                    }
                }
            }

        //call next
        return this.checkNext(model, mapping);

        }else{
            throw new Exception("Table annotation not found on entity model");
        }

    }


}
