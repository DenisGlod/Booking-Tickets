package com.denisglod.bookingtickets.dao;

import com.denisglod.bookingtickets.beans.FilmBean;
import com.denisglod.bookingtickets.beans.ScheduleBean;

import java.util.List;

public interface XmlDAO {
    List<ScheduleBean> getSchedule();

    FilmBean reservation(FilmBean filmBean);

    Boolean deleteReservation(FilmBean filmBean);

    FilmBean getInfoReservation(FilmBean filmBean);
}
