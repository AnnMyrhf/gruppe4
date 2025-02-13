import React, { useState } from 'react';
import {Link, useNavigate} from 'react-router-dom';
import UserService from "../services/user.service"
import Toaster from "../components/Toaster";
import {useDispatch, useSelector} from "react-redux";
import backIcon from "../assests/arrow-left-solid.svg";

// Zeigt die Seite zum anlegen einer neuen Beschwerde und schickt diese an das backend
// @author Maik Bartels
const BeschwerdeForm = () => {
  // States
  const navigate = useNavigate();
  const [file, setFile] = useState(null);
  const [showToast, setShowToast] = useState(false);
  const [toastMessage, setToastMessage] = useState('');
  const [toastStatus, setToastStatus] = useState('');
  const { user: currentUser } = useSelector((state) => state.auth);
  const [formData, setFormData] = useState({
    buergerId: currentUser.id,
    textfeld: '',
    beschwerdeTyp: '',
    titel: ""
  });

  // Toaster Handler
  const handleShowToast = (message, status) => {
    setToastMessage(message);
    setToastStatus(status);
    setShowToast(true);
    // Hier nach 3,5 Sekunden wieder auf false setzen, damit der Toast beim nächsten Mal neu
    // angezeigt werden kann.
    setTimeout(() => setShowToast(false), 3500);
  };

  // Speichere Änderungen der Inputs
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prevState => ({
      ...prevState,
      [name]: value
    }));
    console.log(formData)
  };

  // Speichere hochgeladenes file separat
  const handleFileChange = (e) => {
    setFile(e.target.files[0]);
  };

  // Go to Dashboard Handler
 function goToDashboard(){
        navigate('/dashboard', { replace: true });
}


  const handleSubmit = (e) => {
    e.preventDefault();

    const data = new FormData(); // FormData Objekt erstellen
    data.append("buergerId", formData.buergerId);
    data.append("titel", formData.titel);
    data.append("beschwerdeTyp", formData.beschwerdeTyp);
    data.append("textfeld", formData.textfeld);

    if (file) {
      data.append("file", file); // Datei hinzufügen
    }

    // api call neue Beschwerde einreichen
    UserService.postBeschwerde(data).then(
        (response) => {
          handleShowToast("Beschwerde erfolgreich eingereicht", "success");
          setTimeout(() => goToDashboard(), 1500);
        },
        (error) => {
          const _content =
              (error.response &&
                  error.response.data &&
                  error.response.data.message) ||
              error.message ||
              error.toString();

          console.error("Fehler:", _content);
        }
    );
  };

  // neue Beschwerde markup
  return (
      <div style={{
        width: "100%",
        backgroundColor: "#F2F6FE",
        padding: "64px",
        display: "flex",
        alignItems: "center",
        flexDirection: "column",
        flexGrow: "1",
        position: 'relative'
      }}>
        <main className="main">
          <div style={{
            display: "flex",
            flexDirection: "column",
            gap: "16px",
            alignItems: "flex-start"
          }}>
            <button className="tertiaryBtn"
            onClick={goToDashboard}
            >
              <img src={backIcon} alt="Icon" width="auto" height="16"/>
              Zurück zum Dashboard
            </button>
            <h1>Neue Beschwerde einreichen</h1>
          </div>

          <div style={{
            display: "flex",
            flexDirection: "column",
            gap: "12px",
            width: "100%"
          }}>
            <form onSubmit={handleSubmit} className="form">

              <div className="lvg">
                <label htmlFor="titel">Titel<span className="required">*</span></label>
                <input
                    type="text"
                    id="titel"
                    name="titel"
                    value={formData.titel}
                    onChange={handleChange}
                    required
                />
              </div>

              <div className="lvg">
                <label htmlFor="beschwerdeTyp">Kategorie <span className="required">*</span></label>
                <select
                    id="beschwerdeTyp"
                    name="beschwerdeTyp"
                    value={formData.beschwerdeTyp} // Der Wert des Dropdowns
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
                <label htmlFor="textfeld">Ihr Anliegen <span className="required">*</span></label>
                <textarea
                    type="textarea"
                    id="textfeld"
                    name="textfeld"
                    value={formData.textfeld}
                    onChange={handleChange}
                    required
                    rows={5}
                    cols={50}
                />
              </div>

              <div className="lvg">
                <label htmlFor="file">Anhang (optional)</label>
                <input
                    type="file"
                    id="file"
                    name="file"
                    onChange={handleFileChange}
                    accept="image/*"
                    className="custom-file-input"
                />
              </div>

              <button type="submit">Abschicken</button>
            </form>
          </div>
          <Toaster text={toastMessage} visible={showToast} status={toastStatus}/>
        </main>
      </div>
  );
};

export default BeschwerdeForm;

