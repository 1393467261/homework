package com.hzw.homework.util;

import com.hzw.homework.entity.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright@www.localhost.com.
 * Author:H.zw
 * Date:2018/5/9 20:48
 * Description:
 */
@SuppressWarnings("all")
public class JdbcUtil {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://112.74.36.19:3306/wx?characterEncoding=utf-8";
    static final String USER = "admin";
    static final String PASS = "admin";

    public static Connection getConnection() throws ClassNotFoundException, SQLException {

        Class.forName(JDBC_DRIVER);
        Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
        return connection;
    }

    public static void close(ResultSet rs, PreparedStatement ps, Connection con) {

        if (rs != null && ps != null && con != null) {

            try {
                rs.close();
                ps.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void close(Connection connection, PreparedStatement ps) {

        if (connection != null && ps != null) {

            try {
                ps.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void addBook(String name, String id, float price){

        Connection con = null;
        PreparedStatement ps = null;
        String sql = "insert into library(book_id, book_price, book_name) values(?, ?, ?)";

        try {
            con = getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, id);
            ps.setFloat(2, price);
            ps.setString(3, name);
            ps.execute();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            close(con, ps);
        }
    }

    public static List<Book> getBookList(){

        List<Book> list = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet set = null;
        String sql = "select * from library";

        try{
            con = getConnection();
            ps = con.prepareStatement(sql);
            set = ps.executeQuery();

            while (set.next()){
                list.add(new Book(set.getString("book_id"),
                                    set.getString("book_name"),
                                        set.getFloat("book_price")));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            close(set, ps, con);
        }

        return list;
    }

    public static void addCart(String id) {

        Connection con = null;
        PreparedStatement ps = null;
        String sql = "insert into cart(book_id) values(?)";

        try {
            con = getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, id);
            ps.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            close(con, ps);
        }
    }

    public static List<Book> getCart(){

        List<Book> list = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet set = null;
        String sql = "select * from library where book_id in (select book_id from cart)";

        try{
            con = getConnection();
            ps = con.prepareStatement(sql);
            set = ps.executeQuery();

            while (set.next()){
                list.add(new Book(set.getString("book_id"),
                        set.getString("book_name"),
                        set.getFloat("book_price")));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            close(set, ps, con);
        }

        return list;
    }

    public static void delCart(String id) {

        Connection con = null;
        PreparedStatement ps = null;
        String sql = "delete from cart where book_id = ?";

        try {
            con = getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, id);
            ps.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            close(con, ps);
        }
    }

    public static List<Book> searchBook(String title) {

        List<Book> list = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet set = null;
        String sql = "select * from library where book_name like ?";

        try{
            con = getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + title + "%");
            set = ps.executeQuery();

            while (set.next()){
                list.add(new Book(set.getString("book_id"),
                        set.getString("book_name"),
                        set.getFloat("book_price")));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            close(set, ps, con);
        }

        return list;
    }
}
