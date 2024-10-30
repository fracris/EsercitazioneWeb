console.log('HELLO JS');


// Evento click sul pulsante "Aggiungi un nuovo pesce"
document.getElementById("addFishButton").addEventListener("click", function() {
    // Mostra il form per aggiungere un nuovo pesce impostando "display: block"
    document.getElementById("fishForm").style.display = "block";
    // nascondi il form per rimuovere il pesce
    document.getElementById("removeFishForm").style.display = "none";
});

// Evento submit sul form "newFishForm"
document.getElementById("newFishForm").addEventListener("submit", function(event) {
    // Impedisce il comportamento di submit predefinito (invio dei dati)
    event.preventDefault();

    // Recupera i valori inseriti dall'utente nei campi del form
    const name = document.getElementById("name").value;
    const weight = document.getElementById("weight").value;
    const origin = document.getElementById("origin").value;

    // Riferimento alla tabella dove verranno aggiunti i nuovi pesci
    const tableBody = document.getElementById("fishTable").getElementsByTagName('tbody')[0];

    addNewRowV1(tableBody , name , weight , origin);

//    addNewRowV2(tableBody , name , weight , origin);

    // Nasconde il form di aggiunta pesce dopo l'inserimento
    document.getElementById("fishForm").style.display = "none";
    // Reset dei campi del form per future aggiunte
    document.getElementById("newFishForm").reset();
});


function addNewRowV1(tableBody, name, weight, origin) {
    // Crea un elemento <tr> per la nuova riga
    const newRow = document.createElement("tr");

    // Imposta il contenuto HTML per la nuova riga con la colonna "Number"
    newRow.innerHTML = `
        <td><span class="badge bg-dark-red fish-number"></span></td> <!-- Colonna per il numero progressivo -->
        <td>${name}</td>
        <td><span class="badge bg-dark">${weight}</span></td>
        <td>${origin}</td>
        <td><button class="btn btn-danger btn-sm deleteButton"><i class="fa-solid fa-trash"></i></button></td>
    `;

    // Aggiunge la nuova riga alla tabella
    tableBody.appendChild(newRow);

    // Riassegna i numeri progressivi
    updateFishNumbers(tableBody);

    // Aggiunge l'evento click al pulsante "Elimina" della nuova riga
    newRow.querySelector(".deleteButton").addEventListener("click", function() {
        newRow.remove(); // Rimuove la riga
        updateFishNumbers(tableBody); // Aggiorna i numeri dopo la rimozione
    });
}

// Funzione per aggiornare i numeri di pesce in base alla posizione delle righe
function updateFishNumbers(tableBody) {
    const rows = tableBody.querySelectorAll("tr");
    rows.forEach((row, index) => {
        const numberCell = row.querySelector(".fish-number");
        numberCell.textContent = index + 1; // Imposta il numero progressivo
    });
    let totFishes=document.getElementById('total-fishes');
    totFishes.textContent = "Total fishes: "+rows.length;
    if(rows.length > 0) {
        if(document.getElementById('fishNumber').disabled) document.getElementById('fishNumber').disabled = false;
        document.getElementById('fishNumber').placeholder="Min: 1, Max: "+rows.length;
        document.getElementById('fishNumber').max = rows.length;
    }
    else {
        document.getElementById('fishNumber').disabled = true;
        document.getElementById('fishNumber').placeholder="No fishes found";
    }

}

function addNewRowV2(tableBody, name, weight, origin){
    // Crea una nuova riga nella tabella
    const newRow = tableBody.insertRow();

    // Creazione delle celle per la nuova riga e inserimento dei dati
    const nameCell = newRow.insertCell(0);    // Cella per il nome
    const weightCell = newRow.insertCell(1);     // Cella per il peso
    const originCell = newRow.insertCell(2); // Cella per la zona di pesca
    const deleteButtonCell = newRow.insertCell(3); // Cella per il bottone elimina

    // Assegnazione dei valori alle celle della nuova riga
    nameCell.textContent = name;  // Inserisce il nome nella prima cella
    weightCell.innerHTML = `<span class="badge bg-dark">${weight}</span>`; // Inserisce il peso con badge
    originCell.textContent = origin; // Inserisce il luogo di pesca nella terza cella
    deleteButtonCell.innerHTML =`<button class="btn btn-danger btn-sm deleteButton"><i class="fa-solid fa-trash"></i></button>`

    console.log('Adding new Row , with method 2');
}
