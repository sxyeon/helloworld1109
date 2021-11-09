// func4.js

const persons = [{
    name: '이바다',
    eng: 80,
    math: 90,
    grade: 'A'
}, {
    name: '김영호',
    eng: 85,
    math: 90,
    grade: 'B+'
}, {
    name: '오재준',
    eng: 90,
    math: 88,
    grade: 'A+'
}];

function makeTr(obj) {
    // <tr><td>obj.name</td><td>obj.eng</td><td>obj.math</td></tr>
    // let tr = '<tr><td>' + obj.name + '</td><td>' + obj.eng + '</td><td>' + obj.math + '</td></tr>';
    let tr = '<tr>';
    for (let field in obj) {
        tr += '<td>' + obj[field] + '</td>';
    }
    tr += '</tr>';
    return tr;
}

function makeHead(obj) {
    let tr = '<tr>';
    for (let field in obj) {
        tr += '<th>' + field + '</th>';
    }
    tr += '</tr>';
    return tr;
}

let str = '<table border=1><tbody>';
str += makeHead(persons[0]); // {name:'이바다',eng:80, math:90}
for (let i = 0; i < persons.length; i++) {
    str += makeTr(persons[i]);
}
str += '</tbody></table>';

document.write(str);