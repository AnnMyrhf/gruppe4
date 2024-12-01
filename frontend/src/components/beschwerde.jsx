export default function Beschwerde(props) {
    const {beschwerde}= props

    //Styles
    const cardStyle = {
        height: "100%",
        width: "100%",
        display: "flex",
        flexDirection: "column",
        gap: "32px",
    };

    const headerStyle = {
        display: "flex",
        gap: "32px",
        justifyContent: "start"
    };

    const headerElementStyle = {
        display: "flex",
        gap: "4px",
        flexDirection: "column",
    };

    const headerItemStyle = {
        display: "flex",
        flexDirection: "column",
        gap: "4px"
    };

    const descriptionStyle = {
        width: "100%",
        display: "flex",
        flexDirection: "column",
        justifyContent: "start",
        gap: "4px"
    };

    return (
        <div style={cardStyle}>
            <div style={headerStyle}>
                <div style={headerItemStyle}>
                    <p className="Label">ID</p>
                    <p>{beschwerde.id}</p>
                </div>

                <div style={headerItemStyle}>
                    <p className="Label">Datum</p>
                    <p>Datum</p>
                </div>

                <div style={headerItemStyle}>
                    <p className="Label">Status</p>
                    <p>{beschwerde.status}</p>
                </div>

                <div style={headerItemStyle}>
                    <p className="Label">Priorität</p>
                    <p>{beschwerde.prioritaet}</p>
                </div>

                <div style={headerItemStyle}>
                    <p className="Label">Anhang</p>
                    <p>{beschwerde.anhang}</p>
                </div>
            </div>
            <div style={descriptionStyle}>
                <p className="Label">Beschreibung:</p>
                <p style={{
                    display: '-webkit-box',
                    WebkitBoxOrient: 'vertical', // Präfix für -webkit-box-orient
                    overflow: 'hidden',
                    textOverflow: 'ellipsis',
                    WebkitLineClamp: 5, // Präfix für -webkit-line-clamp
                    lineHeight: '1.5em', // Optional: Zeilenhöhe für Berechnung
                    maxHeight: 'calc(1.5em * 5)', // Maximale Höhe basierend auf 5 Zeilen
                }}>{beschwerde.textfeld}</p>
            </div>
        </div>
    );
}