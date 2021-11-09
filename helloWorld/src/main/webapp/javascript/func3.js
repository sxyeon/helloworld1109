// func3.js

function multiply(num) {
    // 3단.
    document.write('<table border="1"><tbody>');
    for (let i = 1; i <= 9; i++) {
        document.write('<tr><td>' + num + '</td><td>*</td><td>' + i + '</td><td>=</td><td>' + (3 * i) + '</td></tr>');
    }
    document.write('</tbody></table>');
}

function multiplyWithReturn(num) {
    // 3단.
    let str = '';
    str = '<table border="1">';
    for (let i = 1; i <= 9; i++) {
        
    }
    str += '</table>';
    return str; // <table><tr>.....</tr></table>
}
let result = multiplyWithReturn(6);
document.write(result);