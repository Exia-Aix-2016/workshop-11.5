package GUI;

import client.Client;
import client.IClientConnection;

import java.io.IOException;

public abstract class Gui implements IDisplay, ISend {


    protected Client client;
    public   void onConnexion(final String pseudo, final String address, final int port) throws IOException {
        if(client == null){
            this.client = new Client(this,pseudo,address,port);
        }else{
            this.client.closeConnection();
            this.client = new Client(this,pseudo,address,port);
        }
        Thread t = new Thread(client);
        t.start();
    }

   public IClientConnection getClient(){
        return this.client;
    }


}
