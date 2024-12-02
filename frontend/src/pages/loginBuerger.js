import React, { useState } from 'react';
import { useDispatch, useSelector } from "react-redux";
import '../style/registerStyle.css'
import { Navigate, useNavigate  } from 'react-router-dom';
import { login } from "../actions/auth-buerger";


const LoginForm = () => {
    const [formData, setFormData] = useState({
    email: '',
    passwort: '',
  });



  const navigate = useNavigate();
  const { isLoggedIn } = useSelector(state => state.auth);
  const { message } = useSelector(state => state.message);
  const dispatch = useDispatch();
  const [loading, setLoading] = useState(false);

  const { user: currentUser } = useSelector((state) => state.auth);

  if (currentUser) {
    return <Navigate to="/buerger" />;
  }

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prevState => ({
      ...prevState,
      [name]: value
    }));
    console.log(formData)
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log("handleSubmit")
    dispatch(login(formData.email, formData.passwort))
    .then(() => {
        navigate("/buerger");
        window.location.reload();
      })
    .catch(() => {
      setLoading(false);
    });
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
      </form>
    </div>
  );
};

export default LoginForm;

