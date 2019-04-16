package com.hugobulnes.jdb.properties;

import com.hugobulnes.jdb.annotations.Column;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.annotation.Annotation;

public class FieldProperty extends Property{

    private Field field;
    private Method setter;
    private Method getter;

    public FieldProperty(Field field){
        this.field = field;
    }

    /**
     * Returns database column name declared on this field
     * @return String
     */ 
    public String getColumn() throws Exception{
        Column annotation = this.field.getAnnotation(Column.class);
        return annotation.value(); 
    } 

    /**
     * Set the setter method for this field
     * @param setter Method
     */ 
    public void setSetter(Method setter){
        this.setter = setter;
    }

    /**
     * Set the getter method for this field
     * @param getter Method
     */ 
    public void setGetter(Method getter){
        this.getter = getter;
    }

    /**
     * Return the field datatype 
     * @return Class<?>
     */ 
    public Class<?> getType() throws Exception{
        return this.field.getType();
    }

    public <T> Object getValue(T entity) throws Exception{
        return this.getter.invoke(entity); 
    }

    public <T> void setValue(T entity, Object value) throws Exception{
        this.setter.invoke(entity, value); 
    }

    /**
     * Debug method
     */ 
    public String toString(){
        return this.setter.getName() + " " + this.getter.getName();
    }


}
