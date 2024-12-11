import React, { useState, useRef } from "react";
import { useDispatch, useSelector } from "react-redux";

import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import CheckButton from "react-validation/build/button";
import { isEmail } from "validator";

import { buergerRegister } from "../actions/auth-buerger";
import message from "../reducers/message";


const required = (value) => {
    if (!value) {
        return (
            <div className="alert alert-danger" role="alert">
                This field is required!
            </div>
        );
    }
};

const validEmail = (value) => {
    if (!isEmail(value)) {
        return (
            <div className="alert alert-danger" role="alert">
                Ungültige E-Mail-Adresse.
            </div>
        );
    }
};

const gueltigesPasswort = (value) => {
    if (value.length < 8 || value.length > 40) {
        return (
            <div className="alert alert-danger" role="alert">
                The password must be between 6 and 40 characters.
            </div>
        );
    }
};

const Register = () => {
    const form = useRef();
    const checkBtn = useRef();

    const [anrede, setAnrede] = useState("");
    const [vorname, setVorname] = useState("");
    const [nachname, setNachname] = useState("");
    const [telefonnummer, setTelefonnummer] = useState("");
    const [email, setEmail] = useState("");
    const [passwort, setPasswort] = useState("");
    const [successful, setSuccessful] = useState(false);

    const handleChange = (event) => {
        const { name, value } = event.target; // Destructure event object
        switch (name) {
            case 'anrede':
                setAnrede(value);
                break;
            case 'vorname':
                setVorname(value);
                break;
            case 'nachname':
                setNachname(value);
                break;
            case 'telefonnummer':
                setTelefonnummer(value);
                break;
            case 'email':
                setEmail(value);
                break;
            case 'passwort':
                setPasswort(value);
                break;
            default:
                console.error(`Unexpected attribute name: ${name}`);
        }
    };

    const handleRegister = (e) => {
        e.preventDefault();

        setSuccessful(false);

        form.current.validateAll();
        const dispatch = useDispatch();

        if (checkBtn.current.context._errors.length === 0) {
            dispatch(buergerRegister( anrede,
                vorname,
                name,
                telefonnummer,
                email,
                passwort))
                .then(() => {
                    setSuccessful(true);
                })
                .catch(() => {
                    setSuccessful(false);
                });
        }
    };

    return (
        <div className="col-md-12">
            <div className="card card-container">
                <img
                    src="//ssl.gstatic.com/accounts/ui/avatar_2x.png"
                    alt="profile-img"
                    className="profile-img-card"
                />

                <Form onSubmit={handleRegister} ref={form}>
                    {!successful && (
                        <div>
                            <div className="form-group">
                                <label htmlFor="anrede">Anrede</label>
                                <Input
                                    type="text"
                                    className="form-control"
                                    name="anrede"
                                    value={anrede}
                                    onChange={handleChange}
                                    validations={[required]}
                                />
                            </div>
                            <div className="form-group">
                                <label htmlFor="vorname">Vorname</label>
                                <Input
                                    type="text"
                                    className="form-control"
                                    name="vorname"
                                    value={vorname}
                                    onChange={handleChange}
                                    validations={[required]}
                                />
                            </div>
                            <div className="form-group">
                                <label htmlFor="nachname">Nachname</label>
                                <Input
                                    type="text"
                                    className="form-control"
                                    name="nachname"
                                    value={nachname}
                                    onChange={handleChange}
                                    validations={[required]}
                                />
                            </div>
                            <div className="form-group">
                                <label htmlFor="telefonnummer">Telefonnummer</label>
                                <Input
                                    type="text"
                                    className="form-control"
                                    name="telefonnummer"
                                    value={telefonnummer}
                                    onChange={handleChange}
                                    validations={[required]}
                                />
                            </div>

                            <div className="form-group">
                                <label htmlFor="email">Email</label>
                                <Input
                                    type="text"
                                    className="form-control"
                                    name="email"
                                    value={email}
                                    onChange={handleChange}
                                    validations={[required, validEmail]}
                                />
                            </div>

                            <div className="form-group">
                                <label htmlFor="passwort">Passwort</label>
                                <Input
                                    type="passwort"
                                    className="form-control"
                                    name="passwort"
                                    value={passwort}
                                    onChange={handleChange}
                                    validations={[required, gueltigesPasswort]}
                                />
                            </div>

                            <div className="form-group">
                                <button className="btn btn-primary btn-block">Registrieren</button>
                            </div>
                        </div>
                    )}

                    {message && (
                        <div className="form-group">
                            <div className={successful ? "alert alert-success" : "alert alert-danger"} role="alert">
                                {message}
                            </div>
                        </div>
                    )}
                    <CheckButton style={{display: "none"}} ref={checkBtn}/>
                </Form>
            </div>
        </div>
    );
};

export default Register;
