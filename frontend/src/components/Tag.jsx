import React from 'react';

// Definiere eine Funktion, um die Farbe basierend auf dem Status auszuwählen
const getTagColor = (status) => {
    switch (status) {
        case 'kritisch':
            return '#FE9929';         // Kritisch - Rot
        case 'wichtig':
            return '#FFC800';      // Wichtig - Orange
        case 'marginal':
            return '#BEBEBE';      // Marginal - Gelb
        case 'abgeschlossen':
            return '#65C728';       // Abgeschlossen - Grün
        default:
            return 'gray';        // Default - Grau (für nicht definierte Status)
    }
};

const Tag = ({ text, status }) => {
    // Bestimme die Farbe basierend auf dem Status
    const tagColor = getTagColor(status);

    return (
        <span
            style={{
                display: "flex",
                alignItems: "center",
                padding: '4px 8px',
                borderRadius: '16px',
                backgroundColor: tagColor,
                fontSize: '14px',
                lineHeight: "14px",
                height: "22px"

            }}
        >
      {text}
    </span>
    );
};

export default Tag;
