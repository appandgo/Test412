package brian.com.test412;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by briansoufir on 03/04/15.
 */
public class MyAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<Event> list = new ArrayList<Event>();
    private Context context;

    public MyAdapter(ArrayList<Event> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos).toString();
    }

    @Override
    public long getItemId(int pos) {
        return 0;//return listItems.get(position).getId==null ? 0  : list.get(pos).getId();
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.activity_main3, null);
        }

        final TextView listItemText = (TextView) view.findViewById(R.id.list_item_string);
        final Event myEvent = list.get(position);
        listItemText.setText(myEvent.toString());

        Button addBtn = (Button) view.findViewById(R.id.add_btn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentEvent = new Intent(Intent.ACTION_EDIT);
                intentEvent.setType("vnd.android.cursor.item/event");
                intentEvent.putExtra("eventLocation", myEvent.salle + " / IUT MONTREUIL");
                intentEvent.putExtra("beginTime", myEvent.getDateDebutInMillis());
                intentEvent.putExtra("allDay", false);
                intentEvent.putExtra("rrule", "FREQ=DAILY");
                intentEvent.putExtra("endTime", myEvent.getDateFinInMillis());
                intentEvent.putExtra("title", myEvent.getTitle());
                parent.getContext().startActivity(intentEvent);
                notifyDataSetChanged();
            }
        });
        return view;
    }
}


