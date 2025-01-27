import React, {useEffect, useState, useCallback} from "react";
import {Link, Navigate, useNavigate} from 'react-router-dom';
import { useSelector, useDispatch } from "react-redux";
import UserService from "../services/user.service"
import Beschwerde from "../components/beschwerde";
// Holt die Daten (Beschwerden) aus dem backend und zeigt sie auf einem Dashboard
// @author Maik Bartels, Katja Schneider
const Dashboard = () => {

    const [beschwerden, setBeschwerden] = useState([]);
    const [name, setName] = useState("User");
    const { user: currentUser } = useSelector((state) => state.auth);
    const navigate = useNavigate();

    // Api call Beschwerden
    useEffect(() => {
        //Abfrage ob Bürger oder Mitarbeiter
        if (currentUser.role.some(item => item.authority === 'BUERGER')) {
            console.log("BUERGER ist vorhanden!");
            UserService.getBuergerDashBoard(currentUser.id).then(
                (response) => {
                    setBeschwerden(response.data);
                },
                (error) => {
                    const _content =
                        (error.response &&
                            error.response.data &&
                            error.response.data.message) ||
                        error.message ||
                        error.toString();
                    setBeschwerden(_content);
                }
            );
            UserService.getInfo(currentUser.id, true).then(
                (response) => {
                    // setBeschwerden(response.data);
                    setName(response.vorname)
                    console.log(response)
                },
                (error) => {
                    const _content =
                        (error.response &&
                            error.response.data &&
                            error.response.data.message) ||
                        error.message ||
                        error.toString();
                    console.log("Error")

                }
            );
        } else if (currentUser.role.some(item => item.authority === 'MITARBEITER')){
            UserService.getMitarbeiterBoard().then(
                (response) => {
                    console.log(response)
                    setBeschwerden(response.data);
                },
                (error) => {
                    const _content =
                        (error.response &&
                            error.response.data &&
                            error.response.data.message) ||
                        error.message ||
                        error.toString();
                    setBeschwerden(_content);
                }
            );
            UserService.getInfo(currentUser.id, false).then(
                (response) => {
                    // setBeschwerden(response.data);
                    setName(response.vorname)
                    console.log(response)
                },
                (error) => {
                    const _content =
                        (error.response &&
                            error.response.data &&
                            error.response.data.message) ||
                        error.message ||
                        error.toString();
                    console.log("Error")

                }
            );
        }
    }, []);

    // klick auf neue Beschwerde Button
    const handleClick = () => {
        navigate('/neuebeschwerde');
    }

    // Dashboard markup
    return (
        <div style={{
            width: "100%",
            backgroundColor: "#F2F6FE",
            padding: "64px",
            display: "flex",
            alignItems: "center",
            flexDirection: "column",
            flexGrow: "1"
        }}>
            <main className="main">
                <div>
                    <div className="dashboard-head">
                        <h1>{name}s Dashboard</h1>
                        {currentUser && currentUser.role.some(item => item.authority === 'BUERGER') && <button className="primary-btn neuebeschwerde-btn" onClick={handleClick}>Neue Beschwerde</button>}
                    </div>
                    <div>

                    </div>
                </div>

                <div style={{
                    display: "flex",
                    flexDirection: "column",
                    gap: "12px",
                    width: "100%"
                }}>
                    <h2>Beschwerden</h2>
                    {
                        beschwerden.length === 0 ? (
                            <p>Keine Beschwerden vorhanden</p>
                        ) : (
                            beschwerden.map((beschwerde) => (
                                    <Link
                                        key={beschwerde.id}
                                        to={`/dashboard/${beschwerde.id}`}
                                        style={{
                                            textDecoration: "none",
                                            color: "inherit"
                                        }}
                                    >
                                        <Beschwerde beschwerde={beschwerde}/>
                                    </Link>
                                )
                            )
                        )
                    }
                </div>
            </main>
        </div>

    );
};

export default Dashboard;
