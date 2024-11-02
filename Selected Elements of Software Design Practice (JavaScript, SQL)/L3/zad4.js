function sum() {
    var suma = 0
    for (var i = 0; i < arguments.length; i++) {
        suma += arguments[i]
    }
    console.log(suma)
}

sum(1, 2, 3)

sum(1, 2, 3, 4, 5)