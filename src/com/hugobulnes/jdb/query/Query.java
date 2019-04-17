package com.hugobulnes.jdb.query;

import com.hugobulnes.jdb.Model;
import com.hugobulnes.jdb.DatabaseSession;

public interface Query<T>{
    public String compose() throws Exception;
    public Model<T> getModel() throws Exception;
    public boolean execute(DatabaseSession session);
}
