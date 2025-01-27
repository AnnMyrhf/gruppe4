import React, { useCallback, useState } from "react";
import {Link, useNavigate} from "react-router-dom";
import { buergerLogout } from "../actions/auth-buerger";
import { mitarbeiterLogout } from "../actions/auth-mitarbeiter";
import { useSelector, useDispatch } from "react-redux";
import logo from "../assests/Logo.png";
import UserService from "../services/user.service"

// Erstellt eine wiederverwendbare Komponente Header
// @author Maik Bartels
const Header = () => {

    const dispatch = useDispatch();
    const { user: currentUser } = useSelector((state) => state.auth);
    const [showDialog, setShowDialog] = useState(false);
    const navigate = useNavigate();

    // Logout Button Handler
    const logOut = useCallback(() => {
        if (currentUser.role === "BUERGER") {
            dispatch(buergerLogout());
        } else {
            dispatch(mitarbeiterLogout());
        }
    }, [dispatch, currentUser]);

    // Mitarbeiter/Burger löschen Button handler
    const handleDeleteClick = () => {
        setShowDialog(true);
    };

    // Dialog Confirm
    const handleConfirmDelete = () => {
        setShowDialog(false);
        if (currentUser.role.some(item => item.authority === 'BUERGER')) {
            console.log(currentUser.id)
            UserService.deleteAccount(currentUser.id, true).then(
                (response) => {
                    dispatch(buergerLogout());
                    navigate("/");
                    console.log(response);
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
        } else {
            UserService.deleteAccount(currentUser.id, false).then(
                (response) => {
                    dispatch(mitarbeiterLogout());
                    navigate("/");
                    console.log(response);
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
        }
    };

    // Dialog Cancel Button
    const handleCancel = () => {
        setShowDialog(false);
    };

    // Markup header Komponent
    return (
        <header>
            <div style={{ display: "flex", gap: "8px" }}>
                <img src={logo} width="40px" height="40px" alt="Logo" />
                <p style={{ fontSize: "24px", fontWeight: "bold", color: "white", userSelect: "none" }}>
                    CityFeedback
                </p>
                <button onClick={handleDeleteClick}>{currentUser.role.some(item => item.authority === 'BUERGER') ? "Bürger" : "Mitarbeiter"} Account löschen</button>
            </div>
            <nav>
                <p>{currentUser.email}</p>
                <Link className="navItem" to="/" onClick={logOut}>
                    Logout
                </Link>
            </nav>

            {showDialog && (
                <>
                    <div style={backdropStyle}></div>
                    <div style={dialogStyle}>
                        <h4>{currentUser.role.some(item => item.authority === 'BUERGER') ? "Bürger" : "Mitarbeiter"} Account löschen?</h4>
                        <p>Möchten Sie Account wirklich löschen? {currentUser.role.some(item => item.authority === 'BUERGER') ? "Alle Ihre Beschwerden werden dadurch ebenfalls gelöscht!" :
                            "Sie verlieren den Zugriff auf Ihre Kontoinformationen."}</p>
                        <div style={{display: "flex", gap: "10px"}}>
                            <button onClick={handleConfirmDelete} style={confirmButtonStyle}>
                                Ja, löschen
                            </button>
                            <button onClick={handleCancel} style={cancelButtonStyle}>
                                Abbrechen
                            </button>
                        </div>
                    </div>
                </>
            )}
        </header>
    );
};

// Styling
const backdropStyle = {
    position: "fixed",
    top: 0,
    left: 0,
    width: "100%",
    height: "100%",
    backgroundColor: "rgba(0, 0, 0, 0.7)", // Halbtransparenter schwarzer Hintergrund
    zIndex: 999, // Muss unter dem Dialog, aber über dem restlichen Inhalt liegen
};

const dialogStyle = {
    position: "fixed",
    top: "50%",
    left: "50%",
    transform: "translate(-50%, -50%)",
    padding: "20px",
    backgroundColor: "white",
    boxShadow: "0 4px 6px rgba(0,0,0,0.1)",
    borderRadius: "8px",
    zIndex: 1000, // Muss über dem Backdrop liegen
    display: "flex",
    flexDirection: "column",
    gap: "16px",
    maxWidth:"600px"
};

const confirmButtonStyle = {
    backgroundColor: "#e74c3c",
    color: "white",
    border: "none",
    padding: "10px 20px",
    borderRadius: "5px",
    cursor: "pointer",
};

const cancelButtonStyle = {
    backgroundColor: "#bdc3c7",
    color: "white",
    border: "none",
    padding: "10px 20px",
    borderRadius: "5px",
    cursor: "pointer",
};

export default Header;
