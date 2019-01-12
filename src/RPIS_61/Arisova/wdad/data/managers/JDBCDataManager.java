package RPIS_61.Arisova.wdad.data.managers;

import RPIS_61.Arisova.wdad.data.storage.DataSourceFactory;
import RPIS_61.Arisova.wdad.learn.xml.Item;
import RPIS_61.Arisova.wdad.learn.xml.Officiant;
import RPIS_61.Arisova.wdad.learn.xml.Order;

import javax.sql.DataSource;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class JDBCDataManager implements DataManager {
    DataSource dataSource = DataSourceFactory.createDataSource();
    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;

    @Override
    public int earningsTotal(Officiant officiant, Calendar calendar) throws RemoteException {
        int earningsTotal = 0;

        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT SUM( items.cost * items_orders.quantity ) AS earningsTotal FROM orders JOIN officiants ON ( orders.officiant_id = officiants.id ) JOIN items_orders ON ( orders.id = items_orders.orders_id ) JOIN items ON ( items.id = items_orders.items_dictionary_id ) WHERE DATE( orders.date ) =  '" + calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DAY_OF_MONTH) + "' AND (officiants.first_name = '" + officiant.getFirstName() + "' AND officiants.second_name = '" + officiant.getSecondName() + "')");
            while (resultSet.next()) {
                earningsTotal = resultSet.getInt("earningsTotal");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return earningsTotal;
    }

    @Override
    public void removeDay(Calendar calendar) throws RemoteException {
        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM orders, items_orders USING orders, items_orders WHERE orders.id = items_orders.orders_id AND DATE( orders.date ) = '" + calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DAY_OF_MONTH) + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void changeOfficiantName(Officiant oldOfficiant, Officiant newOfficiant) throws RemoteException {
        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate("UPDATE officiants SET officiants.first_name = '" + oldOfficiant.getFirstName() + "', officiants.second_name = '" + oldOfficiant.getSecondName() + "' WHERE officiants.first_name = '" + newOfficiant.getFirstName() + "' AND officiants.second_name = '" + newOfficiant.getSecondName() + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Order> getOrders(Calendar calendar) throws RemoteException {
        List<Order> orders = new ArrayList<>();
        List<Item> items = new ArrayList<>();
        Order order;
        Officiant officiant;
        Item item;
        String request = "SELECT orders.id, officiants.first_name, officiants.second_name, items.name, items.cost, items_orders.quantity  FROM orders JOIN officiants ON ( officiants.id = orders.officiant_id ) JOIN items_orders ON ( orders.id = items_orders.orders_id ) JOIN items ON ( items.id = items_orders.items_dictionary_id ) WHERE DATE( orders.date ) = '" + calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DAY_OF_MONTH) + "'";

        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(request);

            resultSet.next();
            int orderId = resultSet.getInt("id");
            officiant = new Officiant(resultSet.getString("first_name"), resultSet.getString("second_name"));

            resultSet = statement.executeQuery(request);

            while (resultSet.next()){
                if(orderId == resultSet.getInt("id")) {
                    item = new Item(resultSet.getString("name"), resultSet.getInt("cost"));
                    items.add(item);
                } else {
                    order = new Order(officiant, items);
                    orders.add(order);
                    items = new ArrayList<>();
                    item = new Item(resultSet.getString("name"), resultSet.getInt("cost"));
                    items.add(item);
                }
                officiant = new Officiant(resultSet.getString("first_name"), resultSet.getString("second_name"));
                orderId = resultSet.getInt("id");
            }
            order = new Order(officiant, items);
            orders.add(order);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    @Override
    public Calendar lastOfficiantWorkDate(Officiant officiant) throws RemoteException {
        Calendar calendar = Calendar.getInstance();

        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT MAX(`date`) AS `date` FROM orders JOIN officiants ON orders.officiant_id = officiants.id WHERE officiants.first_name = '" + officiant.getFirstName() + "' AND officiants.second_name = '" + officiant.getSecondName() + "'");
            while (resultSet.next()) {
                calendar.setTime(resultSet.getDate("date"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return calendar;
    }
}
