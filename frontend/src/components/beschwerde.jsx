export default function Beschwerde(props) {
    const {beschwerde}= props

    //Styles
    const cardStyle = {
        display: "flex",
        flexDirection: "column",
        width: "100%",
        maxWidth: "1200px",
        border: "1px solid #ddd",
        padding: "32px",
        borderRadius: "8px",
        backgroundColor: "#ffffff",
        boxShadow: "0 2px 10px rgba(0, 0, 0, 0.1)",
        gap: "32px"

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
                <p>{beschwerde.textfeld}</p>
            </div>
        </div>
    );
}