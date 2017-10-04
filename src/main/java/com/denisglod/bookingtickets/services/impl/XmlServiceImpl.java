package com.denisglod.bookingtickets.services.impl;

import com.denisglod.bookingtickets.beans.FilmBean;
import com.denisglod.bookingtickets.beans.ScheduleBean;
import com.denisglod.bookingtickets.dao.factory.DAOFactory;
import com.denisglod.bookingtickets.services.XmlService;

import java.util.List;

public class XmlServiceImpl implements XmlService {
    @Override
    public List<ScheduleBean> getSchedule() {
        return DAOFactory.getFactory().getXmlDAO().getSchedule();
    }

    @Override
    public FilmBean reservation(FilmBean filmBean) {
        return DAOFactory.getFactory().getXmlDAO().reservation(filmBean);
    }

    @Override
    public Boolean deleteReservation(FilmBean filmBean) {
        return DAOFactory.getFactory().getXmlDAO().deleteReservation(filmBean);
    }

    @Override
    public FilmBean orderInformation(FilmBean filmBean) {
        return DAOFactory.getFactory().getXmlDAO().getInfoReservation(filmBean);
    }
}
