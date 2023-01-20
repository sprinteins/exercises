import statement from './statement.js';
import plays from "./plays.json" assert { type: "json" };
import invoices from "./invoices.json" assert { type: "json" };

for (const invoice of invoices) {
    console.log(statement(invoice, plays));
}


