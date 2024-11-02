function fib() {
    var n1 = 1, n2 = 0
    return {

        next: function () {
            var cur = n2
            n2 = n1
            n1 = n1 + cur
            return {
                value: cur,
                done: false
            }
        }
    }
}

function* fib() {
    [n1, n2] = [0, 1]
    while (true) {
        yield n1;
        [n1, n2] = [n2, n1 + n2]
    }
}



function* take(it, top) {
    for (var i = 0; i < top; i++) {
        yield it.next().value
    }
}

for (let num of take(fib(), 20)) {
    console.log(num);
}