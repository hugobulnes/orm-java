package org.hugobulnes.orm.query;

public class Filter<T>{

    private String column;
    private T value;
    private String condition;

    /**
     * Creates a filter to use in a query
     * @param column String - the column to use in the condition clause
     */ 
    public Filter(String column){
        this.column = column;
    }   

    /**
     * Change the mode of the filter to make condition equal to a value
     * @param value T
     */ 
    public void equalTo(T value) throws Exception{
        this.value = value;
        this.condition = "=";
    } 

    /**
     * Returns the value provided to the Filter
     * @return T
     */
    public T getValue(){
        return this.value;
    }

    /**
     * Gets the data type of the value
     * @return Class<T>
     */
    public Class<T> getType() throws Exception{
        Class type = null;
        if(this.value != null){
            type = this.value.getClass();
        }
        return type;
    }

    /**
     * Compose the filter accordint to the condition Mode and return the 
     * string part of the filter to use in a query
     * @return String
     */ 
    public String compose(){
        return this.column + " = ?";

    }

}
