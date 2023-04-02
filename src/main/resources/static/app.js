var tableContainer = document.getElementById('table-container');

// obtener el contenido de la tabla desde el servidor
fetch('/startGame?difficulty=EASY')
    .then(response => response.text())
    .then(tableHtml => {
        // agregar la tabla al elemento con el ID 'table-container'
        tableContainer.innerHTML = tableHtml;
    })
    .catch(error => console.error(error));