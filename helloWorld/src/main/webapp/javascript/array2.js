// array2.js
const numbers = [3, 34, 28, 12, 5];

let sum = 0;
numbers.forEach(function (val, ind, ary) {
    if (val > 10)
        sum += val;
    // document.write(`<h1>${val}</h1>`);
});

console.log(`합계: ${sum}`);

document.write('<button onclick="createList()">생성</button>');
document.write('<div id="show"></div>');

function createList() {
    let fruits = ['Apple', 'Banana', 'Cherry'];
    let ulTag, liTag; // 변수선언.
    ulTag = document.createElement('ul'); // <ul></ul>
    for (let fruit of fruits) {
        liTag = document.createElement('li'); // <li></li>
        liTag.innerHTML = fruit; // <li>Apple</li>
        liTag.id = fruit.toLowerCase();
        liTag.setAttribute('border', 1)// <table border="1">
        ulTag.appendChild(liTag); // <ul><li>Apple</li></ul>
    }
    document.getElementById("show").appendChild(ulTag);
}


function User(id, name, point) {
    this.name = name;
    this.id = id;
    this.point = point;
};
const users = [new User('user1', '사용자1', 90),
    new User('user2', '사용자2', 110),
    new User('user3', '사용자3', 200),
    new User('user4', '사용자4', 30)
];
console.log(users);
let str;

function showList() {

    str = '<h1>회원리스트</h1>';
    str += '<table border="1">';
    str += '<tbody>';
    users.forEach(callBackFnc);
    str += '</tbody>';
    str += '</table>';
    document.write(str);

}

function callBackFnc(val, ind, ary) {
    str += '<tr><td>' + val.id + '</td><td>' + val.name + '</td><td>' + val.point + '</td></tr>';
}