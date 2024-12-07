

import React, { useState, useRef } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Navigate, useNavigate  } from 'react-router-dom';

import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import CheckButton from "react-validation/build/button";

import { buergerLogin } from "../actions/auth-buerger";

const required = (value) => {
    if (!value) {
        return (
            <div className="alert alert-danger" role="alert">
                This field is required!
            </div>
        );
    }
};

const Login = (props) => {

let navigate = useNavigate();

const form = useRef();
const checkBtn = useRef();

const [email, setEmail] = useState("");
const [passwort, setPasswort] = useState("");
const [username, setUsername] = useState("");
const [loading, setLoading] = useState(false);

const { isLoggedIn } = useSelector(state => state.auth);
const { message } = useSelector(state => state.message);

const dispatch = useDispatch();

const onChangeUsername = (e) => {
    const username = e.target.value;
    setUsername(username);
};

    const onChangeEmail = (e) => {
        const email = e.target.value;
        setEmail(email);
    };

const onChangePasswort = (e) => {
    const passwort = e.target.value;
    setPasswort(passwort);
};

const handleLogin = (e) => {
    e.preventDefault();

    setLoading(true);

    form.current.validateAll();

    if (checkBtn.current.context._errors.length === 0) {
        dispatch(buergerLogin(email, passwort))
            .then(() => {
                navigate("/buerger/dashboard");
                window.location.reload();
            })
            .catch(() => {
                setLoading(false);
            });
    } else {
        setLoading(false);
    }
};

if (isLoggedIn) {
    return <Navigate to="/buerger/dashboard" />;
}

return (
    <div className="col-md-12">
        <div className="card card-container">
            <img
                src="//ssl.gstatic.com/accounts/ui/avatar_2x.png"
                alt="profile-img"
                className="profile-img-card"
            />

            <Form onSubmit={handleLogin} ref={form}>
                <div className="form-group">
                    <label htmlFor="email">E-Mail</label>
                    <Input
                        type="text"
                        className="form-control"
                        name="email"
                        value={email}
                        onChange={onChangeUsername}
                        validations={[required]}
                    />
                </div>

                <div className="form-group">
                    <label htmlFor="passwort">Passwort</label>
                    <Input
                        type="passwort"
                        className="form-control"
                        name="passwort"
                        value={passwort}
                        onChange={onChangePasswort}
                        validations={[required]}
                    />
                </div>

                <div className="form-group">
                    <button className="btn btn-primary btn-block" disabled={loading}>
                        {loading && (
                            <span className="spinner-border spinner-border-sm"></span>
                        )}
                        <span>Login</span>
                    </button>
                </div>

                {message && (
                    <div className="form-group">
                        <div className="alert alert-danger" role="alert">
                            {message}
                        </div>
                    </div>
                )}
                <CheckButton style={{ display: "none" }} ref={checkBtn} />
            </Form>
        </div>
    </div>
);
};

export default Login;


