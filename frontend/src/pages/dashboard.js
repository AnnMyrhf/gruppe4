import React, {useEffect, useState, useCallback} from "react";
import {Link, Navigate, useNavigate} from 'react-router-dom';
import { useSelector, useDispatch } from "react-redux";
import UserService from "../services/user.service"
import Beschwerde from "../components/beschwerde";

const Dashboard = () => {

    const [beschwerden, setBeschwerden] = useState([]);

    const dispatch = useDispatch();
    const { user: currentUser } = useSelector((state) => state.auth);
    const navigate = useNavigate();

    useEffect(() => {
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
        }


    }, []);

    const handleClick = (e) => {
        if (true) {
            navigate('/neuebeschwerde', { replace: true });
        }
    }

    const mainStyle = {
        width: "100%",
        maxWidth: "1000px",
        display: "flex",
        flexDirection: "column",
        gap: "32px",
    };

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
            <main style={mainStyle}>
                <div>
                    <div style={{
                        display: "flex",
                        justifyContent: "space-between"
                    }}>
                        <h1>ABC Dashboard</h1>
                        {currentUser && currentUser.role.some(item => item.authority === 'BUERGER') && <button className="primary-btn" onClick={handleClick}>Neue Beschwerde</button>}
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
                    <h2>Offene Beschwerden</h2>
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
