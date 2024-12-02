import axios from "axios";
import authHeader from "./auth-header";

const API_URL = "http://localhost:8081/";

/*const getPublicContent = () => {
    return axios.get(API_URL + "start");
};*/

const getBuergerDashBoard = () => {
    return axios.get(API_URL + "buerger/getBeschwerden", { headers: authHeader() });
};

const getMitarbeiterBoard = () => {
    return axios.get(API_URL + "mitarbeiter/dashboard", { headers: authHeader() });
};

export default {
    //getPublicContent,
    getBuergerDashBoard,
    getMitarbeiterBoard
};