package br.com.chies;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FirebaseComunication
{

    public static FirebaseDatabase firebaseDatabase = null;

    public static class Clima
    {

        public String temperatura;
        public String umidade;
        public String precipitacao;
        public String vento;

        public String data;
        public String hora;
        public String id;
        public String valor;

        public Clima(String data, String hora, String id, String valor)
        {
            this.data = data;
            this.hora = hora;
            this.id = id;
            this.valor = valor;
        }
    }       

    public void initFirebase() throws IOException
    {
        try
        {
            FileInputStream serviceAccount = new FileInputStream(new File("/serviceAccountKey.json"));

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://chies-automation.firebaseio.com/")
                    .build();

            FirebaseApp.initializeApp(options);

            firebaseDatabase = FirebaseDatabase.getInstance();
        } catch (FileNotFoundException ex)
        {
            ex.printStackTrace();
            Logger.getLogger(FirebaseComunication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void saveUsingPush(String recurso, Clima item) throws IOException
    {
        if (item != null)
        {
            if (firebaseDatabase == null)
            {
                initFirebase();
            }
            /* Get database root reference */
            DatabaseReference databaseReference = firebaseDatabase.getReference("/");
            /* Get existing child or will be created new child. */
            DatabaseReference childReference = databaseReference.child(recurso + "/" + item.id);
            /**
             * The Firebase Java client uses daemon threads, meaning it will not
             * prevent a process from exiting. So we'll
             * wait(countDownLatch.await()) until firebase saves record. Then
             * decrement `countDownLatch` value using
             * `countDownLatch.countDown()` and application will continues its
             * execution.
             */
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            /**
             * push() Add to a list of data in the database. Every time you push
             * a new node onto a list, your database generates a unique key,
             * like items/unique-item-id/data
             */
//            childReference.push().setValue(item, new DatabaseReference.CompletionListener()
            childReference.setValue(item, new DatabaseReference.CompletionListener()
            {

                @Override
                public void onComplete(DatabaseError de, DatabaseReference dr)
                {
                    System.out.println(new FuncoesUteis().getDataHora("") + " : Informação Salva com Sucesso!");
                    // decrement countDownLatch value and application will be continues its execution.
                    countDownLatch.countDown();
                }
            });
            try
            {
                //wait for firebase to saves record.
                countDownLatch.await();
            } catch (InterruptedException ex)
            {
                ex.printStackTrace();
                Logger.getLogger(FirebaseComunication.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public String getJSON(String urlToRead) throws Exception
    {
        if ((urlToRead == null) || (urlToRead.equals("")))
        {
            return "#TEC#";
        }
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null)
        {
            result.append(line);
        }
        rd.close();
        return "#TEC#" + result.toString();
    }
}
