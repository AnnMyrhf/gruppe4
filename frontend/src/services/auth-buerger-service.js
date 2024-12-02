import axios from "axios";

const API_URL = "http://localhost:8081/";

const register = (anrede, vorname, name, telefonnummer, email, passwort) => {
    return axios.post(API_URL + "buerger-registrieren", {
        anrede,
        vorname,
        name,
        telefonnummer,
        email,
        passwort,
    });
};

const login = (email, passwort) => {
    return axios
        .post(API_URL + "buerger-anmelden", {
            email,
            passwort,
        })
        .then((response) => {
            if (response.data.accessToken) {
                localStorage.setItem("user", JSON.stringify(response.data));
            }

            return response.data;
        });
};

const logout = () => {
    localStorage.removeItem("user");
};

export default {
    register,
    login,
    logout,
};