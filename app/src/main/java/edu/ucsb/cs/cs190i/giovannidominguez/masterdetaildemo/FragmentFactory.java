package edu.ucsb.cs.cs190i.giovannidominguez.masterdetaildemo;

/**
 * Created by Samuel on 4/20/2017.
 */

public class FragmentFactory {
    public static SavableFragment createFragment(String fragment) {
        switch (fragment) {
            case "Speech-to-Text":
                return new SpeechText();
            case "Text-to-Speech":
                return new TextSpeech();
            case "Comic":
                return new Comic(); // TODO: IMPLEMENT CUH
            case "Video":
                return new Video();
            case "Animation":
                return new Animation();
            default:
                return null;





        }
    }
}
