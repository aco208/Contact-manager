package com.example.aleksandro.contactmanager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    EditText naamTxt, telTxt, emailTxt, adresTxt;
    ImageView contactImageImgView;
    List<Contact> Contacten = new ArrayList<Contact>();
    ListView contactListView;
    Uri imageUri = Uri.parse("android.resource://com.example.aleksandro.contactmanager/drawable/"+R.drawable.no_user_logo);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        naamTxt = (EditText) findViewById(R.id.txtNaam);
        telTxt = (EditText) findViewById(R.id.txtTel);
        emailTxt = (EditText) findViewById(R.id.txtEmail);
        adresTxt = (EditText) findViewById(R.id.txtAdres);
        contactListView = (ListView) findViewById(R.id.listView);
        contactImageImgView = (ImageView) findViewById(R.id.imgViewContactImage);
        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);

        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("creator");
        tabSpec.setContent(R.id.creator);
        tabSpec.setIndicator("Creator");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("Lister");
        tabSpec.setContent(R.id.List);
        tabSpec.setIndicator("lister");
        tabHost.addTab(tabSpec);

        final Button knopAdd = (Button) findViewById(R.id.addKnop);
        knopAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contacten.add(new Contact(naamTxt.getText().toString(), telTxt.getText().toString(), emailTxt.getText().toString(), adresTxt.getText().toString(), imageUri));
                populateLijst();
                Toast.makeText(getApplicationContext(), ""+naamTxt.getText().toString()+" is toegevoegt in uw lijst!", Toast.LENGTH_SHORT).show();
                veldenLegen();
            }
        });

        naamTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                knopAdd.setEnabled(!naamTxt.getText().toString().trim().isEmpty());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        contactImageImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Kies je contact afbeelding"), 1);
            };
        });
    }

    public void onActivityResult(int reqCode, int resCode, Intent data) {
        if(resCode == RESULT_OK){
            if(reqCode == 1) {
                imageUri = (Uri) data.getData();
                contactImageImgView.setImageURI(data.getData());
            }

        }
    }

    private void populateLijst() {
        ArrayAdapter<Contact> adapter = new ContactLijstAdapter();
        contactListView.setAdapter(adapter);
    }

    private void veldenLegen(){
        naamTxt.setText("");
        telTxt.setText("");
        emailTxt.setText("");
        adresTxt.setText("");
        contactImageImgView.setImageResource(R.drawable.no_user_logo);
        imageUri = Uri.parse("android.resource://com.example.aleksandro.contactmanager/drawable/"+R.drawable.no_user_logo);
    }


    private class ContactLijstAdapter extends ArrayAdapter<Contact> {
        public ContactLijstAdapter(){
            super (MainActivity.this, R.layout.contact_lijst_item, Contacten);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null)
                view = getLayoutInflater().inflate(R.layout.contact_lijst_item, parent, false);
            Contact huidigeContact = Contacten.get(position);

            TextView naam = (TextView) view.findViewById(R.id.contactNaam);
            naam.setText(huidigeContact.getNaam());
            TextView tel = (TextView) view.findViewById(R.id.contactTelefoonnummer);
            tel.setText(huidigeContact.getTel());
            TextView email = (TextView) view.findViewById(R.id.contactEmail);
            email.setText(huidigeContact.getEmail());
            TextView adres = (TextView) view.findViewById(R.id.contactAdres);
            adres.setText(huidigeContact.getAdres());
            ImageView ivContactImage = (ImageView) view.findViewById(R.id.ivContactImage);
            ivContactImage.setImageURI(huidigeContact.getImageURI());

            return view;

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
