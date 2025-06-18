package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.Connection;
import Model.Account;
import Util.ConnectionUtil;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.h2.command.Prepared;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;


public class MessageDAO {

    public Message newMessage(Message message){
        Connection connection = ConnectionUtil.getConnection();
        String sql = "INSERT INTO message (message_id, posted_by,message_text, time_posted_epoch) VALUES (?, ?, ?, ?)";
        
        try {
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, message.getMessage_id());
        ps.setInt(2, message.getPosted_by());
        ps.setString(3, message.getMessage_text());
        ps.setLong(4, message.getTime_posted_epoch());
        ps.executeQuery();
        return message;
        }
        catch(SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();

        String sql = "SELECT * FROM message";

        try {
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Message message = new Message(
            rs.getInt("message_id"),
            rs.getInt("posted_by"),
            rs.getString("message_text"),
            rs.getLong("time_posted_epoch"));
            messages.add(message);
        }
        }
        catch(SQLException e) {
            System.out.println(e);
        }
        return messages;
    }

    public Message getMessageByID(int id){
        Connection connection = ConnectionUtil.getConnection();
        String sql = "SELECT * FROM message WHERE message_id = ?";
        
        try {
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Message message = new Message(
            rs.getInt("message_id"),
            rs.getInt("posted_by"),
            rs.getString("message_text"),
            rs.getLong("time_posted_epoch"));
            return message;
        }
        }
        catch(SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public Message deleteMessageById(int id){
        Connection connection = ConnectionUtil.getConnection();
        String selectSQL = "SELECT * FROM message WHERE message_id = ?";
            try {
                PreparedStatement selPs = connection.prepareStatement(selectSQL);
                selPs.setInt(1, id);
                ResultSet rs = selPs.executeQuery();
                if (!rs.next()) {
                    return null;
                }

                Message deletedMessage = new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch"));

                String sql = "DELETE FROM message WHERE message_id = ?";
                    PreparedStatement ps = connection.prepareStatement(sql);
                    ps.setInt(1, id);
                    int rowsDeleted = ps.executeUpdate();
                    if (rowsDeleted > 0) {
                        return deletedMessage;
                    }
            } catch(SQLException e) {
                System.out.println(e);
        }
            return null;
    }

    public Message updateMessageById(int id, String newText) {
        Connection connection = ConnectionUtil.getConnection();
        
        try {
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, newText);
            ps.setInt(2, id);
            ps.executeUpdate();


            String selectedMsgSQL = "SELECT * FROM message WHERE message_id = ?";
            PreparedStatement updatedPs = connection.prepareStatement(selectedMsgSQL); 
            updatedPs.setInt(1, id);
            ResultSet rs = updatedPs.executeQuery();

            if (rs.next()) {
                Message message = new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getInt("time_posted_epoch")
                );
                return message;
            }
        }
        catch(SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public List<Message> getAllMessagesFromAccount(int id) {
    Connection connection = ConnectionUtil.getConnection();
    List<Message> messages = new ArrayList<>(); 

    try {
    String sql = "SELECT * FROM message WHERE posted_by = ?";
    PreparedStatement ps = connection.prepareStatement(sql);
    ps.setInt(1, id);

    ResultSet rs = ps.executeQuery();

    if (rs.next()) {
        Message message = new Message(
            rs.getInt("message_id"),
            rs.getInt("posted_by"),
            rs.getString("message_text"),
            rs.getLong("time_posted_epoch"));
        messages.add(message);
    }
    }
    catch(SQLException e) {
        System.out.println(e);
    }
    return messages;
    }

    
    
}
