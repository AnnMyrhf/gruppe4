import React, { useState } from 'react';
import {Link, useNavigate} from 'react-router-dom';
import Beschwerde from "../components/beschwerde";

const BeschwerdeForm = () => {
  const navigate = useNavigate();
  const [file, setFile] = useState(null);
  const [formData, setFormData] = useState({
    text: '',
    erstellerId: '',
    beschwerdetyp: '',
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prevState => ({
      ...prevState,
      [name]: value
    }));
    console.log(formData)
  };

  const handleFileChange = (e) => {
    setFile(e.target.files[0]);
  };

 function handleDashboard(){
        navigate('/buerger/dashboard', { replace: true });
} 

  const handleSubmit = (e) => {
    e.preventDefault();
    fetch('http://localhost:8081/beschwerde-erstellen', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json', // Header für JSON-Inhalt
        },
        body: JSON.stringify(formData), // Verwende das aktualisierte Mitarbeiter-Objekt
      })
        .then((response) => response.text()) // Antwort als Text umwandeln
        .then((data) => {
          console.log('Server Response:', data);
          handleDashboard();
        })
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
        <main className="main">
          <div>
            <div style={{
              display: "flex",
              justifyContent: "space-between"
            }}>
              <h1>Neue Beschwerde einreichen</h1>
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
            <form onSubmit={handleSubmit} className="form">

              <div className="lvg">
                <label htmlFor="title">Titel<span className="required">*</span></label>
                <input
                    type="text"
                    id="title"
                    name="title"
                    value={formData.title}
                    onChange={handleChange}
                    required
                />
              </div>

              <div className="lvg">
                <label htmlFor="beschwerdetyp">Kategorie <span className="required">*</span></label>
                <select
                    id="beschwerdetyp"
                    name="beschwerdetyp"
                    value={formData.beschwerdetyp} // Der Wert des Dropdowns
                    onChange={handleChange}
                    required
                >
                  <option value="">-- Wählen Sie einen Typ --</option>
                  <option value="infrastruktur">Infrastruktur</option>
                  <option value="gebaeude">Gebäude</option>
                  <option value="lärm">Lärmbelästigung</option>
                  <option value="abfall">Müll und Abfall</option>
                  <option value="gruenflaechen">Grünflächen und Parks</option>
                  <option value="nahverkehr">Öffentlicher Nahverkehr</option>
                  <option value="verwaltung">Verwaltung und Service</option>
                  <option value="sonstiges">Sonstiges</option>
                </select>
              </div>

              <div className="lvg">
                <label htmlFor="textarea">Ihr Anliegen <span className="required">*</span></label>
                <input
                    type="textarea"
                    id="text"
                    name="text"
                    value={formData.text}
                    onChange={handleChange}
                    required
                />
              </div>

              <div className="lvg">
                <label htmlFor="file">Anhang (optional)</label>
                <input
                    type="file"
                    id="file"
                    name="file"
                    onChange={handleFileChange}
                    accept=".pdf,image/*"
                />
              </div>

              <button type="submit">Abschicken</button>
            </form>
          </div>
        </main>
      </div>
  );
};

export default BeschwerdeForm;

