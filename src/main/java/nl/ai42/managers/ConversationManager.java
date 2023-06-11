package nl.ai42.managers;

import nl.ai42.AI42Main;
import nl.ai42.entity.conversation.Conversation;
import nl.ai42.utils.database.Row;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ConversationManager implements Serializable {
    private transient List<Conversation> conversations;

    public ConversationManager() {
        conversations = new ArrayList<>();
    }

    public int getConversationCount()
    {
        int conversationCount = AI42Main.database.getTable("chat").select(
                (row) -> row.getValue("username").equals(AI42Main.currentUser)
        ).size();

        return conversationCount;
    }

    public Conversation startConversation(Conversation conversation) {
        conversations.add(conversation);
        System.out.println("Conversation started");

        return conversation;
    }

    public void endConversation(Conversation conversation) {
        if (conversations.remove(conversation)) {
            System.out.println("Conversation ended: " + conversation.getId());
        } else {
            System.out.println("Conversation not found: " + conversation.getId());
        }
    }

    public ArrayList<Row> getChatHistoryFromConversation(final String currentConversation) {
        ArrayList<Row> rows = AI42Main.database.getTable("chatmsg").select(row ->
                row.getValue("username").equals(AI42Main.currentUser) && row.getValue("chatname").equals(currentConversation)
        );

        return rows;
    }

    public void insertMessageToChat(final String conversation, final String message_content, final boolean is_ai) {
        int msgCounter = AI42Main.database.getTable("chatmsg")
                .select((row) -> true)
                .size() + 1;

        AI42Main.database.getTable("chatmsg").insert(new Row(new HashMap<>() {{
            put("username", AI42Main.currentUser);
            put("chatname", conversation);
            put("msg_counter", String.valueOf(msgCounter));
            put("msg_content", message_content);
            put("is_ai", String.valueOf(is_ai));
            put("sent", new Date().toString());
        }}));

        // Save DB after each insertion
        AI42Main.database.storeInFile();
    }

    public List<Conversation> getAllConversations() {
        return conversations;
    }
}
