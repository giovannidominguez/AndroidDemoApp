package edu.ucsb.cs.cs190i.giovannidominguez.masterdetaildemo;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class Comic extends SavableFragment {
    private Button getComic;
    private EditText input;
    private XkcdRetriever source;
    private TouchImage myComic;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View rootView = inflater.inflate(R.layout.fragment_comic, container, false);

        input = (EditText) rootView.findViewById(R.id.editText);
        getComic = (Button) rootView.findViewById(R.id.getCom);
        myComic = (TouchImage) rootView.findViewById(R.id.thecomic);
        



        getComic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
                source = new XkcdRetriever();
                input.setImeOptions(EditorInfo.IME_ACTION_DONE);
                if(input.getText().toString().length() == 0) {
                    Toast.makeText(getActivity(), "Please Enter A Number Between 1 and 1826", Toast.LENGTH_LONG).show();  //case for empty edit text throw toast

                }
                else if((Integer.parseInt(input.getText().toString()) == 0 )){  // case for if 0 entered (out of range)
                    Toast.makeText(getActivity(), "Number out of range. Selecting random Comic!",
                            Toast.LENGTH_LONG).show();
                    displayComic((int) (Math.random() * 1827 + 1));
                }

                else if ((Integer.parseInt(input.getText().toString()) > source.XkcdMaxId) ) { // out of range bigger than 1827
                    Toast.makeText(getActivity(), "Number out of range. Selecting random Comic!",
                            Toast.LENGTH_LONG).show();
                    displayComic((int) (Math.random() * 1827 + 1));
                } else {
                    displayComic(Integer.parseInt(input.getText().toString())); 
                }
            }




            private void displayComic(int v) {
               

                source.getImage(v, new XkcdRetriever.XkcdBitmapResultListener() {
                    @Override
                    public void onImage(Bitmap image) {
                        myComic.setImageBitmap(image);
                        
                    }
                });
            }

        });


        
        return rootView;
    }

    @Override
    public void saveState(Bundle bundle) {

    }

    @Override
    public void restoreState(Bundle bundle) {

    }
}



