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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nametxt = (EditText) findViewById(R.id.txtname);
        contributeurstxt = (EditText) findViewById(R.id.txtcontributeurs);
        devisetxt = (EditText) findViewById(R.id.txtdevise);
        comptesListView = (ListView) findViewById(R.id.listView);
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
            public void onClick (View view){
                addComptes(nametxt.getText().toString(),contributeurstxt.getText().toString(), devisetxt.getText().toString());
                populateList();
                Toast.makeText(getApplicationContext(), nametxt.getText().toString() + "à été créé.", Toast.LENGTH_SHORT).show();
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
    }

    private void addComptes(String name, String contributeurs, String devise) {
        Comptes.add(new Comptes(name, contributeurs, devise));
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