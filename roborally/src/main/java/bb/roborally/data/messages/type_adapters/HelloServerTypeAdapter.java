package bb.roborally.data.messages.type_adapters;

import bb.roborally.data.messages.connection.HelloServer;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class HelloServerTypeAdapter extends TypeAdapter<HelloServer> {

    @Override
    public void write(JsonWriter jsonWriter, HelloServer helloServer) throws IOException {
        jsonWriter.beginObject();
            jsonWriter.name("group").value(helloServer.getGroup());
            jsonWriter.name("isAI").value(helloServer.isAI());
            jsonWriter.name("protocol").value(helloServer.getProtocol());
        jsonWriter.endObject();
    }

    @Override
    public HelloServer read(JsonReader jsonReader) throws IOException {
        HelloServer helloServer = new HelloServer();
        jsonReader.beginObject();
        String name;
        while (jsonReader.hasNext()){
            name = jsonReader.nextName();
            if(name.equals("protocol")){
                helloServer.setProtocol(jsonReader.nextString());
            }
            if(name.equals("group")){
                helloServer.setGroup(jsonReader.nextString());
            }
            if(name.equals("isAI")){
                helloServer.setAI(jsonReader.nextBoolean());
            }
        }
        jsonReader.endObject();
        return helloServer;
    }
}
