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

export default {
    //getPublicContent,
    getBuergerDashBoard,
    getMitarbeiterBoard,
    getBeschwerde
};