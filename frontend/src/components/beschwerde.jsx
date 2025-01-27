import "../styles/beschwerde.css";
import Tag from './Tag';

// Erstellt eine wiederverwendbare Komponente Beschwerde
// @author Maik Bartels
export default function Beschwerde(props) {
    const {beschwerde} = props

    console.log(beschwerde)
    // Datum fromatieren
    const formatDate = (dateString) => {
        const date = new Date(dateString); // Umwandlung des ISO 8601-Strings in ein Date-Objekt
        const options = {
            day: '2-digit',
            month: '2-digit',
            year: 'numeric'
        };

        // Formatierung mit Intl.DateTimeFormat
        return new Intl.DateTimeFormat('de-DE', options).format(date);
    };

    // Status formatieren
    const getReadableStatus = (status) => {
        const STATUS_LABELS = {
            EINGEGANGEN: 'Eingegangen',
            IN_BEARBEITUNG: 'In Bearbeitung',
            ERLEDIGT: 'Erledigt',
        };

        return STATUS_LABELS[status] || 'Unbekannter Status';
    };

    //Markup der Beschwerde Komponente
    return (
        <div className="beschwerde">
            <div className="beschwerde-head">
                <div className="beschwerde-title-container">
                    <h3 className="beschwerde-title">{beschwerde.titel}</h3>
                    <Tag text={getReadableStatus(beschwerde.status)}/>
                </div>
                <div className="beschwerde-subinfo">
                    <p>{formatDate(beschwerde.erstellDatum)}</p>
                    <div className="subinfo-divider"></div>
                    <p>{beschwerde.beschwerdeTyp}</p>
                </div>
            </div>
            <p className="beschwerde-text">{beschwerde.textfeld}</p>
        </div>
    );
}
