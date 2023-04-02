const cells = document.querySelectorAll('.hidden');
cells.forEach(cell => {
    cell.addEventListener('click', () => {
        const row = cell.dataset.row;
        const column = cell.dataset.column;
        fetch(`/revealCell?row=${row}&column=${column}`)
            .then(response => response.text())
            .then(html => {
                const tableContainer = document.getElementById('table-container');
                tableContainer.innerHTML = html;
            })
            .catch(error => console.error(error));
    });
});

function revealCell(row, column) {
    console.log('Clicked on cell (' + row + ', ' + column + ')');
    fetch('/revealCell?row=' + row + '&column=' + column)
        .then(response => response.text())
        .then(tableHtml => {
            cells.innerHTML = tableHtml;
            history.pushState({}, '', '/refreshTable');
        })
        .catch(error => console.error(error));
}
