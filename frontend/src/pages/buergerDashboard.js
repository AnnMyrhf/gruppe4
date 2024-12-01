import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import Beschwerde from "../components/beschwerde";
import { useNavigate } from 'react-router-dom';

export default function BuergerDashboard() {
    const navigate = useNavigate();

    const [beschwerden, setBeschwerden, setAnzahlBeschwerden] = useState([]);

    useEffect(() => {
        fetch("http://localhost:8081/buerger/dashboard/{buerger}")
            .then((response) => response.json())
            .then((data) => {
              //  setAnzahlBeschwerden(response.data);
                console.log(data);
            });
    }, []);

    const handleClick = (e) => {

        if (true) {
            navigate('/neuebeschwerde', { replace: true });
        } 
    } 

    const handleClick2 = (e) => {

        if (true) {
            navigate('/buerger/dashboard', { replace: true });
        } 
    } 

    // useEffect(() => {
    //         fetch('http://localhost:8081/beschwerde/findByBuerger', {
    //             method: 'POST',
    //             headers: {
    //               'Content-Type': 'application/json', // Header für JSON-Inhalt
    //             },
    //             body: JSON.stringify(1), // Verwende das aktualisierte Mitarbeiter-Objekt
    //           })
    //             .then((response) => response.text()) // Antwort als Text umwandeln
    //             .then((data) => {
    //                 setBeschwerden(data);
    //                 console.log('Server Response:', data);
    //             })
    // }, []);

    // Styles
    const mainStyle = {
        height: "100%",
        margin: 0,
        display: "flex",
        flexDirection: "column",
        justifyContent: "start",
        alignItems: "center",
        backgroundColor: "#F2F6FE",
        gap: "32px",
        padding: "64px",
    };

    const cardStyle = {
        display: "flex",
        flexDirection: "column",
        width: "100%",
        maxWidth: "1200px",
        border: "1px solid #ddd",
        padding: "32px",
        borderRadius: "8px",
        backgroundColor: "#ffffff",
        boxShadow: "0 2px 10px rgba(0, 0, 0, 0.1)",
        gap: "32px",
        textDecoration: "none",
        color: "inherit"

    };

    return (
        <main style={mainStyle}>
            {beschwerden.length === 0 ? (
                // Anzahl Beschwerden einfügen
                <p>Beschwerden vorhanden</p>
            ) : (
                beschwerden.map((beschwerde) => (
                    <Link
                        key={beschwerde.id}
                        to={`/mitarbeiter/dashboard/${beschwerde.id}`} // Navigiere zur Detail-Seite mit ID
                        state={{ beschwerden }} // Übergebe die Beschwerden als State
                        style={cardStyle}
                    >
                        <Beschwerde beschwerde={beschwerde} />
                    </Link>
                ))
            )}

<button type="button" onClick={handleClick}>Neue Beschwerde anlegen</button>
<button type="button" onClick={handleClick2}>Beschwerde Übersicht anzeigen</button>
        </main>
    );
}
