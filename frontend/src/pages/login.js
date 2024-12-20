import React, {useEffect, useState} from 'react';
import { useDispatch, useSelector } from "react-redux";
import '../styles/loginStyle.css'
import {Link, Navigate, useNavigate} from 'react-router-dom';
import { buergerLogin } from "../actions/auth-buerger";
import { mitarbeiterLogin } from "../actions/auth-mitarbeiter";
import buergerIcon from "../assests/people-group-solid.svg";
import mitarbeiterIcon from "../assests/user-tie-solid.svg";
import decorationIMG from "../assests/FeedbackIMG.png"
import Toaster from "../components/Toaster";


const LoginForm = () => {
    const [formData, setFormData] = useState({
    email: '',
    passwort: '',
  });

const [validation, setValidation] = useState(null)

  const navigate = useNavigate();
  const dispatch = useDispatch();
  const [selectedRole, setSelectedRole] = useState("Bürger");
  const { user: currentUser } = useSelector((state) => state.auth);
  const [showToast, setShowToast] = useState(false);
  const [toastMessage, setToastMessage] = useState('');

  useEffect(() => {
    if (validation !== null) {
      handleShowToast(validation);
    }
  }, [validation]);

  if (currentUser) {
    return <Navigate to="/dashboard" />;
  }



  const handleShowToast = (message) => {
    setToastMessage(message);
    setShowToast(true);
    // Hier nach 3,5 Sekunden wieder auf false setzen, damit der Toast beim nächsten Mal neu
    // angezeigt werden kann.
    setTimeout(() => setShowToast(false), 3500);
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setValidation(null)
    setFormData(prevState => ({
      ...prevState,
      [name]: value
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (selectedRole === "Bürger"){
      dispatch(buergerLogin(formData.email, formData.passwort))
          .then(() => {
            navigate("/dashboard");
          })
          .catch((error) => {
            setValidation(error.errors)
            console.log(error)
          });
    } else if (selectedRole === "Mitarbeiter"){
      dispatch(mitarbeiterLogin(formData.email, formData.passwort))
          .then(() => {
            console.log("Mitarbeiter Erfolgreich")
            navigate("/dashboard");
          })
          .catch((error) => {
            setValidation(error.errors)
            console.log(error)
          });
    }
  };

  const handleRoleChange = (role) => {
    setValidation(null)
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
            <h1>Anmelden</h1>
            <p className="subinfo">Melden Sie sich an, um eine Beschwerde abzuschicken.</p>
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
              <label htmlFor="email">E-Mail Adresse<span className="required">*</span></label>
              <input
                  type="text"
                  id="email"
                  name="email"
                  value={formData.email}
                  onChange={handleChange}
                  required
                  className={validation ? "validation" : ""}
              />
            </div>

            <div className="lvg">
              <label htmlFor="passwort">Passwort<span className="required">*</span></label>
              <input
                  type="password"
                  id="passwort"
                  name="passwort"
                  value={formData.passwort}
                  onChange={handleChange}
                  required
                  className={validation ? "validation" : ""}
              />
            </div>

            <div style={{
              position: 'relative',
              display: "flex",
              flexDirection: "column",
              gap: "8px",
            }}>
              <button type="submit">Als {selectedRole} anmelden</button>
              <p style={{
                width: "100%",
                textAlign: "center",
                color: "#808080",
                fontSize: "14px"
              }}> Sie haben noch keinen Account? <Link to="/registrieren">Registrieren</Link></p>
            </div>
          </form>
        </div>
        <Toaster text={toastMessage} visible={showToast}/>
      </div>
  );
};

export default LoginForm;

