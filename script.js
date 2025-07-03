const ele = document.querySelector('#display');

document.addEventListener('keydown', function(e) {
    const key = e.key === ' ' ? 'Space' : e.key;
    const keyCode = e.keyCode || e.which; // fallback for older browsers
    const code = e.code;

    ele.innerHTML = `
        <table border="1" cellpadding="10">
            <tr>
                <th>Key</th>
                <th>KeyCode</th>
                <th>Code</th>
            </tr>
            <tr>
                <td>${key}</td>
                <td>${keyCode}</td>
                <td>${code}</td>
            </tr>
        </table>
    `;
});
