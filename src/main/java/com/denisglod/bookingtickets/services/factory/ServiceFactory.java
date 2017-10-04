package com.denisglod.bookingtickets.services.factory;

import com.denisglod.bookingtickets.services.XmlService;

public abstract class ServiceFactory {
    public abstract XmlService getXmlService();

    public static ServiceFactory getFactory() {
        return new ServiceFactoryImpl();
    }
}
