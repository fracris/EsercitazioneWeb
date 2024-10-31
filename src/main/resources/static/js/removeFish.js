document.getElementById("cancelRemove").addEventListener("click", function() {
    document.getElementById("removeFishForm").style.display = "none";
});

document.getElementById("removeFishButton").addEventListener("click", function() {
    document.getElementById("removeFishForm").style.display = "block";
    document.getElementById("fishForm").style.display = "none";
});

document.getElementById("removeFishByNumberForm").addEventListener("submit", function(event) {
    event.preventDefault();

    const fishNumber = parseInt(document.getElementById("fishNumber").value);

    // Ottiene tutte le righe della tabella, escluse le intestazioni
    const rows =document.getElementById("fishTable").querySelectorAll('tr');

    rows[fishNumber].remove();
    document.getElementById("removeFishForm").style.display = "none";
    document.getElementById("removeFishByNumberForm").reset();
    const tableBody = document.getElementById("fishTable").getElementsByTagName('tbody')[0];

    updateFishNumbers(tableBody);
});

// Aggiunge la funzionalità di eliminazione per i pulsanti "Elimina" già presenti
document.querySelectorAll(".deleteButton").forEach(button => {
    button.addEventListener("click", function() {
        const row = button.closest('tr');
        row.parentNode.removeChild(row);
        const tableBody = document.getElementById("fishTable").getElementsByTagName('tbody')[0];
        updateFishNumbers(tableBody);
    });
});