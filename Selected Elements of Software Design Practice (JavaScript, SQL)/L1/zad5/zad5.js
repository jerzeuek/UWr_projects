function fib_it(n) {
    const tab = [];

    tab[0] = 0;
    tab[1] = 1;
    for (var i = 2; i <= n; i++) {
        tab[i] = tab[i - 1] + tab[i - 2];
    }
    return tab[n];
}

function fib_rec(n) {
    if (n == 0) return 0;
    if (n == 1) return 1;
    else return fib_rec(n - 1) + fib_rec(n - 2);

}

for (var i = 10; i <= 40; i++) {

    console.log(i, ":");

    console.time("iteracja");
    fib_it(i);
    console.timeEnd("iteracja");

    console.time("rekurencja");
    fib_rec(i);
    console.timeEnd("rekurencja");
}