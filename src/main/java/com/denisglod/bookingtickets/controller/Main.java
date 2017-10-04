package com.denisglod.bookingtickets.controller;

import com.denisglod.bookingtickets.beans.FilmBean;
import com.denisglod.bookingtickets.beans.ScheduleBean;
import com.denisglod.bookingtickets.services.factory.ServiceFactory;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Scanner scanner;
            while (true) {
                System.out.println("----- Кинотеатр -----");
                System.out.println("[1] - Расписание");
                System.out.println("[2] - Забронировать одно или несколько мест на сеансе");
                System.out.println("[3] - Отменить бронь");
                System.out.println("[4] - Получить информацию по номеру заказа");
                System.out.println("[0] - Выход");
                System.out.print(">>> ");
                scanner = new Scanner(System.in);
                switch (scanner.nextInt()) {
                    case 1:
                        System.out.println("///////////////////////// Расписание /////////////////////////////");
                        System.out.println("* места помеченные крассным забронированы");
                        List<ScheduleBean> schedule = ServiceFactory.getFactory().getXmlService().getSchedule();
                        schedule.forEach(System.out::print);
                        break;
                    case 2:
                        FilmBean filmBean = new FilmBean();
                        System.out.println("///////// Забронировать одно или несколько мест на сеансе /////////");
                        System.out.print("Введите название фильма (пример: Железный человек) >> ");
                        scanner = new Scanner(System.in);
                        filmBean.setName(scanner.nextLine().trim());
                        System.out.print("Введите дату и сеанс фильма (пример: 01.04.2017 12:00 - 14:00) >> ");
                        scanner = new Scanner(System.in);
                        filmBean.setDate(scanner.nextLine().trim());
                        System.out.print("Введите место (пример: 6) >> ");
                        scanner = new Scanner(System.in);
                        filmBean.setSeat(scanner.nextInt());
                        filmBean.setKey(filmBean.hashCode());
                        FilmBean result = ServiceFactory.getFactory().getXmlService().reservation(filmBean);
                        if (!result.isIncorrectName() && !result.isIncorrectDate() && !result.isIncorrectSeat()) {
                            System.out.println((char) 27 + "[32mМесто успешно забронировано!" + (char) 27 + "[0m");
                            System.out.println("Номер заказа " + filmBean.getKey());
                        } else if (result.isIncorrectSeat()) {
                            System.out.println((char) 27 + "[31mОшибка! Такое место забронировано либо не существует" + (char) 27 + "[0m");
                        } else if (result.isIncorrectDate()) {
                            System.out.println((char) 27 + "[31mОшибка! Некорректная дата фильма" + (char) 27 + "[0m");
                        } else if (result.isIncorrectName()) {
                            System.out.println((char) 27 + "[31mОшибка! Некорректное название фильма" + (char) 27 + "[0m");
                        }
                        break;
                    case 3:
                        FilmBean removeOrder = new FilmBean();
                        System.out.println("///////// Отменить бронь /////////");
                        scanner = new Scanner(System.in);
                        System.out.print("Введите номер заказа >> ");
                        removeOrder.setKey(scanner.nextInt());
                        Boolean removeStatus = ServiceFactory.getFactory().getXmlService().deleteReservation(removeOrder);
                        if (removeStatus) {
                            System.out.println((char) 27 + "[32mБронь успешно снята" + (char) 27 + "[0m");
                        } else {
                            System.out.println((char) 27 + "[31mОшибка! Неверный номер заказа" + (char) 27 + "[0m");
                        }
                        break;
                    case 4:
                        FilmBean info = new FilmBean();
                        System.out.println("///////// Получить информацию по номеру заказа /////////");
                        scanner = new Scanner(System.in);
                        System.out.print("Введите номер заказа >> ");
                        info.setKey(scanner.nextInt());
                        FilmBean resultInfo = ServiceFactory.getFactory().getXmlService().orderInformation(info);
                        if (!resultInfo.isIncorrectInfo()) {
                            System.out.println("Номер заказа: " + resultInfo.getKey());
                            System.out.println("Навание фильма: " + resultInfo.getName());
                            System.out.println("Дата и время: " + resultInfo.getDate());
                            System.out.println("Номер места: " + resultInfo.getSeat());
                        } else {
                            System.out.println((char) 27 + "[31mОшибка! Неверный номер заказа" + (char) 27 + "[0m");
                        }
                        break;
                    case 0:
                        System.out.println("Завершение работы программы ...");
                        return;
                    default:
                        System.out.println("Ошибка! Такой операции несуществует");
                }
                System.out.println("//////////////////////////////////////////////////////");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}