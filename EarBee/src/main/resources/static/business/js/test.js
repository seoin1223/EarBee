function createPaginationRow(currentPage, pageSize, totalCount) {
    const paginationRow = document.createElement('tfoot');
    paginationRow.style.padding = '15px';
    paginationRow.style.textAlign = 'center';

    const paginationCell = document.createElement('td');
    paginationCell.colSpan = 2;
    paginationCell.style.textAlign = 'center';

    const startPage = Math.floor((currentPage - 1) / pageSize) * pageSize + 1;
    const totalPages = Math.ceil(totalCount / pageSize);

    const addr = document.querySelector('#inputNumber');

    function setPageNumber(page) {
        addr.setAttribute('data-current-page', page);
        searchAddr();
    }

    function createPrevButton() {
        const prevButton = document.createElement('a');
        prevButton.style.display = 'inline-block';
        prevButton.textContent = '<';
        setButtonHoverEffect(prevButton, '#D3D3D3');

        prevButton.addEventListener('click', () => {
            const currentPage = parseInt(addr.getAttribute('data-current-page'), 10);
            const prevPage = currentPage > startPage ? currentPage - 1 : startPage - 1;
            setPageNumber(prevPage);
        });

        return prevButton;
    }

    function createPageButton(page) {
        const pageButton = document.createElement('a');
        pageButton.style.display = 'inline-block';
        pageButton.textContent = page;
        setButtonHoverEffect(pageButton, '#D3D3D3');

        pageButton.addEventListener('click', () => setPageNumber(page));

        if (page.toString() === currentPage) {
            pageButton.style.color = '#ff0000';
        }

        return pageButton;
    }

    function createNextButton() {
        const nextButton = document.createElement('a');
        nextButton.style.display = 'inline-block';
        nextButton.textContent = '>';
        setButtonHoverEffect(nextButton, '#D3D3D3');

        nextButton.addEventListener('click', () => {
            const currentPage = parseInt(addr.getAttribute('data-current-page'), 10);
            const nextPage = currentPage + pageSize;
            setPageNumber(nextPage);
        });

        return nextButton;
    }

    if (startPage > 1) {
        paginationCell.appendChild(createPrevButton());
    }

    for (let k = startPage; k <= Math.min(startPage + pageSize - 1, totalPages); k++) {
        paginationCell.appendChild(createPageButton(k));
    }

    if (totalPages > startPage + pageSize - 1) {
        paginationCell.appendChild(createNextButton());
    }

    paginationRow.appendChild(paginationCell);
    return paginationRow;
}