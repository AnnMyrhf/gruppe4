import './styles/globalStyles.css';
import './styles/validationStyle.css';
import React, { useState, useEffect, useCallback } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Routes, Route, Link, useLocation } from "react-router-dom";

import Login from "./pages/login";
import Register from "./pages/registrieren";
import Dashboard from "./pages/dashboard";
import Header from "./components/Header";
import BeschwerdeDetail from "./pages/beschwerdeDetail";
import NeueBeschwerde from "./pages/neueBeschwerde";

// Routet die Anwendung, zeigt abhängig der URL die richtige Seite an. Header wird nur angezeigt, wenn User angemeldet
// @author Maik Bartels
export default function App(){

    // testen, ob User angemeldet
    const { user: currentUser } = useSelector((state) => state.auth);

    // App markup
    // React Router um Layout festzulegen
    // Header nur wenn User angemeldet
    return (
        <div className="app" style={{
            display: "flex",
            flexDirection: "column",
            width: "100%",
            height: "100%"
        }}
        >
            {currentUser && <Header />}
            <Routes>
                <Route path="/" element={<Login />} />
                <Route path="/registrieren" element={<Register/>}/>
                <Route path="/dashboard" element={<Dashboard />}/>
                <Route path="/dashboard/:id" element={<BeschwerdeDetail />} />
                <Route path="/neuebeschwerde" element={<NeueBeschwerde />} />
            </Routes>
        </div>

    );
};

