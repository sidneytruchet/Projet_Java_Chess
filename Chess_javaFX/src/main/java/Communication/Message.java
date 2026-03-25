package Communication;

public class Message {


    public enum Type {
        MOVE,
        CHAT,
        SURRENDER,
        CONNECT
    }

    private Type type;
    private String data;


    public Message(Type type, String data) {
        this.type = type;
        this.data = data;
    }



    @Override
    public String toString() {
        return type.name() + ":" + data;
    }



    public static Message fromString(String rawMessage) {
        if (rawMessage == null || !rawMessage.contains(":")) {

            return new Message(Type.CHAT, rawMessage);
        }


        String[] parts = rawMessage.split(":", 2);
        try {
            Type typeDecode = Type.valueOf(parts[0]);
            return new Message(typeDecode, parts[1]);
        } catch (IllegalArgumentException e) {
            System.out.println("Type de message inconnu : " + parts[0]);
            return new Message(Type.CHAT, rawMessage);
        }
    }


    public Type getType() { return type; }
    public String getData() { return data; }
}