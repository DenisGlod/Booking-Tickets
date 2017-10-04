package com.denisglod.bookingtickets.dao.xml;

import com.denisglod.bookingtickets.beans.FilmBean;
import com.denisglod.bookingtickets.beans.ScheduleBean;
import com.denisglod.bookingtickets.dao.XmlDAO;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DBXml implements XmlDAO {
    @Override
    public List<ScheduleBean> getSchedule() {
        List<ScheduleBean> result = new ArrayList<>();
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            NodeList films = documentBuilder.parse("schedule.xml").getDocumentElement().getChildNodes();
            for (int i = 0; i < films.getLength(); i++) {
                ScheduleBean filmBean = new ScheduleBean();
                if (films.item(i).hasAttributes()) {
                    String filmName = films.item(i).getAttributes().item(0).getTextContent();
                    filmBean.setName(filmName);
                    NodeList data = films.item(i).getChildNodes();
                    ArrayList<String> dateList = new ArrayList<>();
                    ArrayList<HashMap<Integer, Boolean>> seats = new ArrayList<>();
                    for (int j = 0; j < data.getLength(); j++) {
                        if (data.item(j).hasAttributes()) {
                            String dateTime = data.item(j).getAttributes().item(0).getTextContent();
                            dateList.add(dateTime);
                            NodeList date = data.item(j).getChildNodes();
                            HashMap<Integer, Boolean> seatList = new HashMap<>();
                            for (int k = 0; k < date.getLength(); k++) {
                                if (date.item(k).hasAttributes()) {
                                    String number = date.item(k).getAttributes().item(0).getTextContent();
                                    String status = date.item(k).getAttributes().item(1).getTextContent();
                                    seatList.put(Integer.parseInt(number), Boolean.parseBoolean(status));
                                }
                            }
                            seats.add(seatList);
                        }
                    }
                    filmBean.setSeats(seats);
                    filmBean.setData(dateList);
                    result.add(filmBean);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public FilmBean reservation(FilmBean filmBean) {
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document schedule = documentBuilder.parse("schedule.xml");
            NodeList films = schedule.getDocumentElement().getChildNodes();
            for (int i = 0; i < films.getLength(); i++) {
                if (films.item(i).hasAttributes() && filmBean.getName().equals(films.item(i).getAttributes().item(0).getTextContent())) {
                    NodeList data = films.item(i).getChildNodes();
                    for (int j = 0; j < data.getLength(); j++) {
                        if (data.item(j).hasAttributes() && filmBean.getDate().equals(data.item(j).getAttributes().item(0).getTextContent())) {
                            NodeList date = data.item(j).getChildNodes();
                            for (int k = 0; k < date.getLength(); k++) {
                                if (date.item(k).hasAttributes()) {
                                    if (filmBean.getSeat() == Integer.parseInt(date.item(k).getAttributes().item(0).getTextContent())
                                            && Boolean.parseBoolean(date.item(k).getAttributes().item(1).getTextContent())) {
                                        date.item(k).getAttributes().item(1).setTextContent("false");
                                        Transformer transformer = TransformerFactory.newInstance().newTransformer();
                                        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                                        transformer.transform(new DOMSource(schedule), new StreamResult(new File("schedule.xml")));
                                        Document orders = documentBuilder.parse("orders.xml");
                                        Element root = orders.getDocumentElement();
                                        Element order = orders.createElement("order");
                                        order.setAttribute("key", String.valueOf(filmBean.getKey()));
                                        order.setAttribute("name", filmBean.getName());
                                        order.setAttribute("date", filmBean.getDate());
                                        order.setAttribute("seat", String.valueOf(filmBean.getSeat()));
                                        root.appendChild(order);
                                        transformer.transform(new DOMSource(orders), new StreamResult(new File("orders.xml")));
                                        filmBean.setIncorrectDate(false);
                                        filmBean.setIncorrectName(false);
                                        filmBean.setIncorrectSeat(false);
                                        return filmBean;
                                    } else {
                                        filmBean.setIncorrectSeat(true);
                                    }
                                }
                            }
                        } else {
                            filmBean.setIncorrectDate(true);
                        }
                    }
                } else {
                    filmBean.setIncorrectName(true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filmBean;
    }

    @Override
    public Boolean deleteReservation(FilmBean filmBean) {
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = documentBuilder.parse("orders.xml");
            NodeList orders = doc.getDocumentElement().getChildNodes();
            for (int i = 0; i < orders.getLength(); i++) {
                if (orders.item(i).hasAttributes()) {
                    if (filmBean.getKey() == Integer.parseInt(orders.item(i).getAttributes().item(1).getTextContent())) {
                        filmBean.setDate(orders.item(i).getAttributes().item(0).getTextContent());
                        filmBean.setName(orders.item(i).getAttributes().item(2).getTextContent());
                        filmBean.setSeat(Integer.parseInt(orders.item(i).getAttributes().item(3).getTextContent()));
                        Node parent = orders.item(i).getParentNode();
                        parent.removeChild(orders.item(i));
                        Transformer transformer = TransformerFactory.newInstance().newTransformer();
                        transformer.transform(new DOMSource(doc), new StreamResult(new File("orders.xml")));

                        Document schedule = documentBuilder.parse("schedule.xml");
                        NodeList list = schedule.getDocumentElement().getChildNodes();
                        for (int j = 0; j < list.getLength(); j++) {
                            if (list.item(j).hasAttributes() && list.item(j).getAttributes().getNamedItem("name").getTextContent().equals(filmBean.getName())) {
                                NodeList date = list.item(j).getChildNodes();
                                for (int k = 0; k < date.getLength(); k++) {
                                    if (date.item(k).hasAttributes() && date.item(k).getAttributes().getNamedItem("value").getTextContent().equals(filmBean.getDate())) {
                                        NodeList seats = date.item(k).getChildNodes();
                                        for (int l = 0; l < seats.getLength(); l++) {
                                            if (seats.item(l).hasAttributes() && Integer.parseInt(seats.item(k).getAttributes().getNamedItem("number").getTextContent()) == filmBean.getSeat()) {
                                                seats.item(l).getAttributes().getNamedItem("status").setTextContent("true");
                                                transformer.transform(new DOMSource(schedule), new StreamResult(new File("schedule.xml")));
                                                return true;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public FilmBean getInfoReservation(FilmBean filmBean) {
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            NodeList orders = documentBuilder.parse("orders.xml").getDocumentElement().getChildNodes();
            for (int i = 0; i < orders.getLength(); i++) {
                if (orders.item(i).hasAttributes()) {
                    if (filmBean.getKey() == Integer.parseInt(orders.item(i).getAttributes().item(1).getTextContent())) {
                        filmBean.setDate(orders.item(i).getAttributes().item(0).getTextContent());
                        filmBean.setName(orders.item(i).getAttributes().item(2).getTextContent());
                        filmBean.setSeat(Integer.parseInt(orders.item(i).getAttributes().item(3).getTextContent()));
                        filmBean.setIncorrectInfo(false);
                        return filmBean;
                    } else {
                        filmBean.setIncorrectInfo(true);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filmBean;
    }
}
