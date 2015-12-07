package com.chailley.vincent.grootv2;

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
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText nametxt, contributeurstxt, devisetxt;
    List<Comptes> Comptes = new ArrayList<Comptes>();
    ListView comptesListView;
    DatabaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nametxt = (EditText) findViewById(R.id.txtname);
        contributeurstxt = (EditText) findViewById(R.id.txtcontributeurs);
        devisetxt = (EditText) findViewById(R.id.txtdevise);
        comptesListView = (ListView) findViewById(R.id.listView);
        dbHandler = new DatabaseHandler(getApplicationContext());
        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);

        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("comptes");
        tabSpec.setContent(R.id.compte_gen);
        tabSpec.setIndicator("Nouveau compte");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("list");
        tabSpec.setContent(R.id.pay_gen);
        tabSpec.setIndicator("Modifier un compte");
        tabHost.addTab(tabSpec);


        final Button addbtn = (Button) findViewById(R.id.btnAdd);
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Comptes compte = new Comptes(dbHandler.getCompteCount(), String.valueOf(nametxt.getText()), String.valueOf(contributeurstxt.getText()), String.valueOf(devisetxt.getText()));
                if (!comptesExists(compte)) {
                    dbHandler.createCompte(compte);
                    Comptes.add(compte);
                    Toast.makeText(getApplicationContext(), String.valueOf(nametxt.getText()) + " à été créé !", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(getApplicationContext(), String.valueOf(nametxt.getText()) + " existe déjà, changé de nom.", Toast.LENGTH_SHORT).show();
            }
        });




        nametxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                addbtn.setEnabled(!nametxt.getText().toString().trim().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        List<Comptes> addableComptes = dbHandler.getAllComptes();
        int compteCount = dbHandler.getCompteCount();

        for (int i = 0; i < compteCount ; i++){
            Comptes.add(addableComptes.get(i));
        }

        if(!addableComptes.isEmpty())
            populateList();

    }





    private boolean comptesExists(Comptes compte) {
        String name = compte.getName();
        int contactCount = Comptes.size();

        for (int i = 0; i < contactCount; i++) {
            if (name.compareToIgnoreCase(Comptes.get(i).getName()) == 0)
                return true;
        }
        return false;
    }



    private void addComptes(String name, String contributeurs, String devise) {
        Comptes.add(new Comptes(0, name, contributeurs, devise));
    }

    private class ComptesListAdapter extends ArrayAdapter<Comptes> {
        public ComptesListAdapter() {
            super(MainActivity.this, R.layout.listview_item, Comptes);
        }
        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null)
                view = getLayoutInflater().inflate(R.layout.listview_item, parent, false);

            Comptes currentComptes = Comptes.get(position);

            TextView name = (TextView) view.findViewById(R.id.textView2);
            name.setText(currentComptes.getName());
            TextView phone = (TextView) view.findViewById(R.id.textView3);
            phone.setText(currentComptes.getcontributeurs());
            TextView email = (TextView) view.findViewById(R.id.textView4);
            email.setText(currentComptes.getDevise());
            return view;
        }

    }



    private void populateList() {
        ArrayAdapter<Comptes> adapter = new ComptesListAdapter();
        comptesListView.setAdapter(adapter);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}