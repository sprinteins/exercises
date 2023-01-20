const formatCurrency = value => {
    return new Intl.NumberFormat("en-US",
        {
            style: "currency", currency: "USD",
            minimumFractionDigits: 2
        }).format(value / 100);
    }

const statement = (invoice, plays) => {
    let totalAmount = 0;
    let volumeCredits = 0;
    let result = `Statement for ${invoice.customer}\n`;

    for (const performance of invoice.performances) {
        if(!(performance.playID in plays)) throw new Error(`unknown playID for: ${performance.playID}`)
        const play = plays[performance.playID];
        const [ volumeCredit, thisAmount ] = calculator(performance, play);
        // print line for this order
        result += `  ${play.name}: ${formatCurrency(thisAmount / 100)} (${performance.audience} seats)\n`;
        totalAmount += thisAmount;
        volumeCredits += volumeCredit;
    }
    result += `Amount owed is ${formatCurrency(totalAmount / 100)}\n`;
    result += `You earned ${volumeCredits} credits\n`;
    return result;
}

const calculator = (performance, play) => {
    let thisAmount = play.amount.total;
    if (performance.audience > play.audience) {
        thisAmount += play.amount.constant + (play.amount.multiple1 * (performance.audience - play.audience));
    }
    thisAmount += play.amount.multiple2 * performance.audience;

    // add volume credits
    let volumeCredit = Math.max(performance.audience - 30, 0);
    // add extra credit for every ten comedy attendees
    if ("comedy" === play.type) volumeCredit += Math.floor(performance.audience / 10);
    return [volumeCredit, thisAmount];
}

export default statement;
