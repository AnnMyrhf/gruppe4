import axios from "axios";
import authHeader from "./auth-header";

const API_URL = "http://localhost:8081/";

/*const getPublicContent = () => {
    return axios.get(API_URL + "start");
};*/

const getBuergerDashBoard = (id) => {
    return axios.get(API_URL + "buerger/dashboard/" + id, { headers: authHeader() });
};

const getMitarbeiterBoard = () => {
    return axios.get(API_URL + "mitarbeiter/dashboard", { headers: authHeader() });
};

const getBeschwerde = (id) => {
    return axios.get(API_URL + "beschwerde/"+id, {headers: authHeader()})
}

const postBeschwerde = (buergerId, titel, beschwerdeTyp, textfeld) => {
    return axios.post(API_URL + "beschwerde/erstellen", {
        buergerId,
        titel,
        beschwerdeTyp,
        textfeld,
    }, {headers: authHeader()})
};

const updateKommentar = (beschwerdeId, kommentar) => {
    return axios.put(
        `${API_URL}beschwerde/${beschwerdeId}/kommentar`, // URL mit der Beschwerde-ID
        { kommentar }, // Request-Body
        { headers: authHeader() } // Authentifizierungs-Header
    );
};

const updateStatus = (beschwerdeId, status) => {
    return axios.put(
        `${API_URL}beschwerde/${beschwerdeId}/status`, // URL mit der Beschwerde-ID
        { status }, // Request-Body
        { headers: authHeader() } // Authentifizierungs-Header
    );
};

export default {
    //getPublicContent,
    getBuergerDashBoard,
    getMitarbeiterBoard,
    getBeschwerde,
    postBeschwerde,
    updateKommentar,
    updateStatus
};