document.getElementById("cancelRemove").addEventListener("click", function() {
    document.getElementById("removeFishForm").style.display = "none";
});

// DELETE ROW ON DEMAND
document.getElementById("removeFishButton").addEventListener("click", function() {
    document.getElementById("removeFishForm").style.display = "block";
    document.getElementById("fishForm").style.display = "none";
});

document.getElementById("removeFishByNumberForm").addEventListener("submit", function(event) {
    event.preventDefault(); // Impedisce il submit del form

    // Recupera il numero del pesce da rimuovere
    const fishNumber = parseInt(document.getElementById("fishNumber").value);

    // Ottiene tutte le righe della tabella, escluse le intestazioni
    const rows =document.getElementById("fishTable").querySelectorAll('tr');

    rows[fishNumber].remove(); // Rimuove la riga corrispondente (indice base 0)
    document.getElementById("removeFishForm").style.display = "none"; // Nasconde il form
    document.getElementById("removeFishByNumberForm").reset(); // Resetta il campo del form
    const tableBody = document.getElementById("fishTable").getElementsByTagName('tbody')[0];

    updateFishNumbers(tableBody);
});

// Funzione per rimuovere il pesce in base al numero
function removeFishByNumber(event) {

}

// DELETE specific row
// Aggiunge la funzionalità di eliminazione per i pulsanti "Elimina" già presenti
document.querySelectorAll(".deleteButton").forEach(button => {
    button.addEventListener("click", function() {
        const row = button.closest('tr');
        row.parentNode.removeChild(row); // Elimina la riga
        const tableBody = document.getElementById("fishTable").getElementsByTagName('tbody')[0];
        updateFishNumbers(tableBody); // Aggiorna i numeri dopo la rimozione
    });
});

/*
// Funzione per eliminare la riga con opzioni diverse
function deleteRow(button) {
    const row = button.parentNode.parentNode; // Trova la riga corrente

    // Opzione 1: Rimuove la riga direttamente
    row.parentNode.removeChild(row);

}
*/
