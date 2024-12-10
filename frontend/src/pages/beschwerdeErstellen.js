import React, { useState } from 'react';
import '../styles/registerStyle.css'
import { useNavigate } from 'react-router-dom';

const BeschwerdeForm = () => {
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

  const navigate = useNavigate();

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
    <div className="registration-container">
      <form onSubmit={handleSubmit} className="registration-form">
        <h2>Neue Beschwerde einreichen</h2>
        <h4>Teilen Sie uns Ihre Anliegen mit und senden optional eine Datei oder ein Foto als Anhang.</h4>
        
        <div className="form-group">
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

        <div className="form-group">
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

        <button type="submit">Abschicken</button>
      </form>
    </div>
  );
};

export default BeschwerdeForm;

