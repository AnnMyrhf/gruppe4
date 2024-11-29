import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import Beschwerde from "../components/beschwerde";

export default function MitarbeiterDashboard() {
    const [beschwerden, setBeschwerden] = useState([]);

    useEffect(() => {
        fetch("http://localhost:8081/beschwerde/getAll")
            .then((response) => response.json())
            .then((data) => {
                setBeschwerden(data);
                console.log(data);
            });
    }, []);

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
                <p>Keine Beschwerden vorhanden</p>
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
        </main>
    );
}
