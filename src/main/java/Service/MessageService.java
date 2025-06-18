package Service;

import java.util.List;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {

    private MessageDAO messageDAO;
    public MessageService(){
        messageDAO = new MessageDAO();
    }

    public Message createMessage(Message message){
        if (message == null) return null;

        if (message.getMessage_text().trim().isEmpty() || 
            message.getMessage_text().length() > 255  || 
            message.getPosted_by() < 0) {
            return null;
        } 
        return messageDAO.createMessage(message);
    }



    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }


    public Message getMessageById(int id) {
        return messageDAO.getMessageById(id);
    }

    public Message deleteMessageById(int id){
        Message msgToDelete = messageDAO.getMessageById(id);
        if (msgToDelete == null){
            return null;
        }
        messageDAO.deleteMessageById(id);
        return msgToDelete;
    }

    public Message updateMessageById(int id, String newText) {
        Message msgToUpdate = messageDAO.getMessageById(id);
        if (msgToUpdate == null || 
            newText == null ||
            newText.trim().isEmpty() || 
            newText.length() > 255){
            return null;
        }
        return messageDAO.updateMessageById(id, newText);
    }

    public List<Message> getAllMessagesFromAccount(int id){
        return messageDAO.getAllMessagesFromAccount(id);
    }





}

    /*if (message.getMessage_text().trim().isEmpty() || message.getMessage_text().length() > 255  || message.getPosted_by() < 0) {
            return null;
        } else {
            return messageDAO.newMessage(message);
        } 
    }*/
    

