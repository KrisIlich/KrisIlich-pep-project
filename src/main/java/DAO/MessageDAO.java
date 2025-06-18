package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.Connection;

import java.util.ArrayList;
import java.util.List;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class MessageDAO {

    public Message createMessage(Message message){
        
        try {
        Connection connection = ConnectionUtil.getConnection();
        String sql = "INSERT INTO message (posted_by,message_text, time_posted_epoch) VALUES (?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, message.getPosted_by());
        ps.setString(2, message.getMessage_text());
        ps.setLong(3, message.getTime_posted_epoch());
        ps.executeUpdate();
        try (ResultSet keys = ps.getGeneratedKeys()){
            if (keys.next()) {
                message.setMessage_id(keys.getInt(1));
                return message;
            }
        }

        return message;
        }
        catch(SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public List<Message> getAllMessages(){
        List<Message> messages = new ArrayList<>();

        try {
        Connection connection = ConnectionUtil.getConnection();
        String sql = "SELECT * FROM message";            
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

    public Message getMessageById(int id){

        if (id < 0) {
            return null;
        }
        
        try {
        Connection connection = ConnectionUtil.getConnection();
        String sql = "SELECT * FROM message WHERE message_id = ?";
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

            if (id < 0) {
            return null;
        }

            try {
                Connection connection = ConnectionUtil.getConnection();
                String selectSQL = "SELECT * FROM message WHERE message_id = ?";
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

        try {
            Connection connection = ConnectionUtil.getConnection();
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
                    rs.getLong("time_posted_epoch")
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

    
    
}
