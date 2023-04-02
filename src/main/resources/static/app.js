var tableContainer = document.getElementById('table-container');

const tbody = document.querySelector('#table-container tbody');
tbody.addEventListener('click', function (e) {
    const cell = e.target.closest('td');
    if (!cell) {
        return;
    } // Quit, not clicked on a cell
    const row = cell.parentElement;
    console.log(cell.innerHTML, row.rowIndex, cell.cellIndex);
    cell.classList.remove('hidden');
    if (cell.innerHTML === '.') {
        cell.classList.add('mine-clicked');
    }
});

function revealCell(row, column) {
    console.log('Clicked on cell (' + row + ', ' + column + ')');
    fetch('/revealCell?row=' + row + '&column=' + column)
        .then(response => response.text())
        .then(tableHtml => {
            tableContainer.innerHTML = tableHtml;
        })
        .catch(error => console.error(error));
}
