package com.chailley.vincent.grootv2;

/**
 * Created by vincent on 07/12/2015.
 */
public class Comptes {

    private String _name, _contributeurs, _devise;

    public Comptes (String name, String contributeurs, String devise){
        _contributeurs = contributeurs;
        _devise = devise;
        _name = name;
    }

    public String getName() {
        return _name;
    }

    public String getcontributeurs() {
        return _contributeurs;
    }

    public String getDevise() {
        return _devise;
    }

}
