import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { useSelector } from "react-redux";
import UserService from "../services/user.service";
import backIcon from "../assests/arrow-left-solid.svg";
import Toaster from "../components/Toaster";
// Zeigt die Details einer Beschwerde, wenn Beschwerde im Dashboard ausgewählt wird. Für Mitarbeiter sind Änderungen möglich
// @author Maik Bartels
export default function BeschwerdeDetail() {
    const { id } = useParams(); // ID aus der URL holen
    const [beschwerde, setBeschwerde] = useState({}); // Beschwerden
    const [originalBeschwerde, setOriginalBeschwerde] = useState(null); // Zustand für ursprüngliche Daten
    const navigate = useNavigate(); // Navigate
    const { user: currentUser } = useSelector((state) => state.auth); // Angemeldeter User
    const [showToast, setShowToast] = useState(false); // Toaster state
    const [toastMessage, setToastMessage] = useState(''); // Toaster Message
    const [toastStatus, setToastStatus] = useState(''); // Toaster Status
    const anhang = beschwerde.anhang;
    const hasValidAnhang = anhang && anhang.daten && anhang.datenTyp;


    const formatDate = (dateString) => {
        if (!dateString) {
            return 'Ungültiges Datum';
        }
        const date = new Date(dateString); // Umwandlung des ISO 8601-Strings in ein Date-Objekt
        const options = {
            day: '2-digit',
            month: '2-digit',
            year: 'numeric'
        };

        // Formatierung mit Intl.DateTimeFormat
        return new Intl.DateTimeFormat('de-DE', options).format(date);
    };

    const getReadableStatus = (status) => {
        const STATUS_LABELS = {
            EINGEGANGEN: 'Eingegangen',
            IN_BEARBEITUNG: 'In Bearbeitung',
            ERLEDIGT: 'Erledigt',
        };

        return STATUS_LABELS[status] || 'Unbekannter Status';
    };

    const getReadablePrioritaet = (prio) => {
        const STATUS_LABELS = {
            NIEDRIG: 'Niedrig',
            MITTEL: 'Mittel',
            HOCH: 'Hoch',
        };

        return STATUS_LABELS[prio] || 'Unbekannter Status';
    };

    // Handle changes der Beschwerde
    const handleChange = (e) => {
        const { name, value } = e.target;
        setBeschwerde(prevState => ({
            ...prevState,
            [name]: value
        }));
    };

    // Api call beschwerden
    useEffect(() => {
        UserService.getBeschwerde(id).then(
            (response) => {
                setBeschwerde(response.data);
                setOriginalBeschwerde(response.data); // Ursprüngliche Daten speichern
                console.log(response.data)
                const anhang = beschwerde.anhang;
                if (anhang){
                    const imageSrc = `data:${anhang.datenTyp};base64,${anhang.daten}`;
                }
            },
            (error) => {
                const _content =
                    (error.response &&
                        error.response.data &&
                        error.response.data.message) ||
                    error.message ||
                    error.toString();
                console.log(error);
            }
        );
    }, [id]);


    // Api Put call für Änderungen der Beschwerde
    const handleSubmit = (e) => {
        e.preventDefault();

        // Überprüfung, ob sich der Kommentar oder der Status geändert hat
        if (beschwerde !== originalBeschwerde) {
            UserService.updateBeschwerde(id, beschwerde.kommentar, beschwerde.status)
                .then(response => {
                    console.log("Beschwerde aktualisiert:", response.data);
                    handleShowToast("Beschwerde aktualisiert", "success")
                })
                .catch(error => {
                    console.log(error)
                    handleShowToast("Beschwerde aktualisieren fehlgeschlagen", "error")
                });
        }
    };

    // Toaster zeigen
    const handleShowToast = (message, status) => {
        setToastMessage(message);
        setToastStatus(status);
        setShowToast(true);
        // Hier nach 3,5 Sekunden wieder auf false setzen, damit der Toast beim nächsten Mal neu
        // angezeigt werden kann.
        setTimeout(() => setShowToast(false), 3500);
    };

    // Ladezeit des api calls abfangen
    if (!beschwerde) {
        return <p>Lade Beschwerde...</p>;
    }

    // Styling
    const mainStyle = {
        padding: "64px",
        display: "flex",
        flexDirection: "column",
        gap: "16px",
        position: 'relative'
    };

    // beschwerde Detail markup
    return (
        <div style={mainStyle}>
            <button className="tertiaryBtn"
                    onClick={() => navigate('/dashboard')}>
                <img src={backIcon} alt="Icon" width="auto" height="16" />
                Zurück zum Dashboard
            </button>
            <h1>{beschwerde.titel}</h1>
            <p>ID: {beschwerde.id}</p>
            <p>Datum: {formatDate(beschwerde.erstellDatum)}</p>
            <p>Status: {getReadableStatus(beschwerde.status)}</p>
            <p>Priorität: {getReadablePrioritaet(beschwerde.prioritaet)}</p>
            <p>Kategorie: {beschwerde.beschwerdeTyp}</p>
            <p>Beschreibung: {beschwerde.textfeld}</p>
            {hasValidAnhang ? (
                <img
                    src={`data:${anhang.datenTyp};base64,${anhang.daten}`}
                    alt={anhang.dateiName}
                    style={{ maxWidth: "500px", height: "auto" }}
                />
            ) : (
                <p>Kein Anhang verfügbar</p>
            )}
            {
                currentUser &&
                currentUser.role.some(item => item.authority === 'BUERGER') &&
                <div>
                    <h2>Bearbeitungsstatus</h2>
                    {beschwerde.kommentar ?
                        <div>
                            <p>{beschwerde.kommentar}</p>
                        </div>
                        : <p>Beschwerde wurde noch nicht bearbeitet</p>}
                </div>
            }

            {currentUser &&
                currentUser.role.some(item => item.authority === 'MITARBEITER') &&
                <form onSubmit={handleSubmit} className="form">
                    <div className="lvg">
                        <label htmlFor="textarea">Kommentar </label>
                        <input
                            type="textarea"
                            id="kommentar"
                            name="kommentar"
                            value={beschwerde.kommentar || ""}
                            onChange={handleChange}
                        />
                    </div>

                    <div className="lvg">
                        <label htmlFor="status">Status ändern</label>
                        <select
                            id="status"
                            name="status"
                            value={beschwerde.status} // Der Wert des Dropdowns
                            onChange={handleChange}
                            required
                        >
                            <option value="EINGEGANGEN">Eingegangen</option>
                            <option value="IN_BEARBEITUNG">In Bearbeitung</option>
                            <option value="ERLEDIGT">Erledigt</option>
                        </select>
                    </div>

                    <button className="primary-btn">Änderungen speichern</button>
                </form>
            }
            <Toaster text={toastMessage} visible={showToast} status={toastStatus}/>
        </div>
    );
}
