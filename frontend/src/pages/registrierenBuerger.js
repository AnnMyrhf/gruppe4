import React, { useState } from 'react';

const RegistrationForm = () => {
  const [formData, setFormData] = useState({
    anrede: '',
    vorname: '',
    nachname: '',
    telefonnummer: '',
    email: '',
    passwort: '',
  });

  const [errors, setErrors] = useState({});
  const [submitError, setSubmitError] = useState('');

  const validateField = (name, value) => {
    const emailRegex = /^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/;
    const telefonnummerRegex = /^\d+$/;
    const passwortRegEx = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;

    switch (name) {
      case 'email':
        return emailRegex.test(value) ? '' : 'Ungültige E-Mail-Adresse';
      case 'telefonnummer':
        return telefonnummerRegex.test(value) ? '' : 'Telefonnummer darf nur Zahlen enthalten';
      case 'passwort':
        return passwortRegEx.test(value) ? '' : 'Passwort muss mindestens 8 Zeichen lang sein und mindestens einen Buchstaben, eine Zahl und ein Sonderzeichen enthalten';
      case 'confirmPassword':
        return value === formData.passwort ? '' : 'Passwörter stimmen nicht überein';
      default:
        return '';
    }
};

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prevState => ({
      ...prevState,
      [name]: value
    }));

    const error = validateField(name, value);
    setErrors(prevErrors => ({
      ...prevErrors,
      [name]: error
    }));
    console.log(formData)
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const formErrors = {};
    Object.keys(formData).forEach(key => {
      const error = validateField(key, formData[key]);
      if (error) {
        formErrors[key] = error;
      }
    });
    if (Object.keys(formErrors).length === 0) {
        // Form is valid, you can submit the data
        console.log('Form submitted:', formData);
        // Here you would typically send the data to your backend
        fetch('http://localhost:8081/registriere-buerger', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json', // Header für JSON-Inhalt
      },
      body: JSON.stringify(formData), // Verwende das aktualisierte Mitarbeiter-Objekt
    })
      .then((response) => response.text()) // Antwort als Text umwandeln
      .then((data) => {
        console.log('Server Response:', data);
      })
      .catch((error) => console.error('Error sending input:', error));
    console.log('Form submitted:', formData);
        // For demonstration purposes, we'll just show a success message
        setSubmitError('');
        alert('Registrierung erfolgreich!');
      } else {
        // Form has errors
        setErrors(formErrors);
        setSubmitError('Bitte korrigieren Sie die Fehler im Formular.');
      }
    };  

  return (
    <div className="registration-container">
      <form onSubmit={handleSubmit} className="registration-form">
        <h2>Registrieren</h2>
        <h4>Erstellen Sie ein Konto, um Zugang zu weiteren Funktionen zu erhalten.</h4>
        
        <div className="form-group">
          <label htmlFor="anrede">Anrede <span className="required">*</span></label>
          <input 
            type="text" 
            id="anrede" 
            name="anrede" 
            value={formData.anrede}
            onChange={handleChange}
            required 
          />
        </div>

        <div className="form-group">
          <label htmlFor="vorname">Vorname <span className="required">*</span></label>
          <input 
            type="text" 
            id="vorname" 
            name="vorname" 
            value={formData.vorname}
            onChange={handleChange}
            required 
          />
        </div>

        <div className="form-group">
          <label htmlFor="nachname">Nachname <span className="required">*</span></label>
          <input 
            type="text" 
            id="nachname" 
            name="nachname" 
            value={formData.nachname}
            onChange={handleChange}
            required 
          />
        </div>

        <div className="form-group">
          <label htmlFor="telefonnummer">Telefonnummer <span className="required">*</span></label>
          <input 
            type="text" 
            id="telefonnummer" 
            name="telefonnummer" 
            value={formData.telefon}
            onChange={handleChange}
            required 
          />
          {errors.telefonnummer && <span className="error">{errors.telefonnummer}</span>}
        </div>

        <div className="form-group">
          <label htmlFor="email">E-Mail-Adresse <span className="required">*</span></label>
          <input 
            type="text" 
            id="email" 
            name="email" 
            value={formData.email}
            onChange={handleChange}
            required 
          />
           {errors.email && <span className="error">{errors.email}</span>}
        </div>

        <div className="form-group">
          <label htmlFor="passwort">Passwort <span className="required">*</span></label>
          <input 
            type="password" 
            id="passwort" 
            name="passwort" 
            value={formData.passwort}
            onChange={handleChange}
            required 
          />
           {errors.passwort && <span className="error">{errors.passwort}</span>}
        </div>

        <div className="form-group">
          <label htmlFor="confirmPassword">Passwort bestätigen <span className="required">*</span></label>
          <input 
            type="password" 
            id="confirmPassword" 
            name="confirmPassword" 
            value={formData.confirmPassword}
            onChange={handleChange}
            required 
          />
          {errors.confirmPassword && <span className="error">{errors.confirmPassword}</span>}
        </div>

        <button type="submit">Registrieren</button>
        {submitError && <div className="error submit-error">{submitError}</div>}
      </form>
    </div>
  );
};

export default RegistrationForm;

