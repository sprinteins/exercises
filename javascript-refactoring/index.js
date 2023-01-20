import statement from './statement.js';
import plays from "./plays.json" assert { type: "json" };
import invoices from "./invoices.json" assert { type: "json" };

console.log(statement(invoices[0], plays));


