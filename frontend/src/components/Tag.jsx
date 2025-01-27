import React from 'react';
// Erstellt eine wiederverwendbare Komponente Tag
// @author Maik Bartels
// Definiere eine Funktion, um die Farbe basierend auf dem Status auszuwählen
const getTagColor = (text) => {
    switch (text) {
        case 'Eingegangen':
            return '#9ce1ff';         // Kritisch - Rot
        case 'In bearbeitung':
            return '#fee229';      // Marginal - Gelb
        case 'Erledigt':
            return '#65C728';       // Abgeschlossen - Grün
        default:
            return '#E5E3E3';        // Default - Grau (für nicht definierte Status)
    }
};

const Tag = ({ text, status }) => {
    // Bestimme die Farbe basierend auf dem Status
    const tagColor = getTagColor(text);

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
                height: "22px",
                width: "fit-content"
            }}
        >
      {text}
    </span>
    );
};

export default Tag;
