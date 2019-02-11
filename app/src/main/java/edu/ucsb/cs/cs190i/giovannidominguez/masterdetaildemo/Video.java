package edu.ucsb.cs.cs190i.giovannidominguez.masterdetaildemo;


import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.VideoView;



/**
 * A simple {@link Fragment} subclass.
 */
public class Video extends SavableFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_video, container, false);



            super.onCreate(savedInstanceState);
            //rootView.setContentView(R.layout.activity_play_video);

            VideoView videoView = (VideoView) rootView.findViewById(R.id.video_view);


        MediaController mediaController = new MediaController(this.getActivity());
            mediaController.setAnchorView(videoView);
            videoView.setMediaController(mediaController);
            videoView.setVideoURI(Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.bigbuck));
            videoView.start();
        return rootView;
    }


    @Override
    public void saveState(Bundle bundle) {
    }

    @Override
    public void restoreState(Bundle bundle) {
    }





}
