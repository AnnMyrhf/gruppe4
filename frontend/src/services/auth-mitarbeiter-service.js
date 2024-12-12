import axios from "axios";

const API_URL = "http://localhost:8081/";

const register = (anrede, vorname, nachname, telefonnummer, email, passwort, abteilung, position) => {
    return axios.post(API_URL + "mitarbeiter-registrieren", {
        anrede,
        vorname,
        nachname,
        telefonnummer,
        email,
        passwort,
        abteilung,
        position
    })
};

const login = (email, passwort) => {
    return axios
        .post(API_URL + "mitarbeiter-anmelden", {
            email,
            passwort,
        }, {
            headers: {
                "Content-Type": "application/json"
            }
        })
        .then((response) => {
            if (response.data.accessToken) {
                console.log("in localstorage gespeichert")
                localStorage.setItem("user", JSON.stringify(response.data));
            }
            console.log(response.data)
            return response.data;
        })
};

const logout = () => {
    localStorage.removeItem("user");
};

export default {
    register,
    login,
    logout,
};