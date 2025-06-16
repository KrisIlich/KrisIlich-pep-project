package DAO;

import Model.Message;

public class MessageDAO {

    public Message newMessage(Message message){
        //add message sql logic
        return message;
    }

    public Message getAllMessages(){
        //logic
        return null;
    }

    public Message getMessageByID(int id){
        //logic
        Message message = new Message();
        return message;
    }

    public Message deleteMessageByID(int id){
        return null;
    }

    public Message updateMessageByID(int id) {
        return null;
    }

    
    
}
