function sortTableByColumn(table, columnIndex, ascending = true) {
    const tbody = table.querySelector("tbody");
    const rows = Array.from(tbody.querySelectorAll("tr"));

    rows.sort((rowA, rowB) => {
        const cellA = rowA.cells[columnIndex].textContent.trim();
        const cellB = rowB.cells[columnIndex].textContent.trim();

        if (columnIndex === 2) {  // Colonna 'Year', ordina come numeri
            return ascending ? cellA - cellB : cellB - cellA;
        } else {  // Colonna 'Title', ordina alfabeticamente
            return ascending ? cellA.localeCompare(cellB) : cellB.localeCompare(cellA);
        }
    });

    rows.forEach(row => tbody.appendChild(row));
    updateFishNumbers(tbody);
}

window.addEventListener("load", function() {
    const table = document.getElementById("fishTable"); //ottiene l'elemento con id books-table
    let nameAscending = true;
    let weightAscending = true;
    let originAscending = true;

    document.getElementById("sortFishName").addEventListener("click", function() {
        sortTableByColumn(table, 1, nameAscending);
        const icon = document.getElementById("name-icon");
        if (!nameAscending) {
            icon.classList.remove('fa-arrow-down-a-z');
            icon.classList.add('fa-arrow-up-a-z');
        } else {
            icon.classList.remove('fa-arrow-up-a-z');
            icon.classList.add('fa-arrow-down-a-z');
        }
        nameAscending = !nameAscending;  // Inverte l'ordine
    });

    document.getElementById("sortFishWeight").addEventListener("click", function() {
        sortTableByColumn(table, 2, weightAscending);
        const icon = document.getElementById("weight-icon");
        if (!weightAscending) {
            icon.classList.remove('fa-arrow-down-1-9');
            icon.classList.add('fa-arrow-up-1-9');
        } else {
            icon.classList.remove('fa-arrow-up-1-9');
            icon.classList.add('fa-arrow-down-1-9');
        }
        weightAscending = !weightAscending;  // Inverte l'ordine
    });
    document.getElementById("sortFishOrigin").addEventListener("click", function() {
        sortTableByColumn(table, 3, originAscending);
        const icon = document.getElementById("origin-icon");
        if (!originAscending) {
            icon.classList.remove('fa-arrow-down-a-z');
            icon.classList.add('fa-arrow-up-a-z');
        } else {
            icon.classList.remove('fa-arrow-up-a-z');
            icon.classList.add('fa-arrow-down-a-z');
        }
        originAscending = !originAscending;  // Inverte l'ordine
    });
});

