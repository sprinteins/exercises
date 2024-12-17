const { statement } = require("./statement")
const plays = require("./plays.json")
const invoices = require("./invoices.json")
const playTypes = require("./playTypes.json")

console.log(statement(invoices[0], plays, playTypes));