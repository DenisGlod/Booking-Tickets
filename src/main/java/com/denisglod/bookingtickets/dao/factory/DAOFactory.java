package com.denisglod.bookingtickets.dao.factory;

import com.denisglod.bookingtickets.dao.XmlDAO;

public abstract class DAOFactory {
    public abstract XmlDAO getXmlDAO();

    public static DAOFactory getFactory() {
        return getFactory(StorageTypes.XML);
    }

    private static DAOFactory getFactory(StorageTypes type) {
        switch (type) {
            case XML:
                return new XmlDAOFactory();
            default:
                throw new UnsupportedOperationException();
        }
    }

}
