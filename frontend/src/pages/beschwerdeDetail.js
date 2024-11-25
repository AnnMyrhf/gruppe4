import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";

export default function BeschwerdeDetail() {
    const { id } = useParams(); // ID aus der URL holen
    const [beschwerde, setBeschwerde] = useState(null);

    useEffect(() => {
        fetch(`http://localhost:8081/beschwerde/${id}`) // Hol die spezifische Beschwerde über die ID
            .then((response) => response.json())
            .then((data) => {
                setBeschwerde(data)
                console.log(data)
            })
        ;
    }, [id]);

    if (!beschwerde) {
        return <p>Lade Beschwerde...</p>;
    }

    const mainStyle = {
        padding: "64px",
        display: "flex",
        flexDirection: "column",
        gap: "16px"
    };

    return (
        <div style={mainStyle}>
            <h1>Beschwerde Details</h1>
            <p>ID: {beschwerde.id}</p>
            <p>Beschreibung: {beschwerde.textfeld}</p>
            <p>Datum: {beschwerde.erstellDatum}</p>
            <p>Status: {beschwerde.status}</p>
            <p>Priorität: {beschwerde.prioritaet}</p>
            {/* Weitere Details je nach Bedarf */}
        </div>
    );
}
