package com.denisglod.bookingtickets.dao.factory;

import com.denisglod.bookingtickets.dao.XmlDAO;
import com.denisglod.bookingtickets.dao.xml.DBXml;

public class XmlDAOFactory extends DAOFactory {
    @Override
    public XmlDAO getXmlDAO() {
        return new DBXml();
    }
}
