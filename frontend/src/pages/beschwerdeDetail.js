import React, { useState, useEffect } from "react";
import {useNavigate, useParams} from "react-router-dom";

export default function BeschwerdeDetail() {
    const { id } = useParams(); // ID aus der URL holen
    const [beschwerde, setBeschwerde] = useState(null);
    const navigate = useNavigate();


    useEffect(() => {
        fetch(`http://localhost:8081/beschwerde/${id}`) // Hol die spezifische Beschwerde über die ID
            .then((response) => response.json())
            .then((data) => {
                setBeschwerde(data)
            }).catch((error)=>{
                console.log(error)
        })
        ;
    }, [id]);


    useEffect(() => {
       console.log(beschwerde)
    }, beschwerde);

    if (!beschwerde) {
        return <p>Lade Beschwerde...</p>;
    }

    const mainStyle = {
        padding: "64px",
        display: "flex",
        flexDirection: "column",
        gap: "16px"
    };

    const handleClick = ()=> {
        navigate('/dashboard');
    }

    return (
        <div style={mainStyle}>
            <button onClick={handleClick}>Zurück zum Dashboard</button>
            <h1>{beschwerde.titel}</h1>
            <p>ID: {beschwerde.id}</p>
            <p>Datum: {beschwerde.erstellDatum}</p>
            <p>Status: {beschwerde.status}</p>
            <p>Priorität: {beschwerde.prioritaet}</p>
            <p>Kategorie: {beschwerde.beschwerdeTyp}</p>
            <p>Beschreibung: {beschwerde.textfeld}</p>
        </div>
    );
}
