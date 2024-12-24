import React, {useEffect, useState} from 'react';
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
    passwort: '',
    abteilung: '',
    position: ''
  });

  const [confirmPasswort, setConfirmPasswort] = useState("")


  const navigate = useNavigate();
  const dispatch = useDispatch();
  const [selectedRole, setSelectedRole] = useState("Bürger");
  const [showToast, setShowToast] = useState(false);
  const [toastMessage, setToastMessage] = useState('');
  const [toastStatus, setToastStatus] = useState('');
  const { user: currentUser } = useSelector((state) => state.auth);
  const [validation, setValidation] = useState({})

  useEffect(() => {
    if (Object.keys(validation).length > 0) {
      handleShowToast("Registrierung nicht erfolgreich", "error");
      console.log(validation);
    }
  }, [validation]);

  if (currentUser) {
    return <Navigate to="/dashboard" />;
  }

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prevState => ({
      ...prevState,
      [name]: value
    }));

    if (validation[name]) {
      setValidation(prevValidation => {
        const updatedValidation = { ...prevValidation };
        delete updatedValidation[name]; // Fehler für das aktuelle Feld entfernen
        return updatedValidation;
      });
    }
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
            handleShowToast("Registrierung erfolgreich!", "success");
            setTimeout(() => navigate("/"), 5000);
          })
          .catch((error) => {
            setValidation(error.errors)
          });
    } else if (selectedRole === "Mitarbeiter"){
      dispatch(mitarbeiterRegister(formData.anrede,
          formData.vorname,
          formData.nachname,
          formData.telefonnummer,
          formData.email,
          formData.passwort
          ))
          .then(() => {
            handleShowToast("Registrierung erfolgreich!");
            setTimeout(() => navigate("/"), 1500);
          })
          .catch((error) => {
            setValidation(error.errors)
          });
    }
  };



  const handleShowToast = (message, status) => {
    setToastMessage(message);
    setToastStatus(status);
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
          <form onSubmit={handleSubmit} className="form">
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
              <div className="input-wrapper">
                <input
                    type="text"
                    id="anrede"
                    name="anrede"
                    value={formData.anrede}
                    onChange={handleChange}
                    required
                    className={validation.anrede ? "validation" : ""}
                />
                {validation.anrede && (
                    <p className="validation-flyout">{validation.anrede}</p>
                )}
              </div>
            </div>

            <div className="lvg">
              <label htmlFor="vorname">Vorname <span className="required">*</span></label>
              <div className="input-wrapper">
                <input
                    type="text"
                    id="vorname"
                    name="vorname"
                    value={formData.vorname}
                    onChange={handleChange}
                    required
                    className={validation.vorname ? "validation" : ""}
                />
                {validation.vorname && (
                    <p className="validation-flyout">{validation.vorname}</p>
                )}
              </div>
            </div>

            <div className="lvg">
              <label htmlFor="nachname">Nachname <span className="required">*</span></label>
              <div className="input-wrapper">
                <input
                    type="text"
                    id="nachname"
                    name="nachname"
                    value={formData.nachname}
                    onChange={handleChange}
                    required
                    className={validation.nachname ? "validation" : ""}
                />
                {validation.nachname && (
                    <p className="validation-flyout">{validation.nachname}</p>
                )}
              </div>
            </div>

            <div className="lvg">
              <label htmlFor="telefonnummer">Telefonnummer <span className="required">*</span></label>
              <div className="input-wrapper">
                <input
                    type="text"
                    id="telefonnummer"
                    name="telefonnummer"
                    value={formData.telefon}
                    onChange={handleChange}
                    required
                    className={validation.telefonnummer ? "validation" : ""}
                />
                {validation.telefonnummer && (
                    <p className="validation-flyout">{validation.telefonnummer}</p>
                )}
              </div>
            </div>

            <div className="lvg">
              <label htmlFor="email">E-Mail-Adresse <span className="required">*</span></label>
              <div className="input-wrapper">
                <input
                    type="text"
                    id="email"
                    name="email"
                    value={formData.email}
                    onChange={handleChange}
                    required
                    className={validation.email ? "validation" : ""}
                />
                {validation.email && (
                    <p className="validation-flyout">{validation.email}</p>
                )}
              </div>
            </div>

            <div className="lvg">
              <label htmlFor="passwort">Passwort <span className="required">*</span></label>
              <div className="input-wrapper">
                <input
                    type="password"
                    id="passwort"
                    name="passwort"
                    value={formData.passwort}
                    onChange={handleChange}
                    required
                    className={validation.passwort ? "validation" : ""}
                />
                {validation.passwort && (
                    <p className="validation-flyout">{validation.passwort}</p>
                )}
              </div>
            </div>

            <div className="lvg">
              <label htmlFor="confirmPasswort">Passwort bestätigen <span className="required">*</span></label>
              <div className="input-wrapper">
                <input
                    type="password"
                    id="confirmPasswort"
                    name="confirmPasswort"
                    value={confirmPasswort}
                    onChange={handleConfirmChange}
                    required
                />
              </div>
            </div>

            <div style={{
              position: 'relative',
              display: "flex",
              flexDirection: "column",
              gap: "8px",
            }}>
              <button type="submit">{selectedRole} Konto erstellen</button>
              <p style={{
                width: "100%",
                textAlign: "center",
                color: "#808080",
                fontSize: "14px"
              }}> Sie haben bereits einen Account? <Link to="/">Anmelden</Link></p>
            </div>


          </form>
        </div>
        <Toaster text={toastMessage} visible={showToast} status={toastStatus}/>
      </div>
  );
};

export default RegistrationForm;

