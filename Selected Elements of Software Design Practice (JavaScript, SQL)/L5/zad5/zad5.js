"use strict";
exports.__esModule = true;
exports.logPerson = exports.persons = void 0;
exports.persons = [
    {
        name: 'Jan Kowalski',
        age: 17,
        occupation: 'Student'
    },
    {
        name: 'Tomasz Malinowski',
        age: 20,
        role: 'Administrator'
    }
];
function logPerson(person) {
    var additionalInformation;
    if ("role" in person) {
        additionalInformation = person.role;
    }
    else {
        additionalInformation = person.occupation;
    }
    console.log(" - ".concat(person.name, ", ").concat(person.age, ", ").concat(additionalInformation));
}
exports.logPerson = logPerson;
exports.persons.forEach(logPerson);
