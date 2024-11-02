function forEach(a, f) {
    for (var element of a) {
        f(element)
    }
}
function map(a, f) {
    var tab = []
    for (var element of a) {
        tab.push(f(element))
    }

    console.log(tab)
}
function filter(a, f) {
    var tab = []
    for (var element of a) {
        if (f(element) == true) {
            tab.push(element)
        }
    }
    console.log(tab)
}
var a = [1, 2, 3, 4];

forEach(a, function(x){
    console.log(x)
})

filter(a, function(x){
    return (x < 3)
})

map(a, function(x){
    return(x * 2)
})

forEach(a, _ => { console.log(_); });
filter(a, _ => _ < 3);
map(a, _ => _ * 2);
