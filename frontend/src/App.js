import './App.css';
import './style/styles.css';
import "bootstrap/dist/css/bootstrap.min.css";
import React, { useState, useEffect, useCallback } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Routes, Route, Link, useLocation } from "react-router-dom";

import Login from "./pages/loginBuerger";
import RegisterBuerger from "./pages/registrierenBuerger";

import Profile from "./components/Profile";
import { logout } from "./actions/auth-buerger";
import { clearMessage } from "./actions/message";
import BuergerDashBoard from "./components/BuergerDashBoard";
import MitarbeiterDashBoard from "./components/MitarbeiterDashBoard";

export default function App(){
    const [setShowBuergerDashBoard, setShowModeratorBoard] = useState(false);
    const [setShowMitarbeiterDashBoard, setShowAdminBoard] = useState(false);

    const { user: currentUser } = useSelector((state) => state.auth);
    const dispatch = useDispatch();

    let location = useLocation();

    useEffect(() => {
        if (["/buerger-anmelden", "/buerger-registrieren"].includes(location.pathname)) {
            dispatch(clearMessage()); // clear message when changing location
        }
    }, [dispatch, location]);

    const logOut = useCallback(() => {
        dispatch(logout());
    }, [dispatch]);

    useEffect(() => {
        if (currentUser) {
            //setShowBuergerDashBoard(currentUser.roles.includes("BUERGER"));
            //setShowMitarbeiterDashBoard(currentUser.roles.includes("MITARBEITER"));
        } else {
            //setShowBuergerDashBoard(false);
            //setShowMitarbeiterDashBoard(false);
        }
    }, [currentUser]);

    return (
        <div>
            {/*<nav className="navbar navbar-expand navbar-dark bg-dark">
                <Link to={"/"} className="navbar-brand">
                    bezKoder
                </Link>
                <div className="navbar-nav mr-auto">
                    <li className="nav-item">
                        <Link to={"/home"} className="nav-link">
                            Home
                        </Link>
                    </li>

                    {setShowBuergerDashBoard && (
                        <li className="nav-item">
                            <Link to={"/buerger/dashboard"} className="nav-link">
                                Bürger-Dashboard
                            </Link>
                        </li>
                    )}

                    {setShowMitarbeiterDashBoard && (
                        <li className="nav-item">
                            <Link to={"/mitarbeiter/dashboard"} className="nav-link">
                                Mitarbeiter-Dashboard
                            </Link>
                        </li>
                    )}

                        <li className="nav-item">
                            <a href="/buerger-anmelden" className="nav-link" onClick={logOut}>
                                LogOut
                            </a>
                        </li>
                    </div>
                ) : (
                    <div className="navbar-nav ml-auto">
                        <li className="nav-item">
                            <Link to={"/buerger-anmelden"} className="nav-link">
                                Login für Bürger
                            </Link>
                        </li>

                        <li className="nav-item">
                            <Link to={"/buerger-registrieren"} className="nav-link">
                                Registrierung für Bürger
                            </Link>
                        </li>
                    </div>
              </nav>*/}

            <div className="container mt-3">
                <Routes>
                    <Route path="/buerger-anmelden" element={<Login />} />
                    <Route path="/buerger-registrieren" element={<RegisterBuerger/>}/>
                    <Route path="/buerger" element={<Profile />}/>
                    <Route path="/mitarbeiter/dashboard" element={<h1>Anmelde</h1>}/>
                </Routes>
            </div>

        </div>
    );
};

