package org.hugobulnes.orm.query;

import org.hugobulnes.orm.Model;
import org.hugobulnes.orm.DatabaseSession;

public interface Query<T>{
    public String compose() throws Exception;
    public Model<T> getModel() throws Exception;
    public boolean execute(DatabaseSession session) throws Exception;
}
