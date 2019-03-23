package org.hugobulnes.orm.properties;

import org.hugobulnes.orm.properties.FieldProperty;
import java.util.ArrayList;

public class ClassProperty extends Property{

    private String tableName;
    private ArrayList<FieldProperty> fields;

    public ClassProperty(String tableName){
        this.tableName = tableName;
        this.fields = new ArrayList<>();
    }


    /**
     * Get the name of the table specified for the entity
     * @return String
     */ 
    public String getTableName(){
        return this.tableName;
    }

    /**
     * Get an array of Field Properties from the entity
     * @return FieldProperty[]
     */
    public FieldProperty[] getFieldArray() throws Exception{

        FieldProperty[] fieldProperties = 
            this.fields.toArray(new FieldProperty[0]);
        return fieldProperties;
    }

    /**
     * @return String[]
     */
    public void addFieldToList(FieldProperty field) throws Exception{
        this.fields.add(field);
    }  

    /**
     * Get an array of column Names from the entity table
     * @return String[]
     */
    public String[] getColumnArray() throws Exception{
        FieldProperty[] fieldProperty = 
            this.fields.toArray(new FieldProperty[0]);

        String[] columns = new String[fieldProperty.length];
        
        for(int i = 0; i < fieldProperty.length; i++){
            columns[i] = fieldProperty[i].getColumn();
        }

        return columns;

    }  


}
