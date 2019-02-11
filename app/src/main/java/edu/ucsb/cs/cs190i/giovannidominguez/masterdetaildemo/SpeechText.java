package edu.ucsb.cs.cs190i.giovannidominguez.masterdetaildemo;



import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.app.Activity;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.content.ActivityNotFoundException;

import java.util.Locale;
import java.util.ArrayList;






/**
 * A simple {@link Fragment} subclass.
 */
public class SpeechText extends SavableFragment {
    private final int REQ_CODE_SPEECH_INPUT = 1000;
    private static final String TextExtra = "Text";
    private String text = "";
    private TextView txtSpeechInput;
    private ImageButton btnSpeak;

    //private EditText textField;



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_speech_text, container, false);
        View rootView = inflater.inflate(R.layout.fragment_speech_text, container, false);
        txtSpeechInput = (TextView) rootView.findViewById(R.id.texter);
        btnSpeak = (ImageButton) rootView.findViewById(R.id.speak);

        btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });
        return rootView;
    }



    /**
     * Showing google speech input dialog
     * */
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getActivity(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     * */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == Activity.RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txtSpeechInput.setText(result.get(0).toString());
                }
                break;
            }

        }
    }

    @Override
    public void saveState(Bundle bundle) {
        bundle.putString(TextExtra, txtSpeechInput.getText().toString());
    }

    @Override
    public void restoreState(Bundle bundle) {
        if (bundle != null) {
            text = bundle.getString(TextExtra);
        }
    }

}
