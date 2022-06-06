const { statement
    , PlayTypeError 
}           = require("./statement")
const plays     = require("./plays.json")
const invoices  = require("./invoices.json")



function printInvoices() {
try {
    console.log(statement(invoices[0], plays));
} catch(e) {
    if (e instanceof PlayTypeError) {
        console.log('Specifically handling "PlayTypeError"...');
        console.log(e);
    } else {
        console.log(e);
    }
}
}


function main() {
printInvoices();
}

main();
