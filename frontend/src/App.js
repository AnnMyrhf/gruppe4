import './styles/App.css';
import './styles/globalStyles.css';
import React, { useState, useEffect, useCallback } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Routes, Route, Link, useLocation } from "react-router-dom";

import Login from "./pages/login";
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
        <div className="app" style={{
            width: "100%",
            height: "100%"
        }}
        >
            <Routes>
                <Route path="/" element={<Login />} />
                <Route path="/buerger-anmelden" element={<Login />} />
                <Route path="/buerger-registrieren" element={<RegisterBuerger/>}/>
                <Route path="/buerger" element={<Profile />}/>
                <Route path="/mitarbeiter/dashboard" element={<h1>Anmelde</h1>}/>
            </Routes>
        </div>


    );
};

