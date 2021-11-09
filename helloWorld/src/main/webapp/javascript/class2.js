// class2.js

class Table {
    constructor() {
        this.table = document.createElement('table');
        this.tbody = document.createElement('tbody');
        this.table.appendChild(this.tbody);
    }
    setAttribute(attr, val) {
        this.table.setAttribute(attr, val);
    }
    appendTbody(obj) {
        this.tbody.appendChild(this.makeRow(obj));
    }
    makeRow(obj) {
        let tr = document.createElement('tr');
        let row = {};
        row = obj;
        for (let field in row) {
            // console.log(obj[field]);
            let td = document.createElement('td');
            td.innerHTML = row[field].value;
            tr.appendChild(td);
        }
        return tr;
    }
    makeTable() {
        return this.table;
    }
}
class Student {
    constructor(no, name, score, year) {
        this.studentNo = no;
        this.studentName = name;
        this.score = score;
        this.year = year;
    }
}
let table = new Table();
table.setAttribute('border', '1');
document.getElementById('show').appendChild(table.makeTable());

document.getElementById('btn').onclick = function () {
    let studentNo = document.getElementById('studentNo');
    let studentName = document.getElementById('studentName');
    let score = document.getElementById('score');
    let year = document.getElementById('year');
    let s1 = new Student(studentNo, studentName, score, year);
    // console.log(table.makeRow(s1));
    table.appendTbody(s1);
}