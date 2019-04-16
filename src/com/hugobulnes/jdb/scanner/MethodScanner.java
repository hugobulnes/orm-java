package com.hugobulnes.jdb.scanner;

import com.hugobulnes.jdb.properties.FieldProperty;
import com.hugobulnes.jdb.properties.Property;
import java.lang.reflect.Method;
import java.util.Map;

public class MethodScanner extends Scanner{

    /**
     * Default Constructor
     * @param model Class
     */ 
    public MethodScanner(){
    }

    /**
     * Constructor expecting a callback Scanner
     * @param next Scanner
     */
    public MethodScanner(Scanner next){
        if(next != null) this.link(next);
    }

    /**
     * Override method scan. It will loop thru model class methods to find 
     * model class fields setters and getters and stored on the hasmap 
     * reference provided
     * @param mapping Map<String, Property>
     * @return boolean
     */ 
    public boolean scan(Class<?> model, Map<String, Property> mappings) 
        throws Exception{

        for(Method method: model.getDeclaredMethods()){

            String methodName = method.getName().toLowerCase();

            if(methodName.length() > 3){
                //check if set or get
                if(mappings.containsKey(methodName.substring(3))){
                                              
                    String prefix = methodName.substring(0, 3);    
                    
                    FieldProperty field = 
                        (FieldProperty) mappings.get(methodName.substring(3));
                    
                    if(prefix.equals("set")){
                    
                        field.setSetter(method);

                    }else if(prefix.equals("get")){
                        
                        field.setGetter(method);

                    }
                    
                }
            }
        }

        //call next
        return this.checkNext(model, mappings);
    }
}
