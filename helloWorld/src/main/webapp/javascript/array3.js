// array3.js(Array.map, Array.filter)
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

const newUsers = users.map(function (val, ind, ary) {
    let newObj = {}
    newObj.uid = val.id;
    newObj.uname = val.name;
    newObj.score = val.point;
    newObj.idx = ind;

    return newObj;
})
console.log(newUsers);

const filterUsers = newUsers.filter(function (val, ind) {
    return val.idx; // true or false
}); // return 참
console.clear();
console.log(filterUsers);

let sum = filterUsers.reduce(function (initVal, obj, ind) {
    // if (ind != 0) {
    //     console.log(initVal[ind - 1]);
    // }
    console.log(initVal, obj, ind)
    initVal.push(obj)
    return initVal;
}, []); // 참조값... a=3, a={}
console.log(sum);

// arrow function.
let sum2 = function sum2(num1, num2) {
    return num1 + num2;
}

sum2 = (num1, num2) => num1 + num2;

let trueOrFalse = [45, 4, 9, 16, 25].some(function (val, ind, ary) {
    return val > 10;
});
console.log(trueOrFalse);

const fruits = ['Apple', 'Orange', 'Apple', 'Mango'];
console.log(fruits.findIndex(function (val, ind, ary) {
    return val == 'Apple';
}));


console.log(Array.from('ABCD'));
console.log('A,B,C,D'.split(","));

console.log(fruits.keys());
for (let x of fruits.keys()) {
    console.log(x);
}


// let user1 = new User('user1', '사용자1', 90);
// console.log(user1.keys())
// for (let x of user1.keys()) {
//     console.log(x);
// };