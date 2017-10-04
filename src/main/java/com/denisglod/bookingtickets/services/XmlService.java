package com.denisglod.bookingtickets.services;

import com.denisglod.bookingtickets.beans.FilmBean;
import com.denisglod.bookingtickets.beans.ScheduleBean;

import java.util.List;

public interface XmlService {
    List<ScheduleBean> getSchedule();

    FilmBean reservation(FilmBean filmBean);

    Boolean deleteReservation(FilmBean filmBean);

    FilmBean orderInformation(FilmBean filmBean);
}
