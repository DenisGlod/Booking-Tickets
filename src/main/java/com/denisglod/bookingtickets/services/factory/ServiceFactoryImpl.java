package com.denisglod.bookingtickets.services.factory;

import com.denisglod.bookingtickets.services.XmlService;
import com.denisglod.bookingtickets.services.impl.XmlServiceImpl;

public class ServiceFactoryImpl extends ServiceFactory {
    @Override
    public XmlService getXmlService() {
        return new XmlServiceImpl();
    }
}
