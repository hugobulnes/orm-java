package com.hugobulnes.jdb;

import com.hugobulnes.jdb.properties.Property;
import com.hugobulnes.jdb.properties.FieldProperty;
import com.hugobulnes.jdb.properties.ClassProperty;
import com.hugobulnes.jdb.scanner.*;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * @TODO implement a data parser to allow enums
 */ 
public class Model<Entity>{

    private Map<String, Property> mapping;
    private Class<Entity> model;

    public Model(Class<Entity> model) throws Exception{
        this.model = model;
        this.mapping = new HashMap<>();

        ClassScanner scanner = 
            new ClassScanner(new FieldScanner(new MethodScanner()));

        scanner.scan(model, this.mapping);
    }
    
    /**
     * Check if the entity has the key value set
     * @param Entity entity
     * @return boolean
     */ 
    public boolean isKeySet(Entity entity) throws Exception{
       if(this.mapping.containsKey("PK")){
           FieldProperty field = (FieldProperty) this.mapping.get("PK");
           int key = (int)field.getValue(entity);
           if(key == 0){
                return false;   
           }
           return true;
       }else{
           throw new Error("Entity does not contain a key value");
       }
        
    }

    /**
     * Collect values from entity and returned as an Array of Objects
     * @param entity Entity
     * @return Object[]
     */
    public Object[] toArray(Entity entity) throws Exception{
        if(this.mapping.containsKey("table")){
            ClassProperty property = (ClassProperty) this.mapping.get("table");
            
            FieldProperty[] fields = property.getFieldArray();        
            
            //array of values            
            Object[] row = new Object[fields.length];

            for(int i =0 ; i < fields.length; i++){

                row[i] = fields[i].getValue(entity);
            } 
            return row;
        }else{
           throw new Error("Entity needed the table mapping");
        }
    }

    /**
     * Collect values from entity and returned as an Array of Objects
     * @param entity Entity
     * @return Object[]
     */
    public String[] columnsWithValues(Entity entity) throws Exception{
        if(this.mapping.containsKey("table")){
            ClassProperty property = (ClassProperty) this.mapping.get("table");
            
            FieldProperty[] fields = property.getFieldArray();        
            
            //array of values            
            ArrayList<String> columnsWValues = new ArrayList<>();; 

            for(int i =0 ; i < fields.length; i++){
                if(fields[i].getValue(entity) != null){
                    columnsWValues.add(fields[i].getColumn());
                }
            } 
            return columnsWValues.toArray(new String[columnsWValues.size()]);
        }else{
           throw new Error("Entity needed the table mapping");
        }
    }

    /**
     * Set the property value of an entity that contains the key annotation.
     * The entity need the key annotation or it will throw an error.
     * @param entity T
     * @param key Object
     */
   public void setKey(Entity entity, Object value) throws Exception{
       if(this.mapping.containsKey("PK")){
           FieldProperty field = (FieldProperty) this.mapping.get("PK");
           field.setValue(entity, value);
       }else{
           throw new Error("Entity does not contain a key value");
       }
   } 

    /**
     * Get the column name of the primary key property.
     * @return String
     */
   public String getKeyColumn() throws Exception{
       if(this.mapping.containsKey("PK")){
           FieldProperty field = (FieldProperty) this.mapping.get("PK");
           return field.getColumn();
       }else{
           throw new Error("Entity does not contain a key value");
       }
   } 

    /**
     * Get the value of the column name with primary key property.
     * @param entity T
     * @return String
     */
   public Object getKeyValue(Entity entity) throws Exception{
       if(this.mapping.containsKey("PK")){
           FieldProperty field = (FieldProperty) this.mapping.get("PK");
           return field.getValue(entity);
       }else{
           throw new Error("Entity does not contain a key value");
       }
   } 

    /**
     * Get the value of column property of the entity
     * @param entity T
     * @param column String
     * @return Object
     */
   public Object getValue(Entity entity, String column) throws Exception{
       if(this.mapping.containsKey(column)){
           FieldProperty field = (FieldProperty) this.mapping.get(column);
           return field.getValue(entity);
       }else{
           throw new Error("Entity does not contain the column value");
       }
   } 

    /**
    * check if column property from entity has value
    * @param entity T
    * @param column String
    * @return boolean
    */
   public boolean hasValue(Entity entity, String column) throws Exception{
       if(this.mapping.containsKey(column)){
           FieldProperty field = (FieldProperty) this.mapping.get(column);
           return field.getValue(entity) != null;
       }else{
           throw new Error("Entity does not contain the column value");
       }
   } 

    /**
     * Get table column names as an array of Strings
     * @return String[]
     */ 
    public String[] columnsToArray() throws Exception{
        if(this.mapping.containsKey("table")){
            ClassProperty property = (ClassProperty) this.mapping.get("table");
            return property.getColumnArray();
        }else{
           throw new Error("Entity needed the table mapping");
        }
    }

    /**
     * Get the entity field data type provided the column name of that 
     * property.
     * @param column String
     * @return Class<?>
     */ 
    public Class<?> fieldTypeFromColumn(String column) throws Exception{
        if(this.mapping.containsKey(column)){
            FieldProperty property = (FieldProperty) this.mapping.get(column);
            return property.getType();
        }else{
            throw new Exception("column name is not on entity mapping " +
                   column );
        }
    }


    /**
     * Create an instance of the Entity model by providing a mapping array of 
     * values 
     * @param values Map<String, Object>
     * @return Entity
     */ 
    public Entity create(Map<String, Object> values) throws Exception{

       Entity entity = this.model.newInstance();

        for(String key : values.keySet()){
            if(this.mapping.containsKey(key)){
                FieldProperty field = (FieldProperty) this.mapping.get(key);
                field.setValue(entity, values.get(key));
            }
        }
        return entity;
    }

    /**
     * Set the value of an entity provided the column name of the entity field
     * @param entity Entity
     * @param column String
     * @param value Object
     */
    public void setValue(Entity entity, String column, Object value) 
        throws Exception{
        if(this.mapping.containsKey(column)){
            FieldProperty field = (FieldProperty) this.mapping.get(column);
            field.setValue(entity, value);

        }else{
            throw new Exception("column name is not on entity mapping " +
                   column + " with value " + value  );
        }
        
    } 

    
    /**
     * Create an empty entity from model
     * @return Entity
     */ 
    public Entity create() throws Exception{
        Entity entity = this.model.newInstance();
        return entity;
    }

    /**
     * Returns the table name specified in the model entity
     * @return String
     */
    public String getTable() throws Exception{
        if(this.mapping.containsKey("table")){
            ClassProperty property = (ClassProperty) this.mapping.get("table");
            return property.getTableName();
        }else{
            throw new Exception("table not declared");
        }
    }

}
