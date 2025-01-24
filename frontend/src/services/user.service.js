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

const postBeschwerde = (data) => {
    return axios.post(API_URL + "beschwerde/erstellen", data, {
        headers: {
            ...authHeader(),
            "Content-Type": "multipart/form-data", // Multipart-Header
        },
    });
};

const updateBeschwerde = (beschwerdeId, kommentar, status) => {
    return axios.put(
        `${API_URL}beschwerde/${beschwerdeId}/update`, // URL mit der Beschwerde-ID
        { kommentar, status }, // Request-Body
        { headers: authHeader() } // Authentifizierungs-Header
    );
};

// const updateStatus = (beschwerdeId, status) => {
//     return axios.put(
//         `${API_URL}beschwerde/${beschwerdeId}/status`, // URL mit der Beschwerde-ID
//         { status }, // Request-Body
//         { headers: authHeader() } // Authentifizierungs-Header
//     );
// };

const deleteAccount = (id, buerger) => {
    if (buerger) { // Role ist true falls Bürger angemeldet
        return axios
            .delete(API_URL + "buerger-loeschen/" + id, { headers: authHeader() })
            .then((response) => {
            console.log(response.data)
            return response.data;
        })
    } else {
        return axios
            .delete(API_URL + "mitarbeiter-loeschen/" + id, { headers: authHeader() })
            .then((response) => {
                return response.data;
            })
    }

};

const getInfo = (id, buerger) => {
    if (buerger) { // Role ist true falls Bürger angemeldet
        return axios
            .get(API_URL + "buerger-information/" + id, { headers: authHeader() })
            .then((response) => {
                console.log(response.data)
                return response.data;
            })
    } else {
        return axios
            .get(API_URL + "mitarbeiter-information/" + id, { headers: authHeader() })
            .then((response) => {
                return response.data;
            })
    }
};

export default {
    //getPublicContent,
    getBuergerDashBoard,
    getMitarbeiterBoard,
    getBeschwerde,
    postBeschwerde,
    updateBeschwerde,
    deleteAccount,
    getInfo
};