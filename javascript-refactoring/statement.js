const formatCurrency = value => {
    /**
     * A function, which receive an amount of money and format that in USD 
     * @param {int}   value       A json object, which contains the sales detail of each play
     *
     * @return {string} Return the styled value in the USD format.
     */
    return new Intl.NumberFormat("en-US",
        {
            style: "currency", currency: "USD",
            minimumFractionDigits: 2
        }).format(value / 100);
    }

const statement = (invoice, plays) => {
    /**
     * A function, which create a Sale report for an invoice 
     * @param {object}   invoice       A json object, which contains the sales detail of each play
     * @param {object}   plays         A json object, which contains a mapping(or config) for each play
     *
     * @return {string} Return the generated report as a string, for the invoice.
     */
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
    /**
     * A function, which calculate the total sale amount of the given play, 
     * and the Credit which saler receive for it.
     * @param {object}   performance     Performance sale detail
     * @param {object}   play            Extra information about how to calculate the given play inside the performance
     *
     * @return {int, int} Return the calculated credit and sale amount.
     */
    let thisAmount = play.amount.total;
    if (performance.audience > play.audienceLimit) {
        thisAmount += play.amount.constant + (play.amount.multiple1 * (performance.audience - play.audienceLimit));
    }
    thisAmount += play.amount.multiple2 * performance.audience;

    // add volume credits if the limit is reached
    let volumeCredit = Math.max(performance.audience - play.audienceVolumeCredit, 0);
    // add extra credit if the play has the bonus
    if (play.audienceBonus) volumeCredit += Math.floor(performance.audience / play.audienceBonus);
    return [volumeCredit, thisAmount];
}

export default statement;
