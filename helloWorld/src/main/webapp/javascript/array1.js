// array1.js
const persons = [];

function addFnc() {
    let addBtn = document.getElementById('add');
    let val = addBtn.value;
    persons.unshift(val); //요소를 추가.

    console.log(persons);
}

function remFnc() {
    // persons.shift();
    let findVal = document.getElementById('add').value;
    let cnt = -1;
    for (let i = 0; i < persons.length; i++) {
        if (persons[i] === findVal) {
            cnt = i;
            break;
        }
    }
    if (cnt >= 0) {
        persons.splice(cnt, 1);
    }
    console.log(persons);
}

document.write('<input type="text" id="add" value="hong">');
document.write('<button onclick="addFnc()">추가</button>');
document.write('<button onclick="remFnc()">삭제</button>');



const ary = ['hong', 'hwang']; // new Array();
console.log(ary[0])
ary[0] = 'hong1';
ary[1] = 'hwang1';
ary[2] = 'park';
ary.push('choi'); // 제일마지막 추가.
// ary.pop(); // 제일마지막 삭제.
// ary.splice(3, 1); // splice: index, size, replace
// ary.unshift('first');
// ary.shift();
// for (let i = 0; i < ary.length; i++) {
//     console.log(ary[i]);
// }
console.log('----------')
for (let val of ary) {
    console.log(val);
}

const newAry = ary.slice(1, 2); // slice(include, exclude)
console.log(newAry);

const mergeAry = ary.concat(newAry); // 합침.
console.log(mergeAry)

mergeAry.sort();
mergeAry.reverse();
console.log(mergeAry);

const numbers = [2, 10, 100, 24, 4];
numbers.sort(function (f, l) {
    // if (f < l) {
    //     return -1;
    // } else {
    //     return 1;
    // }
    return f - l;
});
console.log(numbers);

const s1 = {
    name: 'hwang',
    score: 70
}
const s2 = {
    name: 'park',
    score: 80
}
const s3 = {
    name: 'choi',
    score: 90
}
const students = [s1, s2, s3];
students.sort(function (f, l) {
    if (f.score < l.score) {
        return -100;
    } else {
        return 100;
    }
});

console.log(students);