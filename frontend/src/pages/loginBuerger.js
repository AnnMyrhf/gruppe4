import React, { useState } from 'react';
import '../style/registerStyle.css'
import { useNavigate } from 'react-router-dom';

const LoginForm = () => {
  const [formData, setFormData] = useState({
    email: '',
    passwort: '',
  });

  const navigate = useNavigate();

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

    if (true) {
        navigate('/buerger/dashboard', { replace: true });
    } 

    // e.preventDefault();
    // const formErrors = {};
    // Object.keys(formData).forEach(key => {
    //   const error = validateField(key, formData[key]);
    //   if (error) {
    //     formErrors[key] = error;
    //   }
    // });
    // if (Object.keys(formErrors).length === 0) {
    //     // Form is valid, you can submit the data
    //     console.log('Form submitted:', formData);
    //     // Here you would typically send the data to your backend
    //     fetch('http://localhost:8081/buerger-anmelden', {
    //   method: 'POST',
    //   headers: {
    //     'Content-Type': 'application/json', // Header für JSON-Inhalt
    //   },
    //   body: JSON.stringify(formData), // Verwende das aktualisierte Mitarbeiter-Objekt
    // })
    //   .then((response) => response.text()) // Antwort als Text umwandeln
    //   .then((data) => {
    //     console.log('Server Response:', data);
    //     if (true) {
    //         navigate('/neue-seite');
    //     } 
    //   })
    //   } 
     };  

  return (
    <div className="registration-container">
      <form onSubmit={handleSubmit} className="registration-form">
        <h2>Einloggen</h2>
        <h4>Loggen Sie sich ein, um Zugriff auf Ihre Beschwerden zu haben.</h4>
        
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
        </div>

        <button type="submit">Einloggen</button>
        {submitError && <div className="error submit-error">{submitError}</div>}
      </form>
    </div>
  );
};

export default LoginForm;

