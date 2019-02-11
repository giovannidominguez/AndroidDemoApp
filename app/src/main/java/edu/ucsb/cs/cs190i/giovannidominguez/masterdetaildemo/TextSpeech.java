package edu.ucsb.cs.cs190i.giovannidominguez.masterdetaildemo;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Activity;
import android.speech.tts.TextToSpeech;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.EditText;
import java.util.Locale;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 */
public class TextSpeech extends SavableFragment{
    private static final String TextExtra = "Text";
    private String text = "";
    TextToSpeech t1;
    EditText ed1;
    ImageButton b1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_text_speech, container, false);

        View rootView = inflater.inflate(R.layout.fragment_text_speak, container, false);
        ed1=(EditText)rootView.findViewById(R.id.editText);
        b1=(ImageButton)rootView.findViewById(R.id.speak);
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(ed1.getWindowToken(), 0);
        t1=new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.CANADA);
                }
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                toSpeak();
            }
        });

        return rootView;
    }


    public void toSpeak() {

        String toSpeak = ed1.getText().toString();
       // Toast.makeText(getActivity(), toSpeak,Toast.LENGTH_SHORT).show();
        t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);


    }

    public void onPause(){
        if(t1 !=null){
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }


    @Override
    public void saveState(Bundle bundle) {
        bundle.putString(TextExtra, ed1.getText().toString());
    }

    @Override
    public void restoreState(Bundle bundle) {
        if (bundle != null) {
            text = bundle.getString(TextExtra);
        }
    }



}


