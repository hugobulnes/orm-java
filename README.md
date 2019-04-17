# orm-java

# Description

This is a simple mini-framework to work with relational databases that maps
custom classes to tables and objects to rows using a small set of annotations 
and the reflection api. 

> Custom classes must have setters and getter for the class variables for the
framework to work.

# How to use it

To connect to the database the host, username and password of the database must
be provided to the singleton class __DatabaseSession__ on the `connect()` 
static class.

    `DatabaseSession session = DatabaseSession.connect(host, username, password);`

The framework uses custom _Collections_ classes called  __Query Objects__ to 
interact with the database where the custom class must be provided as a generic
value. 

__InsertQuery\<Object\>__

This query object is used to insert new rows into the database table. Because
the query object extends a __Collection class__ you can add new objects to 
the query by using the `add()` method.

    ~~~~
    InsertQuery<Student> query = new InsertQuery(Student.class);
    
    query.add(new Student("John McClane", "johnmcclane2000", "mypassword"));

    query.execute(session);

    ~~~~

__SelectQuery\<Object\>__

This query object is used to retrieve data from the database. Once the query is 
executed the retrieved objects are stored on the query instance. To retrieve 
an object from the query object the `get(index)` must be called.

    ~~~~
    SelectQuery<Student> query = new SelectQuery(Student.class);

    query.execute(session);

    System.out.println(query.get(0));

    ~~~~

To retrieve an specific object from the database the framework implements 
a __Filter__ class where the column name and the specific value can be provided

    
    ~~~~
    SelectQuery<Student> query = new SelectQuery(Student.class);

    Filter<String> filter = new Filter("userName");
    filter.equalTo("johnmcclane2000");

    query.filterBy(filter);

    query.execute(session);

    System.out.println(query.get(0));

    ~~~~

__UpdateQuery\<Object\>__

This query object is used to update information of an object from the database.
Any object provided to the query will be updated to the values that the object
has.

    ~~~~
    UpdateQuery<Student> query = new UpdateQuery(Student.class);    

    query.add(new Student("John McClane", "johnmcclane2000", "mypasswordMoreSecure"));

    query.execute(session);

    ~~~~

__DeleteQuery\<Object\>__

This query object is used to delete object from the database (rows). The __Filter__
object can be used to specify the object from the database to delete.

    ~~~~
    DeleteQuery<Student> query = new DeleteQuery(Student.class);

    Filter<String> filter = new Filter("userName");
    filter.equalTo("McClane");

    query.filterBy(filter);

    query.execute(session);

    ~~~~





