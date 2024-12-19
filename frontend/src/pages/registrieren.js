import React, { useState } from 'react';
import {buergerRegister} from "../actions/auth-buerger";
import {Link, Navigate, useNavigate} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import decorationIMG from "../assests/FeedbackIMG.png";
import buergerIcon from "../assests/people-group-solid.svg";
import mitarbeiterIcon from "../assests/user-tie-solid.svg";
import {mitarbeiterRegister} from "../actions/auth-mitarbeiter";
import Toaster from "../components/Toaster";

const RegistrationForm = () => {
  const [formData, setFormData] = useState({
    anrede: '',
    vorname: '',
    nachname: '',
    telefonnummer: '',
    email: '',
    passwort: ''
  });

  const [confirmPasswort, setConfirmPasswort] = useState("")


  const navigate = useNavigate();
  const dispatch = useDispatch();
  const [selectedRole, setSelectedRole] = useState("Bürger");
  const [showToast, setShowToast] = useState(false);
  const [toastMessage, setToastMessage] = useState('');

  const { user: currentUser } = useSelector((state) => state.auth);

  if (currentUser) {
    return <Navigate to="/dashboard" />;
  }

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prevState => ({
      ...prevState,
      [name]: value
    }));
  };

  const handleConfirmChange = (e) =>{
    setConfirmPasswort(e.target.value);
  }

  const handleSubmit = (e) => {
    e.preventDefault();
    if (selectedRole === "Bürger"){
      dispatch(buergerRegister(formData.anrede,
          formData.vorname,
          formData.nachname,
          formData.telefonnummer,
          formData.email,
          formData.passwort))
          .then(() => {
            handleShowToast("Registrierung erfolgreich!");
            setTimeout(() => navigate("/"), 5000);
          })
          .catch((error) => {
            console.log(error)
            handleShowToast("Registrierung fehlgeschlagen!");
          });
    } else if (selectedRole === "Mitarbeiter"){
      dispatch(mitarbeiterRegister(formData.anrede,
          formData.vorname,
          formData.nachname,
          formData.telefonnummer,
          formData.email,
          formData.passwort,
          formData.abteilung,
          formData.position
          ))
          .then(() => {
            handleShowToast("Registrierung erfolgreich!");
            setTimeout(() => navigate("/"), 3500);
          })
          .catch((error) => {
            console.log(error)
            handleShowToast("Registrierung fehlgeschlagen!");

          });
    }
  };



  const handleShowToast = (message) => {
    setToastMessage(message);
    setShowToast(true);
    // Hier nach 3,5 Sekunden wieder auf false setzen, damit der Toast beim nächsten Mal neu
    // angezeigt werden kann.
    setTimeout(() => setShowToast(false), 3500);
  };

  const handleRoleChange = (role) => {
    setSelectedRole(role);
  };

  const handleKeyDown = (event, role) => {
    if (event.key === "Enter" || event.key === " ") {
      event.preventDefault(); // Verhindert Scrollen bei Leertaste
      handleRoleChange(role);
    }
  };

  return (
      <div className="login-container">
        <div className="login-decoration">
          <img src={decorationIMG} alt="We want your Feedback"/>
        </div>
        <div className="loginForm-container">
          <div className="loginForm-Header">
            <h1>Registrieren</h1>
            <p className="subinfo">Erstellen Sie ein Konto, um Beschwerden einzureichen.</p>
          </div>
          <form onSubmit={handleSubmit} className="login-form">
            <div className="lvg">
              <label htmlFor="rolle">Rolle</label>
              <div id="rolle" style={{display: "flex", gap: "8px"}}>
                <div onClick={() => handleRoleChange("Bürger")}
                     className={selectedRole === "Bürger" ? "segmentBtn-active" : "segmentBtn"}
                     tabIndex={selectedRole === "Mitarbeiter" ? "0" : "-1"}
                     onKeyDown={(event) => handleKeyDown(event, "Bürger")}
                >
                  <img src={buergerIcon} alt="Icon" width="auto" height="16"/>
                  Bürger
                </div>
                <div onClick={() => handleRoleChange("Mitarbeiter")}
                     className={selectedRole === "Mitarbeiter" ? "segmentBtn-active" : "segmentBtn"}
                     tabIndex={selectedRole === "Bürger" ? "0" : "-1"}
                     onKeyDown={(event) => handleKeyDown(event, "Mitarbeiter")}
                >
                  <img src={mitarbeiterIcon} alt="Icon" width="auto" height="16"/>
                  Mitarbeiter
                </div>
              </div>
            </div>

            <div className="lvg">
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

            <div className="lvg">
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

            <div className="lvg">
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

            <div className="lvg">
              <label htmlFor="telefonnummer">Telefonnummer <span className="required">*</span></label>
              <input
                  type="text"
                  id="telefonnummer"
                  name="telefonnummer"
                  value={formData.telefon}
                  onChange={handleChange}
                  required
              />
            </div>

            {selectedRole === "Mitarbeiter" && (<div className="lvg">
              <label htmlFor="abteilung">Abteilung <span className="required">*</span></label>
              <input
                  type="text"
                  id="abteilung"
                  name="abteilung"
                  value={formData.abteilung}
                  onChange={handleChange}
                  required
              />
            </div>)}

            {selectedRole === "Mitarbeiter" && (<div className="lvg">
              <label htmlFor="position">Position <span className="required">*</span></label>
              <input
                  type="text"
                  id="position"
                  name="position"
                  value={formData.position}
                  onChange={handleChange}
                  required
              />
            </div>)}

            <div className="lvg">
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

            <div className="lvg">
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

            <div className="lvg">
              <label htmlFor="confirmPasswort">Passwort bestätigen <span className="required">*</span></label>
              <input
                  type="password"
                  id="confirmPasswort"
                  name="confirmPasswort"
                  value={confirmPasswort}
                  onChange={handleConfirmChange}
                  required
              />
            </div>

            <button type="submit">{selectedRole} Konto erstellen</button>
            <p style={{
              width: "100%",
              textAlign: "center",
              margin: "-8px",
              color: "#808080",
              fontSize: "14px"
            }}> Sie haben noch keinen Account? <Link to="/">Anmelden</Link></p>
          </form>
        </div>
        <Toaster text={toastMessage} visible={showToast}/>
      </div>
  );
};

export default RegistrationForm;

