package com.deboosere.myqr;

public class User {
    public String nom;
    public String email;
    public String address;
    public String sang;
    public String lastedit;
    public String dateadd;
    public Boolean actif;
    public String  telephone;

    public User(String nom, String email, String address, String sang, String lastedit, String dateadd, Boolean actif, String telephone) {
        this.nom = nom;
        this.email = email;
        this.address = address;
        this.sang = sang;
        this.lastedit = lastedit;
        this.dateadd = dateadd;
        this.actif = actif;
        this.telephone = telephone;
    }

    @Override
    public String toString() {
        return "User{" +
                "nom='" + nom + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", sang='" + sang + '\'' +
                ", lastedit='" + lastedit + '\'' +
                ", dateadd='" + dateadd + '\'' +
                ", actif=" + actif +
                ", telephone='" + telephone + '\'' +
                '}';
    }

    public User() {
    }
}
