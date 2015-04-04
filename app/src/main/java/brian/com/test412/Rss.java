package brian.com.test412;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by briansoufir on 01/04/15.
 */
public class Rss extends Activity {

    public String url = "http://agendas.iut.univ-paris8.fr/indexRSS.php?login=";
    public String identifiant;
    public ListView lv_Agenda;
    public Button button_AddToCalendar;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        this.identifiant = getIntent().getStringExtra("login");
        this.lv_Agenda = (ListView) findViewById(R.id.listeAgenda);
        this.button_AddToCalendar = (Button) findViewById(R.id.add_btn);

        try {
            this.lv_Agenda.setAdapter(new MyAdapter(XmlToEvent(this.identifiant), this));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.button_AddToCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //TODO
            }
        });
    }

    private ArrayList<Event> XmlToEvent(String identifiant) throws Exception {

        ArrayList<Event> myEventList = new ArrayList<Event>();
        //récupération du flux xml
        DocumentBuilderFactory fabrique = DocumentBuilderFactory.newInstance();
        // création d'un constructeur de documents
        DocumentBuilder constructeur = fabrique.newDocumentBuilder();
        //lecture du flux xml
        Document document;
        InputStream openStream = null;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(new HttpGet(url + identifiant));
            openStream = response.getEntity().getContent();
        } catch (Exception e) {
            Log.e("[GET REQUEST]", "network", e);
        }

        document = constructeur.parse(openStream);
        Element racine = document.getDocumentElement();
        NodeList liste = racine.getElementsByTagName("item");

        for (int i = 0; i < liste.getLength(); i++) {
            Element E1 = (Element) liste.item(i);
            String titre = "", description = "";
            for (int j = 0; j < liste.item(i).getChildNodes().getLength(); j++) {
                Node item = liste.item(i).getChildNodes().item(j);
                if (item.getNodeType() == Node.ELEMENT_NODE) {
                    Element el1 = (Element) item;
                    if (el1.getTagName().equals("title"))
                        titre = item.getTextContent();
                    else if (el1.getTagName().equals("description"))
                        description = item.getTextContent();
                }
            }
            myEventList.add(eventParsed(titre, description, i));
        }
        return myEventList;
    }

    private Event eventParsed(String titre, String description, int i) {
        String[] tabDescriptionTitre = titre.split("[ ]+");
        String[] tabDescription = description.split("[ ]+");

        String typeCour = tabDescription[tabDescription.length - 1].replaceAll("[^\\w]", "");
        String prenomProf = tabDescription[tabDescription.length - 3];
        String nomProf =tabDescription[tabDescription.length - 2];
        String jour = tabDescriptionTitre[0];
        String date = tabDescriptionTitre[1];
        String heureDebut = tabDescriptionTitre[3];
        String heureFin = tabDescriptionTitre[5];
        String nomCour = tabDescriptionTitre[7];

        if (tabDescriptionTitre.length > 9)
            nomCour += tabDescriptionTitre[8];

        String salle = tabDescriptionTitre[tabDescriptionTitre.length - 1].replaceAll("[^\\w]", "");

        return new Event(nomCour, typeCour, salle,prenomProf,nomProf, jour, date, heureDebut, heureFin);
    }
}