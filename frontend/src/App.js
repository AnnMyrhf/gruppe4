import './styles/globalStyles.css';
import React, { useState, useEffect, useCallback } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Routes, Route, Link, useLocation } from "react-router-dom";

import Login from "./pages/login";
import Register from "./pages/registrieren";

import Dashboard from "./pages/dashboard";
import Header from "./components/Header";

export default function App(){

    const { user: currentUser } = useSelector((state) => state.auth);

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
                <Route path="/anmelden" element={<Login />} />
                <Route path="/registrieren" element={<Register/>}/>
                <Route path="/dashboard" element={<Dashboard />}/>
                <Route path="/mitarbeiter/dashboard" element={<h1>Anmelde</h1>}/>
            </Routes>
        </div>


    );
};

