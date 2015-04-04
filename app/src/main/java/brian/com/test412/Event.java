package brian.com.test412;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by briansoufir on 02/04/15.
 */
public class Event {

    public int nb;
    public String title, nomProf, prenomProf, jour, date, heureDebut, heureFin, nomCour, salle, typeCour;

    public Event(String nomCour, String typeCour, String salle, String prenomProf, String nomProf, String jour, String date, String heureDebut, String heureFin) {
        this.nomCour = nomCour;
        this.typeCour = typeCour;
        this.salle = salle;
        this.prenomProf = prenomProf;
        this.nomProf = nomProf;
        this.jour = jour;
        this.date = date;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
    }

    public long getDateDebutInMillis() {
        return this.getDateInMillis(heureDebut);
    }

    public long getDateFinInMillis() {
        return this.getDateInMillis(heureFin);
    }

    public long getDateInMillis(String heure) {
        try {
            return new SimpleDateFormat("dd/MM/yyyy H:m", Locale.FRENCH).parse(date + "/2015 " + heure).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Long.parseLong(null);
    }

    public String getTitle() {
        return nomCour+" en "+salle+" avec "+prenomProf+" "+nomProf;
    }

    public String toString() {
        if (this.nomProf.contains("VIDE") && this.salle.contains("VIDE"))
            return "Pas cours le " + this.jour + " " + this.date;
        return
                nomCour
                        + " en " + salle
                        + " avec " + prenomProf + " " + nomProf
                        + " le " + this.jour
                        + " : " + this.date
                        + " de " + heureDebut
                        + " à " + heureFin;
    }

}