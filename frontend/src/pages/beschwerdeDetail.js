import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";

export default function BeschwerdeDetail() {
    const { id } = useParams(); // ID aus der URL holen
    const [beschwerde, setBeschwerde] = useState(null);

    console.log(id)
    useEffect(() => {
        fetch(`http://localhost:8081/beschwerde/${id}`) // Hol die spezifische Beschwerde über die ID
            .then((response) => response.json())
            .then((data) => setBeschwerde(data));
    }, [id]);

    if (!beschwerde) {
        return <p>Lade Beschwerde...</p>;
    }

    return (
        <div>
            <h1>Beschwerde Details</h1>
            <p><strong>ID:</strong> {beschwerde.id}</p>
            <p><strong>Beschreibung:</strong> {beschwerde.beschreibung}</p>
            <p><strong>Datum:</strong> {beschwerde.datum}</p>
            <p><strong>Status:</strong> {beschwerde.status}</p>
            {/* Weitere Details je nach Bedarf */}
        </div>
    );
}
