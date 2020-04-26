package controller.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.example.defonce_management.R;
import model.Session;

/**
 * Implements the controls of the Consumption fragment displayed
 */
public class Consumption extends Fragment {
    View root;
    Session session;
    TextView bar;
    ProgressBar skren;
    EditText volume, percent, bevname;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.consumption, container, false);
        bar = root.findViewById(R.id.barcomment);
        skren = root.findViewById(R.id.progressBar);
        volume = root.findViewById(R.id.volume);
        percent = root.findViewById(R.id.percent);
        bevname = root.findViewById(R.id.bevname);


        this.session.setSkren();
        setProgressBar(this.session.getSkrenlevel());
        setTextBar("Your alcool blood level is now around "+session.getActual_user().getAlcoolRate()+" g/L" +"\n"+ session.getSkrenmessage());
        return root;
    }

    /**
     * set the text to display under the the skren bar
     * @param text
     */
    public void setTextBar(String text) { bar.setText(text); }

    /**
     * set the progress of the skren bar
     * @param progress
     */
    public void setProgressBar(Integer progress) { skren.setProgress(progress); }

    public String getVolume() {
        return volume.getText().toString();
    }
    public String getPercent() {
        return percent.getText().toString();
    }
    public String getBevname() {
        return bevname.getText().toString();
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
