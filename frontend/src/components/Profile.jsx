import React, {useEffect, useState, useCallback} from "react";
import { Navigate } from 'react-router-dom';
import { useSelector, useDispatch } from "react-redux";
import UserService from "../services/user.service"
import { logout } from "../actions/auth-buerger";

const Profile = () => {

    const [beschwerden, setBeschwerden] = useState([]);
    const dispatch = useDispatch();
    const { user: currentUser } = useSelector((state) => state.auth);

    useEffect(() => {
        UserService.getBuergerDashBoard().then(
            (response) => {
                setBeschwerden(response.data);
            },
            (error) => {
                const _content =
                    (error.response &&
                        error.response.data &&
                        error.response.data.message) ||
                    error.message ||
                    error.toString();
                setBeschwerden(_content);
                console.log(_content)
            }
        );
    }, []);

    const logOut = useCallback(() => {
        dispatch(logout());
    }, [dispatch]);

    if (!currentUser) {
        return <Navigate to="/buerger-anmelden" />;
    }

    return (
        <div className="container">
            <header className="jumbotron">
                <h3>
                    <strong>{currentUser.vorname} {currentUser.nachname}</strong> Profile
                </h3>
            </header>
            <p>
                <strong>Token:</strong> {currentUser.accessToken.substring(0, 20)} ...{" "}
                {currentUser.accessToken.substr(currentUser.accessToken.length - 20)}
            </p>
            <p>
                <strong>Telefonnummer:</strong> {currentUser.telefonnummer}
            </p>
            <p>
                <strong>Email:</strong> {currentUser.email}
            </p>
            <strong>Authorities:</strong>
            <ul>
                {currentUser.roles &&
                    currentUser.roles.map((role, index) => <li key={index}>{role}</li>)}
            </ul>
            <a href="/buerger-anmelden" className="nav-link" onClick={logOut}>
                LogOut
            </a>
        </div>
    );
};

export default Profile;
