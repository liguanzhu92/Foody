package com.example.guanzhuli.foody.HomePage.fragment;
// Lily: Finished all design for this fragment.

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.guanzhuli.foody.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HelpFragment extends Fragment {
    Button mButtonConfirm;
    EditText mEditSubject, mEditContent;

    public HelpFragment() {
        // Required empty public constructor
    }


    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Help");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_help, container, false);
        mEditContent = (EditText) v.findViewById(R.id.help_email_subject);
        mEditSubject = (EditText) v.findViewById(R.id.help_email_content);
        mButtonConfirm = (Button) v.findViewById(R.id.help_email_send);
        mButtonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail();
            }
        });
        return v;
    }

    private void sendEmail() {
        String subject = mEditSubject.getText().toString();
        String message = mEditContent.getText().toString();
        Intent email = new Intent(Intent.ACTION_SEND);
        email.setData(Uri.parse("mailto:"));
        email.setType("text/plain");
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{ "liguanzhu390@gmail.com"});
        email.putExtra(Intent.EXTRA_SUBJECT, subject);
        email.putExtra(Intent.EXTRA_TEXT, message);
        try {
            startActivity(Intent.createChooser(email, "Send mail..."));
            Log.i("Finished", "");
            mEditContent.setText("");
            mEditSubject.setText("");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getContext(),
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }

    }

}
