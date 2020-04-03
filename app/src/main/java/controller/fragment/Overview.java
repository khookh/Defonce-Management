package controller.fragment;

import controller.Session_Control;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.example.defonce_management.R;

public class Overview extends Fragment {

    TextView lasttext;

    View root;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.overview, container, false);
        lasttext = root.findViewById(R.id.lastd);
        Session_Control.startup_ov();
        return root;
    }

    public void setLastDText(String texte) {
        this.lasttext.setText(texte);
    }

}
